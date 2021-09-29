package cn.veasion.auto.utils;

import cn.veasion.auto.exception.BusinessException;
import cn.veasion.auto.model.ApiRequestPO;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * SwaggerUtils
 *
 * @author luozhuowei
 * @date 2021/9/29
 */
public class SwaggerUtils {

    public static List<ApiRequestPO> swaggerToApiRequest(String swaggerUrl) throws Exception {
        List<ApiRequestPO> list = new ArrayList<>(100);
        JSONArray resources = getSwaggerResources(swaggerUrl);
        for (int i = 0; i < resources.size(); i++) {
            list.addAll(swaggerDocsToApiRequest(resources.getJSONObject(i).getString("url")));
        }
        return list;
    }

    public static JSONArray getSwaggerResources(String swaggerUrl) throws Exception {
        String params = null;
        swaggerUrl = swaggerUrl.trim();
        int idx = swaggerUrl.lastIndexOf("?");
        if (idx > -1) {
            params = swaggerUrl.substring(idx + 1);
            swaggerUrl = swaggerUrl.substring(0, idx);
        }
        if (swaggerUrl.endsWith("/v2/api-docs") || (swaggerUrl.endsWith("/api-docs") && params != null && params.contains("group="))) {
            JSONArray result = new JSONArray();
            JSONObject object = new JSONObject();
            if (params != null) {
                object.put("name", params.substring(params.indexOf("group=") + "group=".length()).trim().split("&")[0]);
            }
            object.put("url", params != null ? (swaggerUrl + "?" + params) : swaggerUrl);
            result.add(object);
            return result;
        }
        String baseUrl;
        if (swaggerUrl.endsWith("/swagger-ui.html")) {
            baseUrl = swaggerUrl.substring(0, swaggerUrl.indexOf("/swagger-ui.html"));
        } else if (swaggerUrl.contains("/swagger")) {
            baseUrl = swaggerUrl.substring(0, swaggerUrl.lastIndexOf("/swagger"));
        } else {
            baseUrl = swaggerUrl;
        }
        String url = baseUrl + "/swagger-resources";
        HttpUtils.HttpResponse response = HttpUtils.request(HttpUtils.HttpRequest.build(url, "GET"));
        if (!response.success()) {
            throw new BusinessException("请求失败: " + url);
        }
        JSONArray result = JSON.parseArray(response.getResponseToString());
        for (int i = 0; i < result.size(); i++) {
            JSONObject object = result.getJSONObject(i);
            String u = object.getString("url").trim();
            if (u.startsWith("/")) {
                object.put("url", baseUrl + u);
            }
        }
        return result;
    }

    public static List<ApiRequestPO> swaggerDocsToApiRequest(String apiDocsUrl) throws Exception {
        HttpUtils.HttpResponse response = HttpUtils.request(HttpUtils.HttpRequest.build(apiDocsUrl, "GET"));
        if (!response.success()) {
            throw new BusinessException("请求失败: " + apiDocsUrl);
        }

        JSONObject object = JSON.parseObject(response.getResponseToString().replace("\"$ref\"", "\"_$ref\""));
        String basePath = object.getString("basePath");
        JSONObject paths = object.getJSONObject("paths");
        Set<String> uris = paths.keySet();

        ApiRequestPO apiRequest;
        List<ApiRequestPO> result = new ArrayList<>(uris.size());
        for (String uri : uris) {
            JSONObject apiObj = paths.getJSONObject(uri);
            if (apiObj == null || apiObj.isEmpty()) {
                continue;
            }
            String method = apiObj.keySet().iterator().next();
            apiObj = apiObj.getJSONObject(method);
            String desc = apiObj.getString("summary");
            if (StringUtils.isEmpty(desc)) {
                desc = apiObj.getJSONArray("tags").get(0).toString();
            }
            apiRequest = new ApiRequestPO();
            String url = basePath + uri;
            apiRequest.setApiName(apiName(url));
            apiRequest.setMethod(method.toUpperCase());
            apiRequest.setApiDesc(desc);
            JSONArray consumes = apiObj.getJSONArray("consumes");
            if (consumes != null && !consumes.isEmpty()) {
                if (consumes.toString().contains("application/json")) {
                    apiRequest.setHeadersJson("{\"Content-Type\":\"application/json\"}");
                } else if (consumes.toString().contains("x-www-form-urlencoded")) {
                    apiRequest.setHeadersJson("{\"Content-Type\":\"application/x-www-form-urlencoded\"}");
                } else if (consumes.toString().contains("text/plain")) {
                    apiRequest.setHeadersJson("{\"Content-Type\":\"text/plain\"}");
                } else {
                    apiRequest.setHeadersJson("{\"Content-Type\":\"" + consumes.getString(0).trim() + "\"}");
                }
            }
            JSONObject definitions = object.getJSONObject("definitions");
            JSONArray parameters = apiObj.getJSONArray("parameters");
            apiRequest.setUrl(buildParamUrl(url, parameters));
            apiRequest.setBody(buildBody(parameters, definitions));
            result.add(apiRequest);
        }
        return result;
    }

    private static String buildParamUrl(String url, JSONArray parameters) {
        if (parameters == null || parameters.isEmpty()) {
            return url;
        }
        StringBuilder sb = new StringBuilder(url).append("?");
        for (int i = 0; i < parameters.size(); i++) {
            JSONObject object = parameters.getJSONObject(i);
            if (Boolean.TRUE.equals(object.getBoolean("required")) && "query".equals(object.getString("in"))) {
                String name = object.getString("name").trim();
                sb.append(name).append("=${").append(name);
                if (object.containsKey("defaultValue")) {
                    sb.append("|").append(object.getString("defaultValue"));
                }
                sb.append("}&");
            }
        }
        sb.setLength(sb.length() - 1);
        return sb.toString();
    }

    private static String buildBody(JSONArray parameters, JSONObject definitions) {
        if (parameters == null || parameters.isEmpty()) {
            return null;
        }
        String body = null;
        for (int i = 0; i < parameters.size(); i++) {
            JSONObject object = parameters.getJSONObject(i);
            if (Boolean.TRUE.equals(object.getBoolean("required")) && "body".equals(object.getString("in"))) {
                body = "${body}";
                JSONObject schema = object.getJSONObject("schema");
                if (schema == null) {
                    continue;
                }
                String ref = schema.getString("_$ref");
                JSONObject bodyParam = getDefinitions(definitions, ref);
                if (bodyParam != null) {
                    body = bodyParam.toJSONString();
                }
                break;
            }
        }
        return body;
    }

    private static JSONObject getDefinitions(JSONObject definitions, String ref) {
        if (ref == null) {
            return null;
        }
        ref = ref.replace("#/definitions/", "");
        JSONObject object = definitions.getJSONObject(ref);
        if (object == null || !"object".equals(object.getString("type"))) {
            return null;
        }
        JSONObject result = new JSONObject();
        JSONObject properties = object.getJSONObject("properties");
        Set<String> keySet = properties.keySet();
        for (String key : keySet) {
            JSONObject value = properties.getJSONObject(key);
            String type = value.getString("type");
            if (!"array".equals(type)) {
                result.put(key, "${" + key + "}");
            }
        }
        return result;
    }

    private static String apiName(String url) {
        url = url.trim();
        if (url.length() <= 50) {
            return url;
        }
        return url.substring(0, 50);
    }

}
