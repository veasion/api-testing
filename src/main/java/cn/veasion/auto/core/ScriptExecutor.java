package cn.veasion.auto.core;

import cn.veasion.auto.config.SpringBeanUtils;
import cn.veasion.auto.core.bind.EnvScriptBindBean;
import cn.veasion.auto.core.bind.ScriptBindBean;
import cn.veasion.auto.exception.BusinessException;
import cn.veasion.auto.model.ApiExecuteStrategyPO;
import cn.veasion.auto.model.ApiRequestPO;
import cn.veasion.auto.model.ApiTestCasePO;
import cn.veasion.auto.model.ProjectConfigPO;
import cn.veasion.auto.model.ProjectPO;
import cn.veasion.auto.service.ProjectService;
import jdk.nashorn.api.scripting.NashornScriptEngineFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.script.Bindings;
import javax.script.ScriptEngine;
import javax.script.SimpleScriptContext;
import java.util.Map;

/**
 * ScriptExecutor
 *
 * @author luozhuowei
 * @date 2021/9/11
 */
@Slf4j
@Component
public class ScriptExecutor {

    private static final NashornScriptEngineFactory SCRIPT_ENGINE_FACTORY;
    private static final int ENGINE_BINDINGS_SCOPE = SimpleScriptContext.ENGINE_SCOPE;

    static {
        SCRIPT_ENGINE_FACTORY = new NashornScriptEngineFactory();
    }

    @Resource
    private ProjectService projectService;

    public ScriptContext createScriptContext(Integer projectId) {
        ProjectPO projectPO = projectService.getById(projectId);
        if (projectPO == null) {
            throw new BusinessException("项目不存在: " + projectId);
        }
        ProjectConfigPO projectConfig = projectPO.getProjectConfig();
        if (projectConfig != null && StringUtils.hasText(projectConfig.getGlobalVarJson())) {
            EnvScriptBindBean.globalMap.put(projectId, projectConfig.toGlobalVarJson());
        }
        ScriptContext scriptContext = new ScriptContext();
        scriptContext.setProjectId(projectId);
        scriptContext.setProject(projectPO);
        return scriptContext;
    }

    private ScriptEngine initScriptEngine(ScriptContext scriptContext) {
        Map<String, ScriptBindBean> scriptBindBeanMap = SpringBeanUtils.getBeanOfType(ScriptBindBean.class);
        ScriptEngine scriptEngine = SCRIPT_ENGINE_FACTORY.getScriptEngine("--language=es6", "--no-java");
        scriptEngine.setContext(new SimpleScriptContext());
        Bindings bindings = scriptEngine.getBindings(ENGINE_BINDINGS_SCOPE);
        for (ScriptBindBean bindBean : scriptBindBeanMap.values()) {
            String var = bindBean.var();
            ScriptBindBean object = bindBean.getObject();
            bindings.put(var, object);
            scriptContext.getRoot().put(var, object);
        }
        bindings.put("scriptContext", scriptContext);
        return scriptEngine;
    }

    public ScriptEngine getScriptEngine(ScriptContext scriptContext) {
        return initScriptEngine(scriptContext);
    }

    /**
     * 执行脚本
     */
    public Object executeScript(String script, ScriptContext scriptContext) {
        try {
            scriptContext.getThreadLocalCase().set(null);
            return getScriptEngine(scriptContext).eval(script);
        } catch (Exception e) {
            log.error("执行脚本失败，script: {}", script, e);
            return null;
        }
    }

    /**
     * 执行策略脚本
     */
    public Object execute(ApiExecuteStrategyPO strategyPO, ScriptContext scriptContext) {
        try {
            scriptContext.getThreadLocalCase().set(null);
            return getScriptEngine(scriptContext).eval(strategyPO.getScript());
        } catch (Exception e) {
            log.error("执行脚本失败，策略: {}", strategyPO.getName(), e);
            return null;
        }
    }

    /**
     * 执行用例脚本
     */
    public Object execute(ApiTestCasePO testCasePO, ScriptContext scriptContext) {
        try {
            scriptContext.getThreadLocalCase().set(testCasePO);
            return getScriptEngine(scriptContext).eval(testCasePO.getScript());
        } catch (Exception e) {
            log.error("执行脚本失败，用例: {}", testCasePO.getCaseName(), e);
            return null;
        } finally {
            scriptContext.getThreadLocalCase().remove();
        }
    }

    /**
     * 执行单个请求
     */
    public Object execute(ApiRequestPO apiRequestPO, ScriptContext scriptContext) {
        String apiName = apiRequestPO.getApiName();
        try {
            scriptContext.getThreadLocalCase().set(null);
            return getScriptEngine(scriptContext).eval(String.format("http.request('%s')", apiName));
        } catch (Exception e) {
            log.error("执行脚本失败，apiName: {}", apiName, e);
            return null;
        } finally {
            scriptContext.getThreadLocalCase().remove();
        }
    }

}
