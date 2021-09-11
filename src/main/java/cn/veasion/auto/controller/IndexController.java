package cn.veasion.auto.controller;

import cn.veasion.auto.model.ApiLogPO;
import cn.veasion.auto.model.ApiLogVO;
import cn.veasion.auto.model.R;
import cn.veasion.auto.service.ApiLogService;
import org.apache.http.client.utils.DateUtils;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
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
    public R<Object> listRanking(ApiLogVO apiLog) {
        return R.ok(apiLogService.listRanking(apiLog));
    }

    @RequestMapping("/chartInfo")
    public R<Map<String, Object>> chartInfo() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -7);
        ApiLogVO apiLog = new ApiLogVO();
        apiLog.setStartCreateDate(DateUtils.formatDate(calendar.getTime(), "yyyy-MM-dd 00:00:00"));
        apiLog.setEndCreateTime(DateUtils.formatDate(new Date(), "yyyy-MM-dd 59:59:59"));
        List<Map<String, Object>> mapList = apiLogService.groupStatusCount(apiLog);
        Map<String, Map<String, Object>> dayMap = new HashMap<>(mapList.size());
        for (Map<String, Object> map : mapList) {
            dayMap.put(String.valueOf(map.get("date")), map);
        }

        List<String> triggerDayList = new ArrayList<>();
        List<Number> triggerDayCountSucList = new ArrayList<>();
        List<Number> triggerDayCountFailList = new ArrayList<>();
        List<Number> triggerDayCountRunningList = new ArrayList<>();

        for (int i = 0; i < 7; i++) {
            calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_MONTH, i);
            String date = DateUtils.formatDate(calendar.getTime(), "yyyy-MM-dd");
            triggerDayList.add(date);
            Map<String, Object> dayCountMap = dayMap.get(date);
            if (dayCountMap == null) {
                triggerDayCountSucList.add(0);
                triggerDayCountFailList.add(0);
                triggerDayCountRunningList.add(0);
            } else {
                triggerDayCountSucList.add((Number) dayCountMap.getOrDefault("status2", 0));
                triggerDayCountFailList.add((Number) dayCountMap.getOrDefault("status3", 0));
                triggerDayCountRunningList.add((Number) dayCountMap.getOrDefault("status1", 0));
            }
        }

        List<Map<String, Object>> countStatusList = apiLogService.countStatus(null);
        Map<Number, Number> statusMap = new HashMap<>();
        for (Map<String, Object> map : countStatusList) {
            statusMap.put((Number) map.get("status"), (Number) map.get("count"));
        }

        Map<String, Object> result = new HashMap<>();
        result.put("triggerDayList", triggerDayList);
        result.put("triggerDayCountSucList", triggerDayCountSucList);
        result.put("triggerDayCountFailList", triggerDayCountFailList);
        result.put("triggerDayCountRunningList", triggerDayCountRunningList);

        result.put("triggerCountSucTotal", statusMap.getOrDefault(ApiLogPO.STATUS_SUC, 0));
        result.put("triggerCountFailTotal", statusMap.getOrDefault(ApiLogPO.STATUS_FAIL, 0));
        result.put("triggerCountRunningTotal", statusMap.getOrDefault(ApiLogPO.STATUS_RUNNING, 0));
        return R.ok(result);
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

}
