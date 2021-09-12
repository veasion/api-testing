package cn.veasion.auto.controller;

import cn.veasion.auto.model.ApiLogPO;
import cn.veasion.auto.model.ApiLogVO;
import cn.veasion.auto.model.ApiRankingVO;
import cn.veasion.auto.model.R;
import cn.veasion.auto.service.ApiLogService;
import cn.veasion.auto.utils.CpuMemoryUtils;
import org.apache.http.client.utils.DateUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * IndexController
 *
 * @author luozhuowei
 * @date 2021/9/10
 */
@RestController
@RequestMapping("/api")
public class IndexController {

    @Resource
    private ApiLogService apiLogService;

    @RequestMapping("/listRanking")
    public R<List<ApiRankingVO>> listRanking(ApiLogVO apiLog) {
        return R.ok(apiLogService.listRanking(apiLog));
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

    @RequestMapping("/chartInfo")
    public R<Map<String, Object>> chartInfo() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -7);
        ApiLogVO apiLog = new ApiLogVO();
        apiLog.setStartCreateDate(DateUtils.formatDate(calendar.getTime(), "yyyy-MM-dd 00:00:00"));
        apiLog.setEndCreateTime(DateUtils.formatDate(new Date(), "yyyy-MM-dd 59:59:59"));
        List<Map<String, Object>> mapList = apiLogService.groupDayStatusCount(apiLog);
        Map<String, Map<String, Object>> dayMap = new HashMap<>(mapList.size());
        for (Map<String, Object> map : mapList) {
            dayMap.put(String.valueOf(map.get("date")), map);
        }

        List<String> dayList = new ArrayList<>();
        List<Number> dayCountSucList = new ArrayList<>();
        List<Number> dayCountFailList = new ArrayList<>();
        List<Number> dayCountRunningList = new ArrayList<>();

        for (int i = 6; i >= 0; i--) {
            calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_MONTH, i);
            String date = DateUtils.formatDate(calendar.getTime(), "yyyy-MM-dd");
            dayList.add(date);
            Map<String, Object> dayCountMap = dayMap.get(date);
            if (dayCountMap == null) {
                dayCountSucList.add(0);
                dayCountFailList.add(0);
                dayCountRunningList.add(0);
            } else {
                dayCountSucList.add((Number) dayCountMap.getOrDefault("status2", 0));
                dayCountFailList.add((Number) dayCountMap.getOrDefault("status3", 0));
                dayCountRunningList.add((Number) dayCountMap.getOrDefault("status1", 0));
            }
        }

        Map<Integer, Integer> statusMap = apiLogService.countStatus(null);

        Map<String, Object> result = new HashMap<>();
        result.put("dayList", dayList);
        result.put("dayCountSucList", dayCountSucList);
        result.put("dayCountFailList", dayCountFailList);
        result.put("dayCountRunningList", dayCountRunningList);

        result.put("countSucTotal", statusMap.getOrDefault(ApiLogPO.STATUS_SUC, 0));
        result.put("countFailTotal", statusMap.getOrDefault(ApiLogPO.STATUS_FAIL, 0));
        result.put("countRunningTotal", statusMap.getOrDefault(ApiLogPO.STATUS_RUNNING, 0));
        return R.ok(result);
    }

}
