package cn.veasion.auto.core;

import cn.veasion.auto.core.bind.AbstractScriptBindBean;
import cn.veasion.auto.core.bind.EnvScriptBindBean;
import cn.veasion.auto.core.bind.ScriptBindBean;
import cn.veasion.auto.exception.BusinessException;
import cn.veasion.auto.model.ApiExecuteStrategyPO;
import cn.veasion.auto.model.ApiRequestPO;
import cn.veasion.auto.model.ApiTestCasePO;
import cn.veasion.auto.model.ProjectConfigPO;
import cn.veasion.auto.model.ProjectPO;
import cn.veasion.auto.service.ProjectService;
import cn.veasion.auto.utils.JavaScriptUtils;
import jdk.nashorn.api.scripting.NashornScriptEngineFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.script.Bindings;
import javax.script.ScriptEngine;
import javax.script.ScriptException;
import javax.script.SimpleScriptContext;
import java.util.Map;
import java.util.Set;

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
    @Resource
    private Set<ScriptBindBean> scriptBindBeans;

    public ScriptContext createScriptContext(Integer projectId) {
        ProjectPO projectPO = projectService.getById(projectId);
        if (projectPO == null) {
            throw new BusinessException("项目不存在: " + projectId);
        }
        ProjectConfigPO projectConfig = projectPO.getProjectConfig();
        if (projectConfig != null && StringUtils.hasText(projectConfig.getGlobalVarJson())) {
            EnvScriptBindBean.setGlobalMap(projectId, projectConfig.toGlobalVarJson());
        }
        ScriptContext scriptContext = new ScriptContext();
        scriptContext.setProjectId(projectId);
        scriptContext.setProject(projectPO);
        return scriptContext;
    }

    private ScriptEngine initScriptEngine(ScriptContext scriptContext) throws ScriptException {
        ScriptEngine scriptEngine = SCRIPT_ENGINE_FACTORY.getScriptEngine("--language=es6", "--no-java");
        scriptEngine.setContext(new SimpleScriptContext());
        Bindings bindings = scriptEngine.getBindings(ENGINE_BINDINGS_SCOPE);
        for (ScriptBindBean bindBean : scriptBindBeans) {
            String var = bindBean.var();
            ScriptBindBean object = bindBean.getObject();
            object.setScriptContext(scriptContext);
            bindings.put(var, object);
            scriptContext.getRoot().put(var, object);
            if (bindBean.root()) {
                scriptEngine.eval(JavaScriptUtils.generatorRootFunctionCode(
                        object.getClass(), var,
                        new String[]{"var", "root"},
                        ScriptBindBean.class, AbstractScriptBindBean.class));
            }
        }
        scriptContext.getEnv().reset();
        bindings.put("scriptContext", scriptContext);
        return scriptEngine;
    }

    public ScriptEngine getScriptEngine(ScriptContext scriptContext) throws ScriptException {
        return initScriptEngine(scriptContext);
    }

    /**
     * 执行脚本
     */
    public Object tryExecuteScript(String script, ScriptContext scriptContext) {
        try {
            return executeScript(script, scriptContext, null);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 执行脚本
     */
    public Object executeScript(String script, ScriptContext scriptContext) throws ScriptException {
        return executeScript(script, scriptContext, null);
    }

    /**
     * 执行脚本
     */
    public Object executeScript(String script, ScriptContext scriptContext, Map<String, Object> params) throws ScriptException {
        try {
            scriptContext.getThreadLocalCase().set(null);
            ScriptEngine scriptEngine = getScriptEngine(scriptContext);
            if (params != null && params.size() > 0) {
                for (Map.Entry<String, Object> entry : params.entrySet()) {
                    scriptContext.getEnv().put(entry.getKey(), entry.getValue());
                }
            }
            return scriptEngine.eval(script);
        } catch (Exception e) {
            log.error("执行脚本失败，script: {}", script, e);
            throw e;
        } finally {
            scriptContext.getEnv().reset();
        }
    }

    /**
     * 执行策略脚本
     */
    public Object execute(ApiExecuteStrategyPO strategyPO, ScriptContext scriptContext) throws ScriptException {
        try {
            scriptContext.getThreadLocalCase().set(null);
            return getScriptEngine(scriptContext).eval(strategyPO.getScript());
        } catch (Exception e) {
            log.error("执行脚本失败，策略: {}", strategyPO.getName(), e);
            throw e;
        } finally {
            scriptContext.getEnv().reset();
        }
    }

    /**
     * 执行用例脚本
     */
    public Object execute(ApiTestCasePO testCasePO, ScriptContext scriptContext) throws ScriptException {
        try {
            scriptContext.getThreadLocalCase().set(testCasePO);
            return getScriptEngine(scriptContext).eval(testCasePO.getScript());
        } catch (Exception e) {
            log.error("执行脚本失败，用例: {}", testCasePO.getCaseName(), e);
            throw e;
        } finally {
            scriptContext.getEnv().reset();
            scriptContext.getThreadLocalCase().remove();
        }
    }

    /**
     * 执行单个请求
     */
    public Object execute(ApiRequestPO apiRequestPO, ScriptContext scriptContext) throws ScriptException {
        String apiName = apiRequestPO.getApiName();
        try {
            scriptContext.getThreadLocalCase().set(null);
            return getScriptEngine(scriptContext).eval(String.format("http.request('%s')", apiName));
        } catch (Exception e) {
            log.error("执行脚本失败，apiName: {}", apiName, e);
            throw e;
        } finally {
            scriptContext.getEnv().reset();
            scriptContext.getThreadLocalCase().remove();
        }
    }

}
