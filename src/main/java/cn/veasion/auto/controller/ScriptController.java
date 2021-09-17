package cn.veasion.auto.controller;

import cn.veasion.auto.exception.BusinessException;
import cn.veasion.auto.model.R;
import cn.veasion.auto.service.ApiRequestService;
import cn.veasion.auto.service.ProjectService;
import cn.veasion.auto.service.ScriptService;
import cn.veasion.auto.utils.StringUtils;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * ScriptController
 *
 * @author luozhuowei
 * @date 2021/9/13
 */
@RestController
@RequestMapping("/api/script")
public class ScriptController extends BaseController {

    @Resource
    private ScriptService scriptService;
    @Resource
    private ProjectService projectService;
    @Resource
    private ApiRequestService apiRequestService;

    @PostMapping("/runScript")
    public R<Object> runScript(@RequestBody JSONObject body) {
        notNull(body);
        return R.ok(scriptService.runScript(body));
    }

    @GetMapping("/toScript")
    public R<String> toScript(@RequestParam(required = false) Integer id,
                              @RequestParam(required = false) Integer projectId,
                              @RequestParam(required = false) String apiName) {
        return R.ok(scriptService.toScript(id, projectId, apiName));
    }

    @GetMapping("/apiNameTips")
    public R<Object> apiNameTips(@RequestParam(required = false) Integer projectId) {
        Map<String, Object> result = new HashMap<>();
        result.put("globalMap", projectService.getGlobalMap(projectId));
        result.put("apiNames", apiRequestService.queryAllApiName(projectId));
        return R.ok(result);
    }

    @GetMapping("/codeTips")
    public R<Object> codeTips() {
        return R.ok(scriptService.codeTips());
    }

    @PostMapping("/apiResponseTips/{projectId}")
    public R<Object> apiResponseTips(@RequestBody Map<String, String> varApiNameMap, @PathVariable("projectId") Integer projectId) {
        notNull(varApiNameMap);
        Integer maxTimeoutOfSeconds = null;
        String timeoutKey = "_maxTimeoutOfSeconds";
        if (varApiNameMap.containsKey(timeoutKey)) {
            String value = varApiNameMap.remove(timeoutKey);
            if (StringUtils.hasText(value)) {
                maxTimeoutOfSeconds = Integer.parseInt(value);
            }
        }
        if (varApiNameMap.isEmpty()) {
            throw new BusinessException("参数不能为空");
        }
        return R.ok(scriptService.apiResponseTips(projectId, varApiNameMap, maxTimeoutOfSeconds));
    }

}
