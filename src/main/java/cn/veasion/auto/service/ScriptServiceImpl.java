package cn.veasion.auto.service;

import cn.veasion.auto.core.ScriptContext;
import cn.veasion.auto.core.ScriptExecutor;
import cn.veasion.auto.core.bind.AbstractScriptBindBean;
import cn.veasion.auto.core.bind.ScriptBindBean;
import cn.veasion.auto.exception.BusinessException;
import cn.veasion.auto.model.ApiExecuteStrategyPO;
import cn.veasion.auto.model.ApiLogPO;
import cn.veasion.auto.model.ApiRequestPO;
import cn.veasion.auto.model.ProjectConfigPO;
import cn.veasion.auto.model.ProjectPO;
import cn.veasion.auto.utils.Constants;
import cn.veasion.auto.utils.EvalAnalysisUtils;
import cn.veasion.auto.utils.JavaScriptUtils;
import cn.veasion.auto.utils.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * ScriptServiceImpl
 *
 * @author luozhuowei
 * @date 2021/9/17
 */
@Slf4j
@Service
public class ScriptServiceImpl implements ScriptService {

    @Resource
    private ScriptExecutor scriptExecutor;
    @Resource
    private ProjectService projectService;
    @Resource
    private ApiRequestService apiRequestService;
    @Resource
    private Set<ScriptBindBean> scriptBindBeans;

    @Override
    public Object runScript(JSONObject object) {
        String script = object.getString("script");
        Integer projectId = object.getInteger("projectId");
        JSONObject envMap = object.getJSONObject("envMap");
        if (script == null) {
            throw new BusinessException("脚本不能为空");
        }
        if (projectId == null) {
            throw new BusinessException("projectId不能为空");
        }
        boolean beforeScript = Optional.ofNullable(object.getBoolean("beforeScript")).orElse(true);
        boolean afterScript = Optional.ofNullable(object.getBoolean("afterScript")).orElse(true);
        ProjectPO projectPO = projectService.getById(projectId);
        if (projectPO == null) {
            throw new BusinessException("项目不存在");
        }
        ProjectConfigPO projectConfig = projectPO.getProjectConfig();
        Map<String, Object> resultMap = new HashMap<>();
        ScriptContext scriptContext = scriptExecutor.createScriptContext(projectPO);
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
        return resultMap;
    }

    @Override
    public String toScript(Integer id, Integer projectId, String apiName, Boolean var) {
        ApiRequestPO obj;
        if (id != null) {
            obj = apiRequestService.getById(id);
        } else {
            obj = apiRequestService.queryByApiName(apiName, projectId);
        }
        if (obj == null) {
            throw new BusinessException("请求不存在");
        }
        StringBuilder sb = new StringBuilder();
        sb.append("// ").append(obj.getApiDesc().replace(Constants.LINE, ""));
        sb.append(Constants.LINE);
        if (Boolean.TRUE.equals(var)) {
            sb.append("let response = ");
        }
        sb.append("http.request('").append(obj.getApiName()).append("'");
        Set<String> keys = EvalAnalysisUtils.matcherKeys(obj.getUrl() + obj.getBody() + obj.getHeadersJson(), false);
        if (keys.size() > 0) {
            handleScriptParams(obj.getProjectId(), sb, keys);
        }
        sb.append(");");
        return sb.toString();
    }

    @Override
    public Map<String, Object> codeTips() {
        Map<String, Object> varCodes = new HashMap<>();
        Map<String, Object> root = new HashMap<>();
        for (ScriptBindBean scriptBindBean : scriptBindBeans) {
            Map<String, Object> map = new LinkedHashMap<>();
            varCodes.put(scriptBindBean.var(), map);
            root.put(scriptBindBean.var(), map);
            List<String> list = JavaScriptUtils.methodNames(scriptBindBean.getClass(), true, new String[]{"var", "root", "reset"},
                    ScriptBindBean.class, AbstractScriptBindBean.class);
            if (list.size() > 0) {
                for (String method : list) {
                    map.put(method, null);
                    if (scriptBindBean.root()) {
                        varCodes.put(method, null);
                    }
                }
            }
        }
        Map<String, Object> scriptContext = new LinkedHashMap<>();
        scriptContext.put("root", root);
        scriptContext.put("projectId", null);
        scriptContext.put("project", buildTips(ProjectPO.class));
        scriptContext.put("refLog", buildTips(ApiLogPO.class));
        scriptContext.put("strategy", buildTips(ApiExecuteStrategyPO.class));
        scriptContext.put("apiLogList", null);
        scriptContext.put("requestProcessor(request)", null);
        scriptContext.put("responseProcessor(response, httpStatus, log)", null);
        varCodes.put(ScriptExecutor.SCRIPT_CONTEXT_VAR, scriptContext);
        return varCodes;
    }

    @Override
    public Map<String, Object> apiResponseTips(Integer projectId, Map<String, String> varApiNameMap, Integer maxTimeoutOfSeconds) {
        ProjectPO projectPO = projectService.getById(projectId);
        if (projectPO == null) {
            throw new BusinessException("项目不存在");
        }
        Map<String, Object> resultMap = new HashMap<>();
        Map<String, Object> tempVar = new HashMap<>();
        Map<String, Object> apiNameMap = new HashMap<>();
        resultMap.put("tempVar", tempVar);
        resultMap.put("apiNameMap", apiNameMap);
        ProjectConfigPO projectConfig = projectPO.getProjectConfig();
        ScriptContext scriptContext = scriptExecutor.createScriptContext(projectPO);
        if (projectConfig != null && StringUtils.hasText(projectConfig.getBeforeScript())) {
            try {
                scriptExecutor.executeScript(projectConfig.getBeforeScript(), scriptContext);
            } catch (Exception e) {
                resultMap.put("beforeScript", e.getClass().getSimpleName() + ": " + e.getMessage());
            }
        }
        ExecutorService executor = Executors.newFixedThreadPool(varApiNameMap.size());
        List<Future<?>> tasks = new ArrayList<>(varApiNameMap.size());
        for (Map.Entry<String, String> entry : varApiNameMap.entrySet()) {
            tasks.add(executor.submit(() -> {
                String apiName = entry.getValue();
                ApiRequestPO apiRequestPO = apiRequestService.queryByApiName(apiName, projectId);
                if (apiRequestPO == null) {
                    return null;
                }
                try {
                    Set<String> varKeys = EvalAnalysisUtils.matcherKeys(apiRequestPO.getUrl() + apiRequestPO.getBody() + apiRequestPO.getHeadersJson(), true);
                    if (varKeys.size() > 0) {
                        Map<String, Object> map = new HashMap<>();
                        varKeys.forEach(k -> map.put(k, null));
                        apiNameMap.put(apiName, map);
                    }
                    Object response = scriptExecutor.execute(apiRequestPO, scriptContext);
                    if (response instanceof Map || response instanceof Collection) {
                        tempVar.put(entry.getKey(), response);
                    }
                } catch (Exception e) {
                    log.error("请求异常, http.request('{}')", apiName, e);
                }
                return null;
            }));
        }
        executor.shutdown();
        try {
            if (maxTimeoutOfSeconds == null || maxTimeoutOfSeconds <= 0) {
                maxTimeoutOfSeconds = 5;
            }
            if (!executor.awaitTermination(maxTimeoutOfSeconds, TimeUnit.SECONDS)) {
                for (Future<?> task : tasks) {
                    task.cancel(true);
                }
            }
        } catch (Exception e) {
            log.error("取消请求任务异常", e);
        }
        return resultMap;
    }

    private Map<String, Object> buildTips(Class<?> clazz) {
        Map<String, Object> map = new HashMap<>();
        List<String> list = JavaScriptUtils.methodNames(clazz, true, new String[]{"isAvailable", "isDeleted"});
        for (String method : list) {
            map.put(method, null);
        }
        return map;
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
                if (key.contains("|")) {
                    String[] array = key.split("\\|");
                    params.put(array[0], array[1]);
                } else {
                    params.put(key, null);
                }
            }
            sb.append(", ");
            sb.append(params.toString(SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue).replace("\":", "\": "));
        }
    }
}
