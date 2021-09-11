package cn.veasion.auto.controller;

import cn.veasion.auto.model.Page;
import cn.veasion.auto.model.ApiTestCasePO;
import cn.veasion.auto.model.R;
import cn.veasion.auto.service.ApiTestCaseService;
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
 * ApiTestCaseController
 *
 * @author luozhuowei
 * @date 2021/9/11
 */
@RestController
@RequestMapping("/api/apiTestCase")
public class ApiTestCaseController extends BaseController {

    @Resource
    private ApiTestCaseService apiTestCaseService;

    @GetMapping("/pageList")
    public Page<ApiTestCasePO> pageList(@RequestParam(required = false, defaultValue = "1") int current,
                                        @RequestParam(required = false, defaultValue = "10") int size,
                                        @RequestParam(required = false) Integer projectId,
                                        @RequestParam(required = false) String caseName,
                                        @RequestParam(required = false) String module,
                                        @RequestParam(required = false) String author) {
        ApiTestCasePO apiTestCase = new ApiTestCasePO();
        apiTestCase.setProjectId(projectId);
        apiTestCase.setCaseName(caseName);
        apiTestCase.setModule(module);
        apiTestCase.setAuthor(author);
        return Page.ok(apiTestCaseService.listPage(apiTestCase, current, size));
    }

    @GetMapping("/list")
    public R<List<ApiTestCasePO>> list(@RequestParam(required = false) Integer projectId,
                                       @RequestParam(required = false) String caseName) {
        ApiTestCasePO apiTestCase = new ApiTestCasePO();
        apiTestCase.setProjectId(projectId);
        apiTestCase.setCaseName(caseName);
        return R.ok(apiTestCaseService.listPage(apiTestCase, 1, Constants.MAX_CODE_SIZE));
    }

    @GetMapping("/getById")
    public R<ApiTestCasePO> getById(@RequestParam("id") Integer id) {
        return R.ok(apiTestCaseService.getById(id));
    }

    @PostMapping("/add")
    public R<Object> add(@RequestBody ApiTestCasePO apiTestCase) {
        apiTestCase.setId(null);
        notEmpty(apiTestCase.getCaseName(), "名称不能为空");
        apiTestCase.setCaseName(apiTestCase.getCaseName().trim());
        apiTestCaseService.saveOrUpdate(apiTestCase);
        return R.ok(apiTestCase.getId());
    }

    @PostMapping("/update")
    public R<Object> update(@RequestBody ApiTestCasePO apiTestCase) {
        notNull(apiTestCase.getId(), "id不能为空");
        apiTestCaseService.saveOrUpdate(apiTestCase);
        return R.ok();
    }

    @PostMapping("/remove")
    public R<Object> remove(Integer id) {
        apiTestCaseService.delete(id);
        return R.ok();
    }
}
