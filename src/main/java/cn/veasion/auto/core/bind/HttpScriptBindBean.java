package cn.veasion.auto.core.bind;

import cn.veasion.auto.exception.BusinessException;
import cn.veasion.auto.model.ApiLogPO;
import cn.veasion.auto.model.ApiRequestPO;
import cn.veasion.auto.model.ProjectConfigPO;
import cn.veasion.auto.service.ApiRequestService;
import cn.veasion.auto.utils.Constants;
import cn.veasion.auto.utils.HttpUtils;
import cn.veasion.auto.utils.JavaScriptUtils;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * HttpScriptBindBean
 *
 * @author luozhuowei
 * @date 2021/9/11
 */
@Slf4j
@Component
public class HttpScriptBindBean extends AbstractScriptBindBean {

    @Resource
    private ApiRequestService apiRequestService;

    public Object request(String apiName) {
        return request(apiName, null);
    }

    @SuppressWarnings("unchecked")
    public Object request(String apiName, Object params) {
        ApiRequestPO requestPO = apiRequestService.queryByApiName(apiName, scriptContext.getProjectId());
        if (requestPO == null) {
            throw new BusinessException("请求不存在：" + apiName);
        }
        if (!JavaScriptUtils.isNull(params)) {
            if (params instanceof String) {
                params = JSON.parseObject((String) params);
            }
            params = JavaScriptUtils.toJavaObject(params);
            return request(requestPO, (Map<String, Object>) params);
        } else {
            return request(requestPO, null);
        }
    }

    public Object request(ApiRequestPO requestPO, Map<String, Object> params) {
        String method = requestPO.getMethod();
        String url = eval(requestPO.getUrl(), params);
        String body = eval(requestPO.getBody(), params);
        String headersJson = requestPO.getHeadersJson();
        Map<String, String> headers;
        if (StringUtils.hasText(headersJson)) {
            headersJson = eval(headersJson, params);
            headers = toMap(JSON.parseObject(headersJson));
        } else {
            headers = null;
        }
        if (!StringUtils.hasText(method)) {
            method = "GET";
        }
        return execute(requestPO, HttpUtils.HttpRequest.build(url, method).setBody(body).setHeaders(headers));
    }

    public Object request(String url, String method, Object body, Object headers) {
        String reqUrl = eval(url);
        return execute(HttpUtils.HttpRequest.build(reqUrl, method).setBody(toString(body)).setHeaders(toMap(headers)));
    }

    public Object postJson(String url, Object json) {
        String reqUrl = eval(url);
        String body = toString(json);
        return execute(HttpUtils.HttpRequest.build(reqUrl, "POST").setBody(body).addHeaders(HttpUtils.CONTENT_TYPE, HttpUtils.CONTENT_TYPE_JSON));
    }

    public Object postFormData(String url, String body) {
        String reqUrl = eval(url);
        return execute(HttpUtils.HttpRequest.build(reqUrl, "POST").setBody(body).addHeaders(HttpUtils.CONTENT_TYPE, HttpUtils.CONTENT_TYPE_FORM_DATA));
    }

    public Object postFormDataByParam(String url, Object formDataParams, Object headers) {
        String reqUrl = eval(url);
        Map<String, String> formMap = toMap(formDataParams);
        Map<String, String> headerMap = toMap(headers);
        String body = buildLinks(formMap);
        return execute(HttpUtils.HttpRequest.build(reqUrl, "POST").setBody(body).setHeaders(headerMap).addHeaders(HttpUtils.CONTENT_TYPE, HttpUtils.CONTENT_TYPE_FORM_DATA));
    }

    public Object post(String url, Object body) {
        return post(url, body, null);
    }

    public Object post(String url, Object body, Object headers) {
        String reqUrl = eval(url);
        String bodyStr = toString(body);
        Map<String, String> headerMap = toMap(headers);
        return execute(HttpUtils.HttpRequest.build(reqUrl, "POST").setBody(bodyStr).setHeaders(headerMap));
    }

    public Object get(String url) {
        return get(url, null);
    }

    public Object get(String url, Object headers) {
        String reqUrl = eval(url);
        Map<String, String> headerMap = toMap(headers);
        return execute(HttpUtils.HttpRequest.build(reqUrl, "GET").setHeaders(headerMap));
    }

    public Object getByParams(String url, Object params) {
        return getByParams(url, params, null);
    }

    public Object getByParams(String url, Object params, Object headers) {
        Map<String, String> map = toMap(params);
        if (map != null && map.size() > 0) {
            url = url + (url.contains("?") ? "?" : "&") + buildLinks(map);
        }
        return get(url, headers);
    }

    private String buildLinks(Map<String, String> map) {
        if (map == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }
        sb.setLength(sb.length() - 1);
        return sb.toString();
    }

    private String eval(String str) {
        if (!StringUtils.hasText(str)) {
            return str;
        }
        Object val = scriptContext.getEnv().eval(str);
        return val == null ? str : val.toString();
    }

    private String eval(String str, Map<String, Object> params) {
        if (!StringUtils.hasText(str)) {
            return str;
        }
        Object val = scriptContext.getEnv().eval(str, params);
        return val == null ? str : val.toString();
    }

    private static String toString(Object data) {
        if (!JavaScriptUtils.isNull(data)) {
            data = JavaScriptUtils.toJavaObject(data);
        }
        if (!(data instanceof String)) {
            data = JSON.toJSONString(data);
        }
        return (String) data;
    }

    @SuppressWarnings("unchecked")
    private static Map<String, String> toMap(Object obj) {
        Map<String, String> result = null;
        if (!JavaScriptUtils.isNull(obj)) {
            result = new HashMap<>();
            Map<String, Object> map = (Map<String, Object>) JavaScriptUtils.toJavaObject(obj);
            for (String key : map.keySet()) {
                Object value = map.get(key);
                if (key != null && !JavaScriptUtils.isNull(value)) {
                    result.put(key, String.valueOf(value));
                }
            }
        }
        return result;
    }

    private Object execute(HttpUtils.HttpRequest request) {
        return execute(null, request);
    }

    private Object execute(ApiRequestPO requestPO, HttpUtils.HttpRequest request) {
        ProjectConfigPO projectConfig = scriptContext.getProject().getProjectConfig();
        boolean openReqLog = projectConfig != null && Constants.YES.equals(projectConfig.getOpenReqLog());
        ApiLogPO apiLogPO = scriptContext.buildApiLog(requestPO);
        apiLogPO.setUrl(request.getUrl());
        long timeMillis = System.currentTimeMillis();
        try {
            HttpUtils.HttpResponse response = HttpUtils.request(request);
            apiLogPO.setTime((int) response.getReqTime());
            apiLogPO.setStatus(response.success() ? ApiLogPO.STATUS_SUC : ApiLogPO.STATUS_FAIL);
            if (openReqLog) {
                apiLogPO.setMsg(buildReqLog(request, response));
            }
            Object result = handleResponse(response.getResponse());
            apiLogPO.setExecTime((int) (System.currentTimeMillis() - timeMillis));
            return result;
        } catch (Exception e) {
            log.error("请求接口失败: " + request.getUrl(), e);
            String msg = e.getClass().getSimpleName() + ": " + e.getMessage();
            if (openReqLog) {
                apiLogPO.setMsg(buildReqLog(request, null) + "\n\n" + msg);
            } else {
                apiLogPO.setMsg(msg);
            }
            apiLogPO.setTime((int) (System.currentTimeMillis() - timeMillis));
            apiLogPO.setExecTime(apiLogPO.getTime());
            apiLogPO.setStatus(ApiLogPO.STATUS_FAIL);
            return null;
        }
    }

    private static Object handleResponse(Object response) {
        if (response == null) {
            return null;
        }
        if (response instanceof String) {
            String str = ((String) response).trim();
            if (str.startsWith("{") || str.startsWith("[")) {
                try {
                    return JSON.parse(str);
                } catch (Exception e) {
                    if (log.isDebugEnabled()) {
                        log.debug("解析请求结果为json错误", e);
                    }
                }
            }
        }
        return response;
    }

    private String buildReqLog(HttpUtils.HttpRequest request, HttpUtils.HttpResponse response) {
        StringBuilder sb = new StringBuilder();
        if (StringUtils.hasText(request.getMethod())) {
            sb.append(request.getMethod()).append(" ");
        }
        sb.append(request.getUrl()).append("\r\n");
        if (request.getHeaders() != null && request.getHeaders().size() > 0) {
            for (Map.Entry<String, String> entry : request.getHeaders().entrySet()) {
                sb.append(entry.getKey()).append(": ").append(entry.getValue()).append("\r\n");
            }
        }
        sb.append("\r\n");
        if (request.getBody() != null) {
            sb.append(request.getBody()).append("\r\n\r\n");
        }
        if (response != null) {
            sb.append("\r\n");
            sb.append("status: ").append(response.getStatus());
            sb.append("\n\n======response======\n\n");
            sb.append(response.getResponse());
        }
        return sb.toString();
    }
}
