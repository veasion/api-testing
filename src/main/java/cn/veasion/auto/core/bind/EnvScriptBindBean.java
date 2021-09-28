package cn.veasion.auto.core.bind;

import cn.veasion.auto.utils.EvalAnalysisUtils;
import cn.veasion.auto.utils.JavaScriptUtils;
import com.alibaba.fastjson.JSON;
import jdk.nashorn.api.scripting.ScriptObjectMirror;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

/**
 * EnvScriptBindBean
 *
 * @author luozhuowei
 * @date 2021/9/11
 */
@Component
public class EnvScriptBindBean extends AbstractScriptBindBean {

    /**
     * 项目全局变量（生命周期: 全局）
     */
    private static final Map<Integer, Map<String, Object>> globalMap = new ConcurrentHashMap<>();
    /**
     * 脚本变量（生命周期: 单脚本）
     */
    private ThreadLocal<Map<String, Object>> envMap = ThreadLocal.withInitial(HashMap::new);

    public Object eval(String str) {
        return EvalAnalysisUtils.eval(str, (Function<String, ?>) this::get);
    }

    @SuppressWarnings("unchecked")
    public Object eval(String str, ScriptObjectMirror params) {
        return eval(str, (Map<String, Object>) JavaScriptUtils.toJavaObject(params));
    }

    public Object eval(String str, Map<String, Object> params) {
        return eval(str, params, false);
    }

    public Object eval(String str, Map<String, Object> params, boolean toStr) {
        return EvalAnalysisUtils.eval(str, (Function<String, ?>) key -> {
            Object obj = null;
            if (params != null && params.containsKey(key)) {
                obj = JavaScriptUtils.toJavaObject(params.get(key));
            }
            if (obj == null) {
                obj = get(key);
            }
            if (toStr && obj != null) {
                if (obj instanceof Map || obj instanceof List) {
                    obj = JSON.toJSONString(obj);
                } else {
                    obj = obj.toString();
                }
            }
            return obj;
        });
    }

    public void set(String key, Object value) {
        put(key, value);
    }

    public void put(String key, Object value) {
        envMap.get().put(key, value);
    }

    public void putAll(Map<String, Object> map) {
        envMap.get().putAll(map);
    }

    /**
     * 上下文变量（生命周期: 上下文/单策略所有脚本）
     */
    public void setContext(String key, Object value) {
        putContext(key, value);
    }

    /**
     * 上下文变量（生命周期: 上下文/单策略所有脚本）
     */
    public void putContext(String key, Object value) {
        scriptContext.getContextMap().put(key, value);
    }

    public Object get(String key) {
        return envMap.get().getOrDefault(key, scriptContext.getContextMap().getOrDefault(key, getGlobalMap().get(key)));
    }

    public Object getContext(String key) {
        return scriptContext.getContextMap().get(key);
    }

    public Object getGlobal(String key) {
        return getGlobalMap().get(key);
    }

    private Map<String, Object> getGlobalMap() {
        return getGlobalMap(scriptContext.getProjectId());
    }

    private static Map<String, Object> getGlobalMap(Integer projectId) {
        synchronized (globalMap) {
            if (!globalMap.containsKey(projectId)) {
                globalMap.put(projectId, new ConcurrentHashMap<>());
            }
            return globalMap.get(projectId);
        }
    }

    public static void setGlobalMap(Integer projectId, Map<String, Object> map) {
        synchronized (globalMap) {
            globalMap.put(projectId, new ConcurrentHashMap<>(map));
        }
    }

    public void reset() {
        envMap.remove();
    }

    @Override
    public ScriptBindBean getObject() {
        return new EnvScriptBindBean();
    }

}
