package cn.veasion.auto.core.bind;

import cn.veasion.auto.utils.EvalAnalysisUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.HashMap;
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
@Scope("prototype")
public class EnvScriptBindBean extends AbstractScriptBindBean {

    public static final Map<Integer, Map<String, Object>> globalMap = new ConcurrentHashMap<>();
    private Map<String, Object> envMap = new HashMap<>();

    public Object eval(String str) {
        return EvalAnalysisUtils.eval(str, (Function<String, ?>) this::get);
    }

    public void set(String key, Object value) {
        put(key, value);
    }

    public void put(String key, Object value) {
        envMap.put(key, value);
    }

    public void setGlobal(String key, Object value) {
        putGlobal(key, value);
    }

    public void putGlobal(String key, Object value) {
        getGlobalMap().put(key, value);
    }

    public Object get(String key) {
        return envMap.getOrDefault(key, getGlobalMap().get(key));
    }

    public Object getGlobal(String key) {
        return getGlobalMap().get(key);
    }

    private Map<String, Object> getGlobalMap() {
        return getGlobalMap(scriptContext.getProjectId());
    }

    public Map<String, Object> getGlobalMap(Integer projectId) {
        if (!globalMap.containsKey(projectId)) {
            globalMap.put(projectId, new ConcurrentHashMap<>());
        }
        return globalMap.get(projectId);
    }

    public void setGlobalMap(Integer projectId, Map<String, Object> map) {
        globalMap.put(projectId, new ConcurrentHashMap<>(map));
    }

}
