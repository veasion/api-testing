package cn.veasion.auto.controller;

import cn.veasion.auto.model.ApiLogPO;
import cn.veasion.auto.model.ApiLogVO;
import cn.veasion.auto.model.Page;
import cn.veasion.auto.model.R;
import cn.veasion.auto.service.ApiLogService;
import com.sun.org.apache.bcel.internal.Constants;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * ApiLogController
 *
 * @author luozhuowei
 * @date 2021/9/11
 */
@RestController
@RequestMapping("/api/apiLog")
public class ApiLogController extends BaseController {

    @Resource
    private ApiLogService apiLogService;

    @GetMapping("/listPage")
    public Page<ApiLogPO> listPage(@RequestParam(required = false, defaultValue = "1") int pageNo,
                                   @RequestParam(required = false, defaultValue = "10") int pageSize,
                                   ApiLogVO apiLog) {
        return Page.ok(apiLogService.listPage(apiLog, pageNo, pageSize));
    }

    @GetMapping("/list")
    public R<List<ApiLogPO>> list(ApiLogVO apiLog) {
        return R.ok(apiLogService.listPage(apiLog, 1, Constants.MAX_CODE_SIZE));
    }

    @GetMapping("/queryByStrategyId")
    public Page<ApiLogPO> queryByStrategyId(Integer executeStrategyId, String logId) {
        return Page.ok(apiLogService.queryByStrategyId(executeStrategyId, logId, 1, Constants.MAX_CODE_SIZE));
    }

    @GetMapping("/pressureResult")
    public R<Object> pressureResult(Integer strategyId, String logId) {
        return R.ok(apiLogService.pressureResult(strategyId, logId));
    }

    @GetMapping("/sumList")
    public R<Object> sumList(ApiLogVO apiLog) {
        List<ApiLogPO> list = apiLogService.listPage(apiLog, 1, Constants.MAX_CODE_SIZE);
        Map<String, Object> result = new HashMap<>();
        result.put("list", list);
        result.put("startTime", list.stream().map(ApiLogPO::getCreateTime).filter(Objects::nonNull).min(Date::compareTo).orElse(null));
        result.put("endTime", list.stream().map(ApiLogPO::getUpdateTime).filter(Objects::nonNull).max(Date::compareTo).orElse(null));
        result.put("totalTime", list.stream().map(ApiLogPO::getTime).filter(Objects::nonNull).reduce(Integer::sum));
        result.put("totalExecTime", list.stream().map(ApiLogPO::getExecTime).filter(Objects::nonNull).reduce(Integer::sum));
        result.put("statusCountMap", list.stream().collect(Collectors.groupingBy(ApiLogPO::getStatus, Collectors.counting())));
        return R.ok(result);
    }

}
