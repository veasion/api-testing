package cn.veasion.auto.controller;

import cn.veasion.auto.core.ScriptContext;
import cn.veasion.auto.core.ScriptExecutor;
import cn.veasion.auto.model.ApiLogPO;
import cn.veasion.auto.model.ApiRequestPO;
import cn.veasion.auto.model.ProjectConfigPO;
import cn.veasion.auto.model.ProjectPO;
import cn.veasion.auto.model.R;
import cn.veasion.auto.service.ApiRequestService;
import cn.veasion.auto.service.ProjectService;
import cn.veasion.auto.utils.Constants;
import cn.veasion.auto.utils.EvalAnalysisUtils;
import cn.veasion.auto.utils.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

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
    @Resource
    private ApiRequestService apiRequestService;

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
        Object result;
        try {
            result = scriptExecutor.executeScript(script, scriptContext, envMap);
        } catch (Exception e) {
            result = null;
            scriptContext.getRefLog().setStatus(ApiLogPO.STATUS_FAIL);
            scriptContext.getRefLog().appendLog("执行失败 " + e.getClass().getSimpleName() + "：" + e.getMessage());
        }
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

    @GetMapping("/toScript")
    public R<String> toScript(@RequestParam(required = false) Integer id,
                              @RequestParam(required = false) Integer projectId,
                              @RequestParam(required = false) String apiName) {
        ApiRequestPO obj;
        if (id != null) {
            obj = apiRequestService.getById(id);
        } else {
            obj = apiRequestService.queryByApiName(apiName, projectId);
        }
        notNull(obj, "请求不存在");
        StringBuilder sb = new StringBuilder();
        sb.append("// ").append(obj.getApiDesc().replace(Constants.LINE, ""));
        sb.append(Constants.LINE).append("http.request('").append(obj.getApiName()).append("'");
        Set<String> keys = EvalAnalysisUtils.matcherKeys(obj.getUrl() + obj.getBody() + obj.getHeadersJson());
        if (keys.size() > 0) {
            handleScriptParams(obj.getProjectId(), sb, keys);
        }
        sb.append(");");
        return R.ok(sb.toString());
    }

    private void handleScriptParams(Integer projectId, StringBuilder sb, Set<String> keys) {
        ProjectPO project = projectService.getById(projectId);
        if (project != null && project.getProjectConfig() != null) {
            JSONObject varJson = project.getProjectConfig().toGlobalVarJson();
            if (varJson != null) {
                Iterator<String> iterator = keys.iterator();
                while (iterator.hasNext()) {
                    String key = iterator.next();
                    if (varJson.containsKey(key)) {
                        keys.remove(key);
                    }
                }
            }
        }
        if (keys.size() > 0) {
            JSONObject params = new JSONObject();
            for (String key : keys) {
                params.put(key, null);
            }
            sb.append(", ");
            sb.append(params.toString(SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue).replace("\":", "\": "));
        }
    }

}
