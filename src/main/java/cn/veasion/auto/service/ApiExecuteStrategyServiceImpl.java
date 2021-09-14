package cn.veasion.auto.service;

import cn.veasion.auto.config.ScheduledConfig;
import cn.veasion.auto.config.SpringBeanUtils;
import cn.veasion.auto.core.StrategyExecutor;
import cn.veasion.auto.exception.BusinessException;
import cn.veasion.auto.mapper.ApiExecuteStrategyMapper;
import cn.veasion.auto.mapper.StrategyCaseRelationMapper;
import cn.veasion.auto.model.ApiExecuteStrategyPO;
import cn.veasion.auto.model.ApiExecuteStrategyVO;
import cn.veasion.auto.model.ApiTestCasePO;
import cn.veasion.auto.model.StrategyCaseRelationPO;
import cn.veasion.auto.utils.Constants;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
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
    public List<ApiExecuteStrategyVO> list(ApiExecuteStrategyVO apiExecuteStrategy) {
        return apiExecuteStrategyMapper.queryList(apiExecuteStrategy);
    }

    @Override
    public Page<ApiExecuteStrategyVO> listPage(ApiExecuteStrategyVO apiExecuteStrategy, int pageIndex, int pageSize) {
        PageHelper.startPage(pageIndex, pageSize);
        return (Page<ApiExecuteStrategyVO>) apiExecuteStrategyMapper.queryList(apiExecuteStrategy);
    }

    @Override
    public void saveOrUpdate(ApiExecuteStrategyVO apiExecuteStrategy) {
        if (apiExecuteStrategy.getId() == null) {
            apiExecuteStrategy.init();
            apiExecuteStrategyMapper.insert(apiExecuteStrategy);
        } else {
            apiExecuteStrategy.setUpdateTime(new Date());
            apiExecuteStrategyMapper.update(apiExecuteStrategy);
            ApiExecuteStrategyPO strategyPO = getById(apiExecuteStrategy.getId());
            BeanUtils.copyProperties(strategyPO, apiExecuteStrategy);
        }
        if (ApiExecuteStrategyPO.STRATEGY_PRESSURE.equals(apiExecuteStrategy.getStrategy())) {
            ApiExecuteStrategyPO.ThreadStrategy threadStrategy = apiExecuteStrategy.toThreadStrategy();
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
        String key = ScheduledConfig.key(ApiExecuteStrategyPO.class, apiExecuteStrategy.getId());
        if (ApiExecuteStrategyPO.STRATEGY_JOB.equals(apiExecuteStrategy.getStrategy()) &&
                StringUtils.hasText(apiExecuteStrategy.getJobCron()) &&
                Constants.YES.equals(apiExecuteStrategy.getIsAvailable()) &&
                Constants.NO.equals(apiExecuteStrategy.getIsDeleted())) {
            final ApiExecuteStrategyPO strategyPO = apiExecuteStrategy;
            ScheduledConfig.resetCronTask(key, () -> SpringBeanUtils.getBean(getClass()).runStrategy(strategyPO), apiExecuteStrategy.getJobCron());
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
    public List<ApiExecuteStrategyVO> queryCronExecutableStrategy(Integer projectId) {
        return apiExecuteStrategyMapper.queryCronExecutableStrategy(projectId);
    }

    @Override
    public void triggerCronUpdate(Integer projectId, boolean isAdd) {
        List<ApiExecuteStrategyVO> list;
        if (isAdd) {
            // 新增: 查询可用
            list = queryCronExecutableStrategy(projectId);
        } else {
            // 移除: 查询所有
            ApiExecuteStrategyVO strategyVO = new ApiExecuteStrategyVO();
            strategyVO.setProjectId(projectId);
            strategyVO.setStrategy(ApiExecuteStrategyPO.STRATEGY_JOB);
            list = list(strategyVO);
        }
        for (ApiExecuteStrategyVO strategy : list) {
            try {
                String key = ScheduledConfig.key(strategy.getClass(), strategy.getId());
                if (isAdd) {
                    ScheduledConfig.addCronTask(
                            key,
                            () -> SpringBeanUtils.getBean(getClass()).runStrategy(strategy),
                            strategy.getJobCron());
                } else {
                    ScheduledConfig.removeCronTask(key);
                }
            } catch (Exception e) {
                log.error("触发更新策略任务失败, id = {}, jobCron = {}", strategy.getId(), strategy.getJobCron(), e);
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
