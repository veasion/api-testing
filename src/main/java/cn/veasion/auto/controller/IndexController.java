package cn.veasion.auto.controller;

import cn.veasion.auto.model.R;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

    @GetMapping("/index")
    public R<Map<String, Object>> index() {
        Map<String, Object> dashboardMap = new HashMap<>();
        dashboardMap.put("jobInfoCount", 0); // 全部执行器数量
        dashboardMap.put("jobLogCount", 0); // 成功数量 + 失败数量 + 执行中数量
        dashboardMap.put("jobLogSuccessCount", 0); // 成功数量
        dashboardMap.put("executorCount", 0); // 执行器数量
        return R.ok(dashboardMap);
    }

    @PostMapping("/chartInfo")
    public R<Map<String, Object>> chartInfo() {
        List<String> triggerDayList = new ArrayList<>(); // 7天: ["2021-09-04", "2021-09-05", ...]
        List<Integer> triggerDayCountSucList = new ArrayList<>(); // 7天: 成功数量
        List<Integer> triggerDayCountFailList = new ArrayList<>(); // 7天: 失败数量
        List<Integer> triggerDayCountRunningList = new ArrayList<>(); // 7天: 执行中数量

        int triggerCountSucTotal = 0; // 成功数量
        int triggerCountFailTotal = 0; // 失败数量
        int triggerCountRunningTotal = 0; // 执行中数量

        Map<String, Object> result = new HashMap<>();
        result.put("triggerDayList", triggerDayList);
        result.put("triggerDayCountSucList", triggerDayCountSucList);
        result.put("triggerDayCountFailList", triggerDayCountFailList);
        result.put("triggerDayCountRunningList", triggerDayCountRunningList);

        result.put("triggerCountSucTotal", triggerCountSucTotal);
        result.put("triggerCountFailTotal", triggerCountFailTotal);
        result.put("triggerCountRunningTotal", triggerCountRunningTotal);
        return R.ok(result);
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

}
