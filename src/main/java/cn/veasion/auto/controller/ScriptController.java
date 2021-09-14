package cn.veasion.auto.controller;

import cn.veasion.auto.core.ScriptContext;
import cn.veasion.auto.core.ScriptExecutor;
import cn.veasion.auto.model.ProjectConfigPO;
import cn.veasion.auto.model.ProjectPO;
import cn.veasion.auto.model.R;
import cn.veasion.auto.service.ProjectService;
import cn.veasion.auto.utils.StringUtils;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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
    private ScriptExecutor scriptExecutor;
    @Resource
    private ProjectService projectService;

    @PostMapping("/runScript")
    public R<Object> runScript(@RequestBody JSONObject body) throws Exception {
        notNull(body);
        String script = body.getString("script");
        Integer projectId = body.getInteger("projectId");
        JSONObject envMap = body.getJSONObject("envMap");
        notEmpty(script, "脚本不能为空");
        notNull(projectId, "projectId不能为空");
        boolean beforeScript = Optional.ofNullable(body.getBoolean("beforeScript")).orElse(true);
        boolean afterScript = Optional.ofNullable(body.getBoolean("afterScript")).orElse(true);
        ProjectPO projectPO = projectService.getById(projectId);
        notNull(projectPO, "项目不存在");
        ProjectConfigPO projectConfig = projectPO.getProjectConfig();
        Map<String, Object> resultMap = new HashMap<>();
        ScriptContext scriptContext = scriptExecutor.createScriptContext(projectId);
        scriptContext.buildApiLog(null, true);
        if (beforeScript && projectConfig != null && StringUtils.hasText(projectConfig.getBeforeScript())) {
            try {
                scriptExecutor.executeScript(projectConfig.getBeforeScript(), scriptContext, envMap);
            } catch (Exception e) {
                resultMap.put("beforeScript", e.getClass().getSimpleName() + ": " + e.getMessage());
            }
        }
        Object result = scriptExecutor.executeScript(script, scriptContext, envMap);
        if (afterScript && projectConfig != null && StringUtils.hasText(projectConfig.getAfterScript())) {
            try {
                scriptExecutor.executeScript(projectConfig.getAfterScript(), scriptContext, envMap);
            } catch (Exception e) {
                resultMap.put("afterScript", e.getClass().getSimpleName() + ": " + e.getMessage());
            }
        }
        if (result instanceof String || result instanceof Map ||
                result instanceof Number || result instanceof Collection) {
            resultMap.put("result", result);
        } else {
            resultMap.put("result", String.valueOf(result));
        }
        resultMap.put("refLog", scriptContext.getRefLog());
        resultMap.put("logs", scriptContext.getApiLogList());
        return R.ok(resultMap);
    }

}
