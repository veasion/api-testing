package cn.veasion.auto.controller;

import cn.veasion.auto.model.ApiExecuteStrategyVO;
import cn.veasion.auto.model.ApiTestCasePO;
import cn.veasion.auto.model.Page;
import cn.veasion.auto.model.ApiExecuteStrategyPO;
import cn.veasion.auto.model.R;
import cn.veasion.auto.service.ApiExecuteStrategyService;
import cn.veasion.auto.utils.Constants;
import com.alibaba.fastjson.JSONObject;
import org.springframework.scheduling.support.CronExpression;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * ApiExecuteStrategyController
 *
 * @author luozhuowei
 * @date 2021/9/11
 */
@RestController
@RequestMapping("/api/apiExecuteStrategy")
public class ApiExecuteStrategyController extends BaseController {

    @Resource
    private ApiExecuteStrategyService apiExecuteStrategyService;

    @GetMapping("/listPage")
    public Page<ApiExecuteStrategyVO> listPage(@RequestParam(required = false, defaultValue = "1") int pageNo,
                                               @RequestParam(required = false, defaultValue = "10") int pageSize,
                                               ApiExecuteStrategyVO apiExecuteStrategyVO) {
        return Page.ok(apiExecuteStrategyService.listPage(apiExecuteStrategyVO, pageNo, pageSize));
    }

    @GetMapping("/list")
    public R<List<ApiExecuteStrategyVO>> list(ApiExecuteStrategyVO apiExecuteStrategyVO) {
        return R.ok(apiExecuteStrategyService.listPage(apiExecuteStrategyVO, 1, Constants.MAX_PAGE_SIZE));
    }

    @GetMapping("/getById")
    public R<ApiExecuteStrategyPO> getById(@RequestParam("id") Integer id) {
        return R.ok(apiExecuteStrategyService.getById(id));
    }

    @GetMapping("/queryStrategyById")
    public R<ApiExecuteStrategyVO> queryStrategyById(@RequestParam("id") Integer id) {
        return R.ok(apiExecuteStrategyService.queryStrategyById(id));
    }

    @PostMapping("/add")
    public R<Object> add(@RequestBody ApiExecuteStrategyVO apiExecuteStrategy) {
        apiExecuteStrategy.setId(null);
        if (apiExecuteStrategy.getIsAvailable() == null) {
            apiExecuteStrategy.setIsAvailable(Constants.NO);
        }
        checkNotNull("参数不能为空", apiExecuteStrategy::getName, apiExecuteStrategy::getProjectId, apiExecuteStrategy::getType, apiExecuteStrategy::getStrategy);
        if (ApiExecuteStrategyPO.TYPE_SCRIPT.equals(apiExecuteStrategy.getType())) {
            notEmpty(apiExecuteStrategy.getScript(), "脚本不能为空");
        } else if (ApiExecuteStrategyPO.TYPE_CASES.equals(apiExecuteStrategy.getType())) {
            notEmpty(apiExecuteStrategy.getCaseIds(), "请选择case");
        }
        apiExecuteStrategy.setName(apiExecuteStrategy.getName().trim());
        apiExecuteStrategyService.saveOrUpdate(apiExecuteStrategy);
        return R.ok(apiExecuteStrategy.getId());
    }

    @PostMapping("/update")
    public R<Object> update(@RequestBody ApiExecuteStrategyVO apiExecuteStrategy) {
        notNull(apiExecuteStrategy.getId(), "id不能为空");
        if (ApiExecuteStrategyPO.TYPE_SCRIPT.equals(apiExecuteStrategy.getType())) {
            notEmpty(apiExecuteStrategy.getScript(), "脚本不能为空");
        } else if (ApiExecuteStrategyPO.TYPE_CASES.equals(apiExecuteStrategy.getType())) {
            notEmpty(apiExecuteStrategy.getCaseIds(), "请选择case");
        }
        apiExecuteStrategyService.saveOrUpdate(apiExecuteStrategy);
        return R.ok();
    }

    @PostMapping("/delete")
    public R<Object> delete(@RequestBody Integer id) {
        apiExecuteStrategyService.delete(id);
        return R.ok();
    }

    @GetMapping("/listApiTestCase")
    public R<List<ApiTestCasePO>> listApiTestCase(Integer id) {
        return R.ok(apiExecuteStrategyService.listApiTestCase(id));
    }

    @PostMapping("/deleteCases")
    public R<Object> deleteCases(@RequestBody JSONObject data) {
        notNull(data);
        Integer id = data.getInteger("id");
        List<Integer> caseIds = data.getJSONArray("caseIds").toJavaList(Integer.class);
        return R.ok(apiExecuteStrategyService.deleteCases(id, caseIds));
    }

    @PostMapping("/addCasesWithTx")
    public R<Object> addCasesWithTx(@RequestBody JSONObject data) {
        notNull(data);
        Integer id = data.getInteger("id");
        List<Integer> caseIds = data.getJSONArray("caseIds").toJavaList(Integer.class);
        return R.ok(apiExecuteStrategyService.addCasesWithTx(id, caseIds));
    }

    @GetMapping("/runStrategy")
    public R<String> runStrategy(Integer id) {
        ApiExecuteStrategyVO strategyVO = apiExecuteStrategyService.queryStrategyById(id);
        notNull(strategyVO, "策略不存在");
        apiExecuteStrategyService.runStrategy(strategyVO);
        return R.ok(strategyVO.getRefLogId());
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
