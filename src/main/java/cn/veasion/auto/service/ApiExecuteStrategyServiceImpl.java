package cn.veasion.auto.service;

import cn.veasion.auto.config.ScheduledConfig;
import cn.veasion.auto.config.SpringBeanUtils;
import cn.veasion.auto.core.StrategyExecutor;
import cn.veasion.auto.exception.BusinessException;
import cn.veasion.auto.mapper.ApiExecuteStrategyMapper;
import cn.veasion.auto.mapper.StrategyCaseRelationMapper;
import cn.veasion.auto.model.ApiExecuteStrategyPO;
import cn.veasion.auto.model.ApiTestCasePO;
import cn.veasion.auto.model.StrategyCaseRelationPO;
import cn.veasion.auto.utils.Constants;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * ApiExecuteStrategyServiceImpl
 *
 * @author luozhuowei
 * @date 2021/9/10
 */
@Slf4j
@Service
public class ApiExecuteStrategyServiceImpl implements ApiExecuteStrategyService, ApplicationListener<ContextRefreshedEvent> {

    @Resource
    private StrategyExecutor strategyExecutor;
    @Resource
    private ApiExecuteStrategyMapper apiExecuteStrategyMapper;
    @Resource
    private StrategyCaseRelationMapper strategyCaseRelationMapper;

    @Override
    public ApiExecuteStrategyPO getById(int id) {
        return apiExecuteStrategyMapper.queryById(id);
    }

    @Override
    public List<ApiExecuteStrategyPO> list(ApiExecuteStrategyPO apiExecuteStrategyPO) {
        return apiExecuteStrategyMapper.queryList(apiExecuteStrategyPO);
    }

    @Override
    public Page<ApiExecuteStrategyPO> listPage(ApiExecuteStrategyPO apiExecuteStrategyPO, int pageIndex, int pageSize) {
        PageHelper.startPage(pageIndex, pageSize);
        return (Page<ApiExecuteStrategyPO>) apiExecuteStrategyMapper.queryList(apiExecuteStrategyPO);
    }

    @Override
    public void saveOrUpdate(ApiExecuteStrategyPO apiExecuteStrategyPO) {
        if (apiExecuteStrategyPO.getId() == null) {
            apiExecuteStrategyPO.init();
            apiExecuteStrategyMapper.insert(apiExecuteStrategyPO);
        } else {
            apiExecuteStrategyPO.setUpdateTime(new Date());
            apiExecuteStrategyMapper.update(apiExecuteStrategyPO);
            apiExecuteStrategyPO = getById(apiExecuteStrategyPO.getId());
        }
        if (ApiExecuteStrategyPO.STRATEGY_PRESSURE.equals(apiExecuteStrategyPO.getStrategy())) {
            ApiExecuteStrategyPO.ThreadStrategy threadStrategy = apiExecuteStrategyPO.toThreadStrategy();
            if (threadStrategy == null) {
                throw new BusinessException("线程创建策略不能为空");
            }
            if (threadStrategy.getType() == null) {
                throw new BusinessException("压测类型不能为空");
            }
            if (ApiExecuteStrategyPO.THREAD_STRATEGY_TIME.equals(threadStrategy.getType())) {
                if (threadStrategy.getTimeInMillis() == null || threadStrategy.getTimeInMillis() <= 0) {
                    throw new BusinessException("压测时长填写错误");
                }
            } else if (threadStrategy.getLoopCount() == null || threadStrategy.getLoopCount() <= 0) {
                throw new BusinessException("压测次数填写错误");
            }
        }
        String key = ScheduledConfig.key(ApiExecuteStrategyPO.class, apiExecuteStrategyPO.getId());
        if (ApiExecuteStrategyPO.STRATEGY_JOB.equals(apiExecuteStrategyPO.getStrategy()) &&
                StringUtils.hasText(apiExecuteStrategyPO.getJobCron()) &&
                Constants.YES.equals(apiExecuteStrategyPO.getIsAvailable()) &&
                Constants.NO.equals(apiExecuteStrategyPO.getIsDeleted())) {
            final ApiExecuteStrategyPO strategyPO = apiExecuteStrategyPO;
            ScheduledConfig.resetCronTask(key, () -> SpringBeanUtils.getBean(getClass()).runStrategy(strategyPO), apiExecuteStrategyPO.getJobCron());
        } else {
            ScheduledConfig.removeCronTask(key);
        }
    }

    @Override
    public List<ApiTestCasePO> listApiTestCase(int id) {
        return strategyCaseRelationMapper.queryApiTestCaseList(id);
    }

    @Override
    public int deleteCases(int id, List<Integer> caseIds) {
        return strategyCaseRelationMapper.delete(id, caseIds);
    }

    @Override
    public int addCasesWithTx(int id, List<Integer> caseIds) {
        List<StrategyCaseRelationPO> list = new ArrayList<>(caseIds.size());
        for (Integer caseId : caseIds) {
            StrategyCaseRelationPO relationPO = new StrategyCaseRelationPO();
            relationPO.init();
            relationPO.setCaseId(caseId);
            relationPO.setExecuteStrategyId(id);
            list.add(relationPO);
        }
        return strategyCaseRelationMapper.insertAll(list);
    }

    @Override
    public int delete(int id) {
        strategyCaseRelationMapper.delete(id, null);
        int count = apiExecuteStrategyMapper.deleteById(id);
        if (count > 0) {
            ScheduledConfig.removeCronTask(ScheduledConfig.key(ApiExecuteStrategyPO.class, id));
        }
        return count;
    }

    @Override
    public List<ApiExecuteStrategyPO> queryCronExecutableStrategy(Integer projectId) {
        return apiExecuteStrategyMapper.queryCronExecutableStrategy(projectId);
    }

    @Override
    public void triggerCronUpdate(Integer projectId, boolean isAdd) {
        List<ApiExecuteStrategyPO> list;
        if (isAdd) {
            // 新增: 查询可用
            list = queryCronExecutableStrategy(projectId);
        } else {
            // 移除: 查询所有
            ApiExecuteStrategyPO strategyPO = new ApiExecuteStrategyPO();
            strategyPO.setProjectId(projectId);
            strategyPO.setStrategy(ApiExecuteStrategyPO.STRATEGY_JOB);
            list = list(strategyPO);
        }
        for (ApiExecuteStrategyPO strategyPO : list) {
            try {
                String key = ScheduledConfig.key(strategyPO.getClass(), strategyPO.getId());
                if (isAdd) {
                    ScheduledConfig.addCronTask(
                            key,
                            () -> SpringBeanUtils.getBean(getClass()).runStrategy(strategyPO),
                            strategyPO.getJobCron());
                } else {
                    ScheduledConfig.removeCronTask(key);
                }
            } catch (Exception e) {
                log.error("触发更新策略任务失败, id = {}, jobCron = {}", strategyPO.getId(), strategyPO.getJobCron(), e);
            }
        }
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        this.triggerCronUpdate(null, true);
    }

    @Async
    @Override
    public void runStrategy(ApiExecuteStrategyPO strategy) {
        strategyExecutor.run(strategy);
    }

}
