package cn.veasion.auto.core.bind;

import cn.veasion.auto.exception.BusinessException;
import cn.veasion.auto.model.ApiLogPO;
import cn.veasion.auto.model.ApiRequestPO;
import cn.veasion.auto.model.ProjectConfigPO;
import cn.veasion.auto.service.ApiRequestService;
import cn.veasion.auto.utils.Constants;
import cn.veasion.auto.utils.HttpClientUtils;
import cn.veasion.auto.utils.JavaScriptUtils;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

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
        boolean isGet = !StringUtils.hasText(method) || "GET".equalsIgnoreCase(method);
        return execute(requestPO, url, buildReqLog(method, url, body, headers), () -> {
            try {
                if (isGet) {
                    return IOUtils.toString(HttpClientUtils.get(url, headers), HttpClientUtils.CHARSET_DEFAULT);
                } else {
                    return HttpClientUtils.post(url, body, null, null, headers);
                }
            } catch (IOException e) {
                throw new BusinessException("请求异常", e);
            }
        });
    }

    public Object postJson(String url, Object json) {
        String reqUrl = eval(url);
        String body = toString(json);
        return execute(reqUrl, buildReqLog("POST", reqUrl, body, null), () -> {
            try {
                return HttpClientUtils.postJson(reqUrl, body);
            } catch (IOException e) {
                throw new BusinessException("请求异常", e);
            }
        });
    }

    public Object postForm(String url, Object postForm) {
        return postForm(url, postForm, null);
    }

    public Object postForm(String url, Object postForm, Object headers) {
        String reqUrl = eval(url);
        Map<String, String> formMap = toMap(postForm);
        Map<String, String> headerMap = toMap(headers);
        return execute(reqUrl, buildReqLog("POST", reqUrl, buildLinks(formMap), headerMap), () -> {
            try {
                return HttpClientUtils.postForm(reqUrl, formMap, headerMap);
            } catch (IOException e) {
                throw new BusinessException("请求异常", e);
            }
        });
    }

    public Object post(String url, Object body) {
        return post(url, body, null);
    }

    public Object post(String url, Object body, Object headers) {
        String reqUrl = eval(url);
        String bodyStr = toString(body);
        Map<String, String> headerMap = toMap(headers);
        return execute(reqUrl, buildReqLog("POST", reqUrl, bodyStr, headerMap), () -> {
            try {
                return HttpClientUtils.post(reqUrl, bodyStr, null, null, headerMap);
            } catch (IOException e) {
                throw new BusinessException("请求异常", e);
            }
        });
    }

    public Object get(String url) {
        return get(url, null);
    }

    public Object get(String url, Object headers) {
        String reqUrl = eval(url);
        Map<String, String> headerMap = toMap(headers);
        return execute(reqUrl, buildReqLog("GET", reqUrl, null, headerMap), () -> {
            try {
                return IOUtils.toString(HttpClientUtils.get(reqUrl, headerMap), HttpClientUtils.CHARSET_DEFAULT);
            } catch (IOException e) {
                throw new BusinessException("请求异常", e);
            }
        });
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
        Object val = scriptContext.getEnv().eval(str);
        return val == null ? str : val.toString();
    }

    private String eval(String str, Map<String, Object> params) {
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

    private Object execute(String url, Supplier<String> reqLog, Supplier<String> supplier) {
        return execute(null, url, reqLog, supplier);
    }

    private Object execute(ApiRequestPO requestPO, String url, Supplier<String> reqLog, Supplier<String> supplier) {
        ProjectConfigPO projectConfig = scriptContext.getProject().getProjectConfig();
        boolean openReqLog = projectConfig != null && Constants.YES.equals(projectConfig.getOpenReqLog());
        ApiLogPO apiLogPO = scriptContext.buildApiLog(requestPO);
        apiLogPO.setUrl(url);
        if (openReqLog) {
            apiLogPO.setMsg(reqLog.get());
        }
        long timeMillis = System.currentTimeMillis();
        try {
            String response = supplier.get();
            apiLogPO.setTime((int) (System.currentTimeMillis() - timeMillis));
            apiLogPO.setExecTime(apiLogPO.getTime());
            apiLogPO.setStatus(ApiLogPO.STATUS_SUC);
            if (openReqLog) {
                apiLogPO.setMsg(apiLogPO.getMsg() + "\n\n======response======\n\n" + response);
            }
            return handleResponse(response);
        } catch (Exception e) {
            log.error("请求接口失败: " + url, e);
            String msg = e.getClass().getSimpleName() + ": " + e.getMessage();
            if (openReqLog) {
                apiLogPO.setMsg(apiLogPO.getMsg() + "\n\n" + msg);
            } else {
                apiLogPO.setMsg(msg);
            }
            apiLogPO.setTime((int) (System.currentTimeMillis() - timeMillis));
            apiLogPO.setExecTime(apiLogPO.getTime());
            apiLogPO.setStatus(ApiLogPO.STATUS_FAIL);
            return null;
        }
    }

    private static Object handleResponse(String response) {
        if (StringUtils.hasText(response)) {
            String str = response.trim();
            if (str.startsWith("{") || str.startsWith("[")) {
                try {
                    return JSON.parse(response);
                } catch (Exception e) {
                    if (log.isDebugEnabled()) {
                        log.debug("解析请求结果为json错误", e);
                    }
                }
            }
        }
        return response;
    }

    private Supplier<String> buildReqLog(String method, String url, String body, Map<String, String> headers) {
        return () -> {
            StringBuilder sb = new StringBuilder();
            if (StringUtils.hasText(method)) {
                sb.append(method).append(" ");
            }
            sb.append(url).append("\r\n");
            if (headers != null && headers.size() > 0) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    sb.append(entry.getKey()).append(": ").append(entry.getValue()).append("\r\n");
                }
            }
            sb.append("\r\n");
            if (body != null) {
                sb.append(body).append("\r\n\r\n");
            }
            return sb.toString();
        };
    }
}
