package cn.veasion.auto.controller;

import cn.veasion.auto.config.SpringBeanUtils;
import cn.veasion.auto.job.CheckStrategyJob;
import cn.veasion.auto.model.R;
import cn.veasion.auto.utils.CpuMemoryUtils;
import org.springframework.scheduling.support.CronExpression;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * PublicController
 *
 * @author luozhuowei
 * @date 2021/9/11
 */
@RestController
@RequestMapping("/api/public")
public class PublicController extends BaseController {

    @GetMapping("/job/check")
    public void jobCheck() {
        SpringBeanUtils.getBean(CheckStrategyJob.class).check();
    }

    @RequestMapping("/serverInfo")
    public R<Object> serverInfo() {
        Map<String, Object> data = new HashMap<>();
        data.put("cpuUsage", CpuMemoryUtils.cpuLoad());
        data.put("memoryUsage", CpuMemoryUtils.memoryLoad());
        data.put("jvmUsage", CpuMemoryUtils.jvmLoad());
        data.put("updateTime", new Date());
        return R.ok(data);
    }

    @GetMapping("/nextTriggerTime")
    public R<List<String>> nextTriggerTime(String cron) {
        List<String> result = new ArrayList<>();
        CronExpression cronExpression = CronExpression.parse(cron);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime lastTime = LocalDateTime.now();
        for (int i = 0; i < 5; i++) {
            lastTime = cronExpression.next(lastTime);
            if (lastTime != null) {
                result.add(dateTimeFormatter.format(lastTime));
            } else {
                break;
            }
        }
        return R.ok(result);
    }

}
