package cn.veasion.auto.job;

import cn.veasion.auto.service.ApiLogService;
import org.apache.http.client.utils.DateUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Calendar;

/**
 * ApiLogAutoDeleteJob
 *
 * @author luozhuowei
 * @date 2021/9/12
 */
@Component
public class ApiLogAutoDeleteJob {

    @Resource
    private ApiLogService apiLogService;

    @Scheduled(cron = "0 30 1 * * ?")
    public void autoDeleteJob() {
        // 删除30天前的日志
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 30);
        apiLogService.deleteLogs(DateUtils.formatDate(calendar.getTime(), "yyyy-MM-dd 00:00:00"));
    }

}
