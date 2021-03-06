package cn.veasion.auto.controller;

import cn.veasion.auto.model.ApiExecuteStrategyVO;
import cn.veasion.auto.model.ApiTestCasePO;
import cn.veasion.auto.model.Page;
import cn.veasion.auto.model.ApiExecuteStrategyPO;
import cn.veasion.auto.model.R;
import cn.veasion.auto.service.ApiExecuteStrategyService;
import cn.veasion.auto.utils.Constants;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
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
        checkNotNull("??????????????????", apiExecuteStrategy::getName, apiExecuteStrategy::getProjectId, apiExecuteStrategy::getType, apiExecuteStrategy::getStrategy);
        if (ApiExecuteStrategyPO.TYPE_SCRIPT.equals(apiExecuteStrategy.getType())) {
            notEmpty(apiExecuteStrategy.getScript(), "??????????????????");
        } else if (ApiExecuteStrategyPO.TYPE_CASES.equals(apiExecuteStrategy.getType())) {
            notEmpty(apiExecuteStrategy.getCaseIds(), "?????????case");
        }
        apiExecuteStrategy.setName(apiExecuteStrategy.getName().trim());
        apiExecuteStrategyService.saveOrUpdate(apiExecuteStrategy);
        return R.ok(apiExecuteStrategy.getId());
    }

    @PostMapping("/update")
    public R<Object> update(@RequestBody ApiExecuteStrategyVO apiExecuteStrategy) {
        notNull(apiExecuteStrategy.getId(), "id????????????");
        if (ApiExecuteStrategyPO.TYPE_SCRIPT.equals(apiExecuteStrategy.getType())) {
            notEmpty(apiExecuteStrategy.getScript(), "??????????????????");
        } else if (ApiExecuteStrategyPO.TYPE_CASES.equals(apiExecuteStrategy.getType())) {
            notEmpty(apiExecuteStrategy.getCaseIds(), "?????????case");
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
    public R<String> runStrategy(Integer id) throws InterruptedException {
        ApiExecuteStrategyVO strategyVO = apiExecuteStrategyService.queryStrategyById(id);
        notNull(strategyVO, "???????????????");
        apiExecuteStrategyService.runStrategy(strategyVO);
        int count = 0;
        while (strategyVO.getRefLogId() == null) {
            Thread.sleep(200);
            if (++count > 10) {
                break;
            }
        }
        return R.ok(strategyVO.getRefLogId());
    }

}
