package cn.veasion.auto.controller;

import cn.veasion.auto.model.Page;
import cn.veasion.auto.model.ApiRequestPO;
import cn.veasion.auto.model.R;
import cn.veasion.auto.service.ApiRequestService;
import com.sun.org.apache.bcel.internal.Constants;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * ApiRequestController
 *
 * @author luozhuowei
 * @date 2021/9/11
 */
@RestController
@RequestMapping("/api/apiRequest")
public class ApiRequestController extends BaseController {

    @Resource
    private ApiRequestService apiRequestService;

    @GetMapping("/pageList")
    public Page<ApiRequestPO> pageList(@RequestParam(required = false, defaultValue = "1") int current,
                                       @RequestParam(required = false, defaultValue = "10") int size,
                                       ApiRequestPO apiRequestPO) {
        return Page.ok(apiRequestService.listPage(apiRequestPO, current, size));
    }

    @GetMapping("/list")
    public R<List<ApiRequestPO>> list(ApiRequestPO apiRequestPO) {
        return R.ok(apiRequestService.listPage(apiRequestPO, 1, Constants.MAX_CODE_SIZE));
    }

    @GetMapping("/getById")
    public R<ApiRequestPO> getById(@RequestParam("id") Integer id) {
        return R.ok(apiRequestService.getById(id));
    }

    @PostMapping("/add")
    public R<Object> add(@RequestBody ApiRequestPO apiRequestPO) {
        apiRequestPO.setId(null);
        notEmpty(apiRequestPO.getApiName(), "命名不能为空");
        notEmpty(apiRequestPO.getApiDesc(), "请求描述不能为空");
        apiRequestPO.setApiName(apiRequestPO.getApiName().trim());
        apiRequestService.saveOrUpdate(apiRequestPO);
        return R.ok(apiRequestPO.getId());
    }

    @PostMapping("/update")
    public R<Object> update(@RequestBody ApiRequestPO apiRequestPO) {
        notNull(apiRequestPO.getId(), "id不能为空");
        apiRequestService.saveOrUpdate(apiRequestPO);
        return R.ok();
    }

    @PostMapping("/remove")
    public R<Object> remove(Integer id) {
        apiRequestService.delete(id);
        return R.ok();
    }

}
