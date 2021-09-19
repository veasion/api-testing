package cn.veasion.auto.core;

import cn.veasion.auto.exception.BusinessException;
import cn.veasion.auto.mapper.ApiExecuteStrategyMapper;
import cn.veasion.auto.mapper.StrategyCaseRelationMapper;
import cn.veasion.auto.model.ApiExecuteStrategyPO;
import cn.veasion.auto.model.ApiExecuteStrategyVO;
import cn.veasion.auto.model.ApiLogPO;
import cn.veasion.auto.model.ApiTestCasePO;
import cn.veasion.auto.model.ApiTestCaseVO;
import cn.veasion.auto.model.ProjectConfigPO;
import cn.veasion.auto.model.ProjectPO;
import cn.veasion.auto.service.ApiLogService;
import cn.veasion.auto.service.ApiTestCaseService;
import cn.veasion.auto.service.ProjectService;
import cn.veasion.auto.utils.Constants;
import cn.veasion.auto.utils.ThreadUtils;
import com.github.pagehelper.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.script.ScriptException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

/**
 * StrategyExecutor
 *
 * @author luozhuowei
 * @date 2021/9/11
 */
@Slf4j
@Component
public class StrategyExecutor {

    @Resource
    private ApiLogService apiLogService;
    @Resource
    private ProjectService projectService;
    @Resource
    private ScriptExecutor scriptExecutor;
    @Resource
    private ApiTestCaseService apiTestCaseService;
    @Resource
    private ApiExecuteStrategyMapper apiExecuteStrategyMapper;
    @Resource
    private StrategyCaseRelationMapper strategyCaseRelationMapper;

    /**
     * 策略执行（任务执行 or 接口压测）
     */
    public void run(ApiExecuteStrategyVO strategy) {
        Integer type = strategy.getType();
        boolean script = ApiExecuteStrategyPO.TYPE_SCRIPT.equals(type);
        if (script && !StringUtils.hasText(strategy.getScript())) {
            throw new BusinessException("执行策略异常，脚本为空！策略: " + strategy.getName());
        }
        ProjectPO projectPO = projectService.getById(strategy.getProjectId());
        ProjectConfigPO projectConfig = projectPO.getProjectConfig();

        ScriptContext scriptContext = scriptExecutor.createScriptContext(projectPO);
        scriptContext.setStrategy(strategy);

        ApiLogPO refLog = scriptContext.buildApiLog(null, true);
        apiLogService.addWithNewTx(refLog);
        strategy.setRefLogId(refLog.getId());

        long timeMillis = System.currentTimeMillis();
        try {
            if (StringUtils.hasText(projectConfig.getBeforeScript())) {
                scriptExecutor.executeScript(projectConfig.getBeforeScript(), scriptContext);
            }
            if (ApiExecuteStrategyPO.STRATEGY_PRESSURE.equals(strategy.getStrategy())) {
                // 接口压测
                refLog.setStatus(null);
                this.runPressure(scriptContext);
                if (refLog.getStatus() == null) {
                    refLog.setStatus(ApiLogPO.STATUS_SUC);
                }
            } else {
                // 任务执行
                this.runStrategy(scriptContext);
                refLog.setStatus(ApiLogPO.STATUS_SUC);
            }
            if (StringUtils.hasText(projectConfig.getAfterScript())) {
                scriptExecutor.tryExecuteScript(projectConfig.getAfterScript(), scriptContext);
            }
        } catch (Exception e) {
            log.error("执行异常，策略: {}", strategy.getName(), e);
            if (StringUtils.hasText(projectConfig.getExceptionScript())) {
                scriptExecutor.tryExecuteScript(projectConfig.getExceptionScript(), scriptContext);
            }
            refLog.appendLog(e.getMessage());
            refLog.setStatus(ApiLogPO.STATUS_FAIL);
        } finally {
            refLog.setExecTime((int) (System.currentTimeMillis() - timeMillis));
            finallyUpdate(strategy, scriptContext, refLog);
        }
    }

    private void finallyUpdate(ApiExecuteStrategyVO strategy, ScriptContext scriptContext, ApiLogPO refLog) {
        List<ApiLogPO> batchLogs = scriptContext.getApiLogList();
        int totalTime = 0;
        int strategyStatus = ApiExecuteStrategyPO.STATUS_FAIL;
        for (ApiLogPO log : batchLogs) {
            if (log.getTime() != null) {
                totalTime += log.getTime();
            }
            if (ApiLogPO.STATUS_FAIL.equals(log.getStatus())) {
                refLog.setStatus(ApiLogPO.STATUS_FAIL);
            } else if (ApiLogPO.STATUS_SUC.equals(log.getStatus())) {
                strategyStatus = ApiExecuteStrategyPO.STATUS_PART_SUC;
            }
        }
        if (ApiLogPO.STATUS_SUC.equals(refLog.getStatus())) {
            strategyStatus = ApiExecuteStrategyPO.STATUS_ALL_SUC;
        }
        refLog.setTime(totalTime);
        // 批量添加日志
        apiLogService.addAllWithNewTx(batchLogs);
        // 修改策略状态
        apiExecuteStrategyMapper.updateStatus(strategy.getId(), strategyStatus);
        // 更新策略日志
        apiLogService.updateWithNewTx(refLog);
        batchLogs.clear();
        System.gc();
    }

    /**
     * 定时任务普通执行
     */
    private void runStrategy(ScriptContext scriptContext) throws ScriptException {
        ApiExecuteStrategyPO strategy = scriptContext.getStrategy();
        boolean script = ApiExecuteStrategyPO.TYPE_SCRIPT.equals(strategy.getType());
        if (script) {
            scriptExecutor.execute(strategy, scriptContext);
        } else {
            ApiLogPO exceptionLog = new ApiLogPO();
            executeTestCaseAll(scriptContext, exceptionLog);
            scriptContext.getRefLog().appendLog(exceptionLog.getMsg());
        }
    }

    /**
     * 压测
     */
    private void runPressure(ScriptContext scriptContextMain) throws Exception {
        ProjectPO project = scriptContextMain.getProject();
        ProjectConfigPO projectConfig = project.getProjectConfig();
        ApiExecuteStrategyPO strategy = scriptContextMain.getStrategy();
        boolean script = ApiExecuteStrategyPO.TYPE_SCRIPT.equals(strategy.getType());
        int threadCount = Optional.ofNullable(strategy.getThreadCount()).orElse(1);
        ApiExecuteStrategyPO.ThreadStrategy threadStrategy = strategy.toThreadStrategy();
        if (threadStrategy == null || threadStrategy.getType() == null) {
            throw new BusinessException("压测执行参数异常！策略: " + strategy.getName());
        }

        // 线程用户
        List<Map<String, Object>> userEnvMaps = threadStrategy.getUserEnvMaps();
        AtomicInteger atomicIndex = new AtomicInteger(-1);
        List<ScriptContext> threadScriptContexts = new ArrayList<>(threadCount);
        ThreadLocal<ScriptContext> userThreadLocal = ThreadLocal.withInitial(() -> {
            ScriptContext scriptContext = scriptExecutor.createScriptContext(project);
            scriptContext.setStrategy(strategy);
            scriptContext.setNeedResetEnv(false);
            scriptContext.setRefLog(scriptContextMain.getRefLog());
            try {
                String beforeScript;
                if (projectConfig != null && StringUtils.hasText(projectConfig.getBeforeScript())) {
                    beforeScript = projectConfig.getBeforeScript();
                } else {
                    beforeScript = "1";
                }
                Map<String, Object> envMap = null;
                if (ApiExecuteStrategyPO.THREAD_ENV_TYPE_CUSTOM.equals(threadStrategy.getUserEnvType())) {
                    envMap = userEnvMaps.get(atomicIndex.updateAndGet(i -> ++i >= userEnvMaps.size() ? 0 : i));
                }
                scriptExecutor.executeScript(beforeScript, scriptContext, envMap);
            } catch (Exception e) {
                scriptContext.getRefLog().setStatus(ApiLogPO.STATUS_FAIL);
                log.error("执行前置脚本异常，项目: {}", project.getName(), e);
                throw new BusinessException("执行前置脚本异常", e);
            }
            threadScriptContexts.add(scriptContext);
            return scriptContext;
        });

        long intervalInMillis = Optional.ofNullable(threadStrategy.getIntervalInMillis()).orElse(-1L);
        boolean isTime = ApiExecuteStrategyPO.THREAD_STRATEGY_TIME.equals(threadStrategy.getType());
        ApiLogPO exceptionLog = new ApiLogPO();
        Callable<?> task = () -> {
            ScriptContext scriptContext = userThreadLocal.get();
            try {
                if (script) {
                    scriptExecutor.execute(strategy, scriptContext);
                } else {
                    executeTestCaseAll(scriptContext, exceptionLog);
                }
            } catch (Exception e) {
                scriptContext.getRefLog().setStatus(ApiLogPO.STATUS_FAIL);
                exceptionLog.setMsg(e.getClass().getSimpleName() + ": " + e.getMessage());
            }
            return null;
        };
        try {
            if (isTime) {
                long timeInMillis = Optional.ofNullable(threadStrategy.getTimeInMillis()).orElse(1L);
                ThreadUtils.concurrentInTime(threadCount, timeInMillis, intervalInMillis, task);
            } else {
                int loopCount = Optional.ofNullable(threadStrategy.getLoopCount()).orElse(1);
                ThreadUtils.concurrentInCount(threadCount, loopCount, intervalInMillis, task);
            }
        } catch (Exception e) {
            log.error("压测异常，策略: {}", strategy.getName(), e);
            throw e;
        } finally {
            scriptContextMain.getRefLog().appendLog(exceptionLog.getMsg());
            for (ScriptContext threadScriptContext : threadScriptContexts) {
                scriptContextMain.getApiLogList().addAll(threadScriptContext.getApiLogList());
            }
        }
    }

    private void executeTestCaseAll(ScriptContext scriptContext, ApiLogPO exceptionLog) {
        loadCase(scriptContext.getStrategy(), casePO -> {
            try {
                scriptExecutor.execute(casePO, scriptContext);
            } catch (ScriptException e) {
                log.error("执行case脚本异常，caseName: {}", casePO.getCaseName(), e);
                scriptContext.getRefLog().setStatus(ApiLogPO.STATUS_FAIL);
                exceptionLog.setMsg(e.getClass().getSimpleName() + ": " + e.getMessage());
            }
        });
    }

    private void loadCase(ApiExecuteStrategyPO strategy, Consumer<ApiTestCasePO> consumer) {
        if (ApiExecuteStrategyPO.TYPE_ALL_CASE.equals(strategy.getType())) {
            ApiTestCaseVO apiTestCaseVO = new ApiTestCaseVO();
            apiTestCaseVO.setProjectId(strategy.getProjectId());
            apiTestCaseVO.setIsAvailable(Constants.YES);
            int pageIndex = 1, pageSize = 100;
            while (true) {
                Page<ApiTestCaseVO> page = apiTestCaseService.listPage(apiTestCaseVO, pageIndex++, pageSize);
                page.forEach(consumer);
                if (page.isEmpty() || page.getTotal() < pageSize) {
                    break;
                }
            }
        } else if (ApiExecuteStrategyPO.TYPE_CASES.equals(strategy.getType())) {
            List<ApiTestCasePO> list = strategyCaseRelationMapper.queryApiTestCaseList(strategy.getId());
            if (list != null) {
                list.forEach(consumer);
            }
        }
    }

}
