package cn.veasion.auto.job;

import cn.veasion.auto.config.ScheduledConfig;
import cn.veasion.auto.model.ApiExecuteStrategyPO;
import cn.veasion.auto.model.ApiExecuteStrategyVO;
import cn.veasion.auto.service.ApiExecuteStrategyService;
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
        List<ApiExecuteStrategyVO> list = apiExecuteStrategyService.queryCronExecutableStrategy(null);
        for (ApiExecuteStrategyVO strategy : list) {
            String key = ScheduledConfig.key(strategy.getClass(), strategy.getId());
            if (!ScheduledConfig.containsKey(key)) {
                try {
                    ScheduledConfig.resetCronTask(key, () -> apiExecuteStrategyService.runStrategy(strategy), strategy.getJobCron());
                } catch (Exception e) {
                    log.error("添加策略任务失败, id = {}, jobCron = {}", strategy.getId(), strategy.getJobCron(), e);
                }
            }
        }
    }

}
