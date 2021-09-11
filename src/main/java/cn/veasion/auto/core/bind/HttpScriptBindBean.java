package cn.veasion.auto.core.bind;

import cn.veasion.auto.exception.BusinessException;
import cn.veasion.auto.model.ApiLogPO;
import cn.veasion.auto.model.ApiRequestPO;
import cn.veasion.auto.service.ApiLogService;
import cn.veasion.auto.service.ApiRequestService;
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
    private ApiLogService apiLogService;
    @Resource
    private ApiRequestService apiRequestService;

    public Object request(String apiName) {
        ApiRequestPO requestPO = apiRequestService.queryByApiName(apiName);
        if (requestPO == null) {
            throw new BusinessException("请求不存在：" + apiName);
        }
        return request(requestPO);
    }

    public Object request(ApiRequestPO requestPO) {
        String method = requestPO.getMethod();
        String url = eval(requestPO.getUrl());
        String body = eval(requestPO.getBody());
        String headersJson = requestPO.getHeadersJson();
        Map<String, String> headers;
        if (StringUtils.hasText(headersJson)) {
            headersJson = eval(headersJson);
            headers = toMap(JSON.parseObject(headersJson));
        } else {
            headers = null;
        }
        boolean isGet = !StringUtils.hasText(method) || "GET".equalsIgnoreCase(method);
        return execute(requestPO, url, () -> {
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
        return execute(url, () -> {
            try {
                return HttpClientUtils.postJson(eval(url), toString(json));
            } catch (IOException e) {
                throw new BusinessException("请求异常", e);
            }
        });
    }

    public Object postForm(String url, Object postForm, Object headers) {
        return execute(url, () -> {
            try {
                return HttpClientUtils.postForm(eval(url), toMap(postForm), toMap(headers));
            } catch (IOException e) {
                throw new BusinessException("请求异常", e);
            }
        });
    }

    public Object post(String url, Object body, Object headers) {
        return execute(url, () -> {
            try {
                return HttpClientUtils.post(eval(url), toString(body), null, null, toMap(headers));
            } catch (IOException e) {
                throw new BusinessException("请求异常", e);
            }
        });
    }

    public Object get(String url, Object headers) {
        return execute(url, () -> {
            try {
                return IOUtils.toString(HttpClientUtils.get(eval(url), toMap(headers)), HttpClientUtils.CHARSET_DEFAULT);
            } catch (IOException e) {
                throw new BusinessException("请求异常", e);
            }
        });
    }

    public Object getByParams(String url, Object params, Object headers) {
        Map<String, String> map = toMap(params);
        if (map != null) {
            StringBuilder sb = new StringBuilder(url);
            sb.append(url.contains("?") ? "?" : "&");
            for (Map.Entry<String, String> entry : map.entrySet()) {
                sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
            }
            sb.setLength(sb.length() - 1);
            url = sb.toString();
        }
        return get(eval(url), headers);
    }

    private String eval(String str) {
        Object val = scriptContext.getEnv().eval(str);
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

    private Object execute(String url, Supplier<String> supplier) {
        return execute(null, url, supplier);
    }

    private Object execute(ApiRequestPO requestPO, String url, Supplier<String> supplier) {
        ApiLogPO apiLogPO = scriptContext.buildApiLog(requestPO);
        apiLogPO.setUrl(url);
        apiLogService.addWithNewTx(apiLogPO);
        long timeMillis = System.currentTimeMillis();
        try {
            String response = supplier.get();
            apiLogPO.setTime((int) (System.currentTimeMillis() - timeMillis));
            apiLogPO.setExecTime(apiLogPO.getTime());
            apiLogPO.setStatus(ApiLogPO.STATUS_SUC);
            apiLogService.updateWithNewTx(apiLogPO);
            return handleResponse(response);
        } catch (Exception e) {
            log.error("请求接口失败: " + url, e);
            apiLogPO.setMsg(e.getMessage());
            apiLogPO.setTime((int) (System.currentTimeMillis() - timeMillis));
            apiLogPO.setExecTime(apiLogPO.getTime());
            apiLogPO.setStatus(ApiLogPO.STATUS_FAIL);
            apiLogService.updateWithNewTx(apiLogPO);
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
}
