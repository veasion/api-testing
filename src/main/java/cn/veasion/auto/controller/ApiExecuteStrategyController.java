package cn.veasion.auto.controller;

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

    @GetMapping("/pageList")
    public Page<ApiExecuteStrategyPO> pageList(@RequestParam(required = false, defaultValue = "1") int current,
                                              @RequestParam(required = false, defaultValue = "10") int size,
                                              ApiExecuteStrategyPO apiExecuteStrategyPO) {
        return Page.ok(apiExecuteStrategyService.listPage(apiExecuteStrategyPO, current, size));
    }

    @GetMapping("/list")
    public R<List<ApiExecuteStrategyPO>> list(ApiExecuteStrategyPO apiExecuteStrategyPO) {
        return R.ok(apiExecuteStrategyService.listPage(apiExecuteStrategyPO, 1, Constants.MAX_PAGE_SIZE));
    }

    @GetMapping("/getById")
    public R<ApiExecuteStrategyPO> getById(@RequestParam("id") Integer id) {
        return R.ok(apiExecuteStrategyService.getById(id));
    }

    @PostMapping("/add")
    public R<Object> add(@RequestBody ApiExecuteStrategyPO apiExecuteStrategyPO) {
        apiExecuteStrategyPO.setId(null);
        if (apiExecuteStrategyPO.getIsAvailable() == null) {
            apiExecuteStrategyPO.setIsAvailable(Constants.NO);
        }
        checkNotNull("参数不能为空", apiExecuteStrategyPO::getName, apiExecuteStrategyPO::getProjectId, apiExecuteStrategyPO::getType, apiExecuteStrategyPO::getStrategy);
        if (ApiExecuteStrategyPO.TYPE_SCRIPT.equals(apiExecuteStrategyPO.getType())) {
            notEmpty(apiExecuteStrategyPO.getScript(), "脚本不能为空");
        }
        apiExecuteStrategyPO.setName(apiExecuteStrategyPO.getName().trim());
        apiExecuteStrategyService.saveOrUpdate(apiExecuteStrategyPO);
        return R.ok(apiExecuteStrategyPO.getId());
    }

    @PostMapping("/update")
    public R<Object> update(@RequestBody ApiExecuteStrategyPO apiExecuteStrategyPO) {
        notNull(apiExecuteStrategyPO.getId(), "id不能为空");
        apiExecuteStrategyService.saveOrUpdate(apiExecuteStrategyPO);
        return R.ok();
    }

    @PostMapping("/remove")
    public R<Object> remove(Integer id) {
        apiExecuteStrategyService.delete(id);
        return R.ok();
    }

    @GetMapping("/listApiTestCase")
    public R<List<ApiTestCasePO>> listApiTestCase(Integer id) {
        return R.ok(apiExecuteStrategyService.listApiTestCase(id));
    }

    @PostMapping("/deleteCases")
    public R<Object> deleteCases(JSONObject data) {
        notNull(data);
        Integer id = data.getInteger("id");
        List<Integer> caseIds = data.getJSONArray("caseIds").toJavaList(Integer.class);
        return R.ok(apiExecuteStrategyService.deleteCases(id, caseIds));
    }

    @PostMapping("/addCasesWithTx")
    public R<Object> addCasesWithTx(JSONObject data) {
        notNull(data);
        Integer id = data.getInteger("id");
        List<Integer> caseIds = data.getJSONArray("caseIds").toJavaList(Integer.class);
        return R.ok(apiExecuteStrategyService.addCasesWithTx(id, caseIds));
    }

}
