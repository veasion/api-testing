package cn.veasion.auto.service;

import com.alibaba.fastjson.JSONObject;

import java.util.Map;

/**
 * ScriptService
 *
 * @author luozhuowei
 * @date 2021/9/17
 */
public interface ScriptService {

    Object runScript(JSONObject object);

    String toScript(Integer id, Integer projectId, String apiName);

    Map<String, Object> codeTips();

    Map<String, Object> apiResponseTips(Integer projectId, Map<String, String> varApiNameMap, Integer maxTimeoutOfSeconds);

}
