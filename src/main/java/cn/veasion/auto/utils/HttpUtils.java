package cn.veasion.auto.utils;

import com.alibaba.fastjson.JSON;
import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.util.StringUtils;

import javax.net.ssl.SSLContext;
import javax.net.ssl.X509TrustManager;
import java.io.File;
import java.io.InputStream;
import java.io.Serializable;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * HttpUtils
 *
 * @author luozhuowei
 * @date 2021/9/13
 */
public class HttpUtils {

    public static final String CHARSET_DEFAULT = "UTF-8";
    public static final String CONTENT_TYPE = "Content-Type";
    public static final String CONTENT_TYPE_JSON = "application/json";
    public static final String CONTENT_TYPE_FORM_DATA = "application/x-www-form-urlencoded";
    private static final PoolingHttpClientConnectionManager CONNECTION_MANAGER;

    static {
        // http
        CONNECTION_MANAGER = new PoolingHttpClientConnectionManager();
        CONNECTION_MANAGER.setMaxTotal(200);
        CONNECTION_MANAGER.setDefaultMaxPerRoute(100);
    }

    /**
     * 通用接口请求
     */
    public static HttpResponse request(HttpRequest request) throws Exception {
        HttpRequestBase requestBase = toRequest(request);
        Map<String, String> headers = request.getHeaders();
        ContentType contentType = null;
        if (headers != null && headers.size() > 0) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                String key = entry.getKey();
                if (!StringUtils.hasText(key)) {
                    continue;
                }
                String value = entry.getValue();
                if (CONTENT_TYPE.equalsIgnoreCase(key) && value != null) {
                    contentType = ContentType.parse(value);
                }
                requestBase.setHeader(key, value);
            }
        }

        // body
        setBodyEntity(requestBase, contentType, request.getBody());

        HttpClient client = getHttpClient(request.getUrl(), requestBase);
        long startTime = System.currentTimeMillis();
        org.apache.http.HttpResponse response = client.execute(requestBase);

        HttpResponse httpResponse = new HttpResponse();
        httpResponse.setReqTime(System.currentTimeMillis() - startTime);
        httpResponse.setStatus(response.getStatusLine().getStatusCode());
        Header[] allHeaders = response.getAllHeaders();
        if (allHeaders != null && allHeaders.length > 0) {
            httpResponse.setHeaders(new HashMap<>());
            for (Header header : allHeaders) {
                httpResponse.getHeaders().put(header.getName(), header.getValue());
            }
        }
        if (request.responseHandler != null) {
            httpResponse.setResponse(request.responseHandler.apply(response.getEntity()));
        } else {
            String charset = CHARSET_DEFAULT;
            if (contentType != null && contentType.getCharset() != null) {
                charset = contentType.getCharset().name();
            }
            httpResponse.setResponse(IOUtils.toString(response.getEntity().getContent(), charset));
        }
        return httpResponse;
    }

    private static void setBodyEntity(HttpRequestBase requestBase, ContentType contentType, Object body) {
        if (body != null && requestBase instanceof HttpEntityEnclosingRequest) {
            HttpEntityEnclosingRequest entityRequest = (HttpEntityEnclosingRequest) requestBase;
            if (body instanceof String) {
                entityRequest.setEntity(getStringEntity((String) body, contentType));
            } else if (body instanceof byte[]) {
                entityRequest.setEntity(new ByteArrayEntity((byte[]) body, contentType));
            } else if (body instanceof File) {
                entityRequest.setEntity(new FileEntity((File) body, contentType));
            } else if (body instanceof InputStream) {
                entityRequest.setEntity(new InputStreamEntity((InputStream) body, contentType));
            } else if (ContentType.APPLICATION_JSON.equals(contentType)) {
                entityRequest.setEntity(getStringEntity(JSON.toJSONString(body), contentType));
            } else {
                entityRequest.setEntity(getStringEntity(body.toString(), contentType));
            }
        }
    }

    private static StringEntity getStringEntity(String body, ContentType contentType) {
        if (contentType != null && contentType.getCharset() != null) {
            return new StringEntity(body, contentType);
        } else {
            return new StringEntity(body, CHARSET_DEFAULT);
        }
    }

    public static HttpRequestBase toRequest(HttpRequest request) {
        String url = request.getUrl();
        String method = request.getMethod();
        if (!StringUtils.hasText(url)) {
            throw new RuntimeException("url不能为空");
        }
        if (!StringUtils.hasText(method)) {
            method = "GET";
        }
        switch (method.toUpperCase()) {
            case "GET":
                return new HttpGet(url);
            case "POST":
                return new HttpPost(url);
            case "PUT":
                return new HttpPut(url);
            case "PATCH":
                return new HttpPatch(url);
            case "DELETE":
                return new HttpDelete(url);
            default:
                throw new RuntimeException("不支持的请求方式：" + method);
        }
    }

    private static HttpClient getHttpClient(String url, HttpRequestBase request) {
        RequestConfig.Builder customReqConf = RequestConfig.custom();
        request.setConfig(customReqConf.build());
        if (isHttps(url)) {
            try {
                // ssl https
                SSLContext context = SSLContext.getInstance("TLS");
                context.init(null, new X509TrustManager[]{new X509TrustManager() {
                    public void checkClientTrusted(X509Certificate[] x509Certificates, String s) {
                    }

                    public void checkServerTrusted(X509Certificate[] x509Certificates, String s) {
                    }

                    public X509Certificate[] getAcceptedIssuers() {
                        return new X509Certificate[0];
                    }
                }}, new SecureRandom());
                return HttpClients.custom().setSSLSocketFactory(new SSLConnectionSocketFactory(context)).setConnectionManager(CONNECTION_MANAGER).build();
            } catch (Exception e) {
                e.printStackTrace();
                return HttpClients.createDefault();
            }
        } else {
            return HttpClients.custom().setConnectionManager(CONNECTION_MANAGER).build();
        }
    }

    private static boolean isHttps(String url) {
        return url != null && url.trim().startsWith("https");
    }

    public static class HttpRequest implements Serializable {
        private String url;
        private String method;
        private Map<String, String> headers;
        private Object body;
        private Function<HttpEntity, Object> responseHandler;

        public static HttpRequest build(String url, String method) {
            HttpRequest request = new HttpRequest();
            request.url = url;
            request.method = method;
            return request;
        }

        public String getUrl() {
            return url;
        }

        public HttpRequest setUrl(String url) {
            this.url = url;
            return this;
        }

        public String getMethod() {
            return method;
        }

        public HttpRequest setMethod(String method) {
            this.method = method;
            return this;
        }

        public Map<String, String> getHeaders() {
            return headers;
        }

        public HttpRequest setHeaders(Map<String, String> headers) {
            this.headers = headers;
            return this;
        }

        public HttpRequest addHeaders(String key, Object value) {
            if (headers == null) {
                headers = new HashMap<>();
            }
            headers.put(key, value != null ? value.toString() : null);
            return this;
        }

        public Object getBody() {
            return body;
        }

        public HttpRequest setBody(Object body) {
            this.body = body;
            return this;
        }

        public Function<HttpEntity, Object> getResponseHandler() {
            return responseHandler;
        }

        public HttpRequest setResponseHandler(Function<HttpEntity, Object> responseHandler) {
            this.responseHandler = responseHandler;
            return this;
        }
    }

    public static class HttpResponse implements Serializable {
        private int status;
        private long reqTime;
        private Object response;
        private Map<String, String> headers;

        public boolean success() {
            return status == 200;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public long getReqTime() {
            return reqTime;
        }

        public void setReqTime(long reqTime) {
            this.reqTime = reqTime;
        }

        public Object getResponse() {
            return response;
        }

        public void setResponse(Object response) {
            this.response = response;
        }

        public Map<String, String> getHeaders() {
            return headers;
        }

        public void setHeaders(Map<String, String> headers) {
            this.headers = headers;
        }
    }
}
