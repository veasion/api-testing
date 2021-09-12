package cn.veasion.auto.core;

import cn.veasion.auto.exception.BusinessException;
import cn.veasion.auto.mapper.StrategyCaseRelationMapper;
import cn.veasion.auto.model.ApiExecuteStrategyPO;
import cn.veasion.auto.model.ApiLogPO;
import cn.veasion.auto.model.ApiTestCasePO;
import cn.veasion.auto.model.ProjectConfigPO;
import cn.veasion.auto.service.ApiLogService;
import cn.veasion.auto.service.ApiTestCaseService;
import cn.veasion.auto.utils.Constants;
import cn.veasion.auto.utils.ThreadUtils;
import com.github.pagehelper.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;
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
    private ScriptExecutor scriptExecutor;
    @Resource
    private ApiTestCaseService apiTestCaseService;
    @Resource
    private StrategyCaseRelationMapper strategyCaseRelationMapper;

    public void run(ApiExecuteStrategyPO strategy) {
        Integer type = strategy.getType();
        boolean script = ApiExecuteStrategyPO.TYPE_SCRIPT.equals(type);
        if (script && !StringUtils.hasText(strategy.getScript())) {
            throw new BusinessException("执行策略异常，脚本为空！策略: " + strategy.getName());
        }
        ScriptContext scriptContext = scriptExecutor.createScriptContext(strategy.getProjectId());
        scriptContext.setStrategy(strategy);

        ApiLogPO apiLog = scriptContext.buildApiLog(null, false);
        apiLogService.addWithNewTx(apiLog);
        scriptContext.setRefId(apiLog.getId());

        ProjectConfigPO projectConfig = scriptContext.getProject().getProjectConfig();
        long timeMillis = System.currentTimeMillis();
        try {
            if (StringUtils.hasText(projectConfig.getBeforeScript())) {
                scriptExecutor.executeScript(projectConfig.getBeforeScript(), scriptContext);
            }
            if (ApiExecuteStrategyPO.STRATEGY_PRESSURE.equals(strategy.getStrategy())) {
                // 压测
                apiLog.setStatus(null);
                runPressure(strategy, scriptContext, apiLog);
                if (apiLog.getStatus() == null) {
                    apiLog.setStatus(ApiLogPO.STATUS_SUC);
                }
            } else {
                // 普通执行
                runStrategy(strategy, scriptContext);
                apiLog.setStatus(ApiLogPO.STATUS_SUC);
            }
            if (StringUtils.hasText(projectConfig.getAfterScript())) {
                scriptExecutor.executeScript(projectConfig.getAfterScript(), scriptContext);
            }
        } catch (Exception e) {
            log.error("执行异常，策略: {}", strategy.getName(), e);
            if (StringUtils.hasText(projectConfig.getExceptionScript())) {
                scriptExecutor.executeScript(projectConfig.getExceptionScript(), scriptContext);
            }
            apiLog.setMsg(e.getMessage());
            apiLog.setStatus(ApiLogPO.STATUS_FAIL);
        } finally {
            apiLog.setExecTime((int) (System.currentTimeMillis() - timeMillis));
            List<ApiLogPO> batchLogs = scriptContext.getApiLogList();
            int totalTime = 0;
            for (ApiLogPO log : batchLogs) {
                if (log.getTime() != null) {
                    totalTime += log.getTime();
                }
                if (ApiLogPO.STATUS_FAIL.equals(log.getStatus())) {
                    apiLog.setStatus(ApiLogPO.STATUS_FAIL);
                }
            }
            apiLog.setTime(totalTime);
            apiLogService.updateWithNewTx(apiLog);
            // 批量添加日志
            apiLogService.addAllWithNewTx(batchLogs);
            batchLogs.clear();
            System.gc();
        }
    }

    private void runStrategy(ApiExecuteStrategyPO strategy, ScriptContext scriptContext) {
        boolean script = ApiExecuteStrategyPO.TYPE_SCRIPT.equals(strategy.getType());
        if (script) {
            scriptExecutor.execute(strategy, scriptContext);
        } else {
            loadCase(strategy, casePO -> scriptExecutor.execute(casePO, scriptContext));
        }
    }

    private void runPressure(ApiExecuteStrategyPO strategy, ScriptContext scriptContext, ApiLogPO apiLog) throws Exception {
        boolean script = ApiExecuteStrategyPO.TYPE_SCRIPT.equals(strategy.getType());
        int threadCount = Optional.ofNullable(strategy.getThreadCount()).orElse(1);
        ApiExecuteStrategyPO.ThreadStrategy threadStrategy = strategy.toThreadStrategy();
        if (threadStrategy == null || threadStrategy.getType() == null) {
            throw new BusinessException("压测执行参数异常！策略: " + strategy.getName());
        }
        long intervalInMillis = Optional.ofNullable(threadStrategy.getIntervalInMillis()).orElse(-1L);
        boolean isTime = ApiExecuteStrategyPO.THREAD_STRATEGY_TIME.equals(threadStrategy.getType());
        Callable<?> task = () -> {
            try {
                if (script) {
                    scriptExecutor.execute(strategy, scriptContext);
                } else {
                    loadCase(strategy, casePO -> scriptExecutor.execute(casePO, scriptContext));
                }
            } catch (Exception e) {
                apiLog.setMsg(e.getMessage());
                apiLog.setStatus(ApiLogPO.STATUS_FAIL);
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
        }
    }

    private void loadCase(ApiExecuteStrategyPO strategy, Consumer<ApiTestCasePO> consumer) {
        if (ApiExecuteStrategyPO.TYPE_ALL_CASE.equals(strategy.getType())) {
            ApiTestCasePO apiTestCasePO = new ApiTestCasePO();
            apiTestCasePO.setProjectId(strategy.getProjectId());
            apiTestCasePO.setIsAvailable(Constants.YES);
            int pageIndex = 1, pageSize = 100;
            while (true) {
                Page<ApiTestCasePO> page = apiTestCaseService.listPage(apiTestCasePO, pageIndex++, pageSize);
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