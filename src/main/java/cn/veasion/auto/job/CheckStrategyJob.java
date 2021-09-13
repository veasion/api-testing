package cn.veasion.auto.job;

import cn.veasion.auto.config.ScheduledConfig;
import cn.veasion.auto.model.ApiExecuteStrategyPO;
import cn.veasion.auto.service.ApiExecuteStrategyService;
import cn.veasion.auto.utils.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * CheckStrategyJob
 *
 * @author luozhuowei
 * @date 2021/9/11
 */
@Slf4j
@Component
public class CheckStrategyJob {

    @Resource
    private ApiExecuteStrategyService apiExecuteStrategyService;

    @Scheduled(cron = "0 */30 * * * ? ")
    public void check() {
        ApiExecuteStrategyPO apiExecuteStrategyPO = new ApiExecuteStrategyPO();
        apiExecuteStrategyPO.setJobCron("*");
        apiExecuteStrategyPO.setType(ApiExecuteStrategyPO.STRATEGY_JOB);
        List<ApiExecuteStrategyPO> list = apiExecuteStrategyService.list(apiExecuteStrategyPO);
        for (ApiExecuteStrategyPO strategyPO : list) {
            String key = ScheduledConfig.key(strategyPO.getClass(), strategyPO.getId());
            if (Constants.NO.equals(strategyPO.getIsAvailable())) {
                ScheduledConfig.removeCronTask(key);
                continue;
            }
            if (!ScheduledConfig.containsKey(key)) {
                try {
                    ScheduledConfig.resetCronTask(key, () -> apiExecuteStrategyService.runWithTx(strategyPO), strategyPO.getJobCron());
                } catch (Exception e) {
                    log.error("添加策略任务失败, id = {}, jobCron = {}", strategyPO.getId(), strategyPO.getJobCron(), e);
                }
            }
        }
    }

}
