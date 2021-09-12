package cn.veasion.auto.utils;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.config.RequestConfig.Builder;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;

import javax.net.ssl.SSLContext;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * HttpClientUtils
 *
 * @author luozhuowei
 * @date 2020/12/31
 */
public class HttpClientUtils {

    public static final String CHARSET_DEFAULT = "UTF-8";
    private static final PoolingHttpClientConnectionManager CONNECTION_MANAGER;

    static {
        // http
        CONNECTION_MANAGER = new PoolingHttpClientConnectionManager();
        CONNECTION_MANAGER.setMaxTotal(200);
        CONNECTION_MANAGER.setDefaultMaxPerRoute(100);
    }

    private HttpClientUtils() {
    }

    /**
     * post json 请求
     *
     * @param url  url
     * @param json json
     */
    public static String postJson(String url, String json) throws IOException {
        return post(url, json, "application/json", CHARSET_DEFAULT);
    }

    /**
     * POST
     *
     * @param url
     * @param body
     * @param contentType 例如application/xml，application/x-www-form-urlencoded，application/json，text/html
     * @param charset     编码，默认UTF-8
     */
    public static String post(String url, String body, String contentType, String charset) throws IOException {
        return post(url, body, contentType, charset, null);
    }

    /**
     * POST
     *
     * @param url
     * @param body
     * @param contentType 例如application/xml，application/x-www-form-urlencoded，application/json，text/html
     * @param charset     编码，默认UTF-8
     * @param headers
     */
    public static String post(String url, String body, String contentType, String charset, Map<String, String> headers) throws IOException {
        if (contentType == null && headers != null) {
            contentType = headers.get("Content-Type");
        }
        HttpPost post = new HttpPost(url);
        if (charset == null) {
            charset = CHARSET_DEFAULT;
        }
        if (body != null && body.length() > 0) {
            if (contentType != null) {
                post.setEntity(new StringEntity(body, ContentType.create(contentType, charset)));
            } else {
                post.setEntity(new StringEntity(body, charset));
            }
        }
        if (headers != null && !headers.isEmpty()) {
            for (Entry<String, String> entry : headers.entrySet()) {
                post.addHeader(entry.getKey(), entry.getValue());
            }
        }
        HttpClient client = getHttpClient(url, post);
        HttpResponse res = client.execute(post);
        return IOUtils.toString(res.getEntity().getContent(), charset);
    }

    /**
     * POST [FORM] 请求
     *
     * @param url     url
     * @param params  参数
     * @param headers header
     */
    public static String postForm(String url, Map<String, String> params, Map<String, String> headers) throws IOException {
        HttpPost post = new HttpPost(url);
        if (params != null && !params.isEmpty()) {
            List<NameValuePair> formParams = new ArrayList<>();
            Set<Entry<String, String>> entrySet = params.entrySet();
            for (Entry<String, String> entry : entrySet) {
                formParams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formParams, CHARSET_DEFAULT);
            post.setEntity(entity);
        }
        if (headers != null && !headers.isEmpty()) {
            for (Entry<String, String> entry : headers.entrySet()) {
                post.addHeader(entry.getKey(), entry.getValue());
            }
        }
        HttpClient client = getHttpClient(url, post);
        HttpResponse res = client.execute(post);
        return IOUtils.toString(res.getEntity().getContent(), CHARSET_DEFAULT);
    }

    /**
     * get请求
     */
    public static byte[] get(String url) throws IOException {
        return get(url, (Map<String, String>) null);
    }

    /**
     * get请求
     */
    public static String get(String url, String charset) throws IOException {
        return IOUtils.toString(get(url, (Map<String, String>) null), charset != null ? charset : CHARSET_DEFAULT);
    }

    /**
     * get请求
     */
    public static byte[] get(String url, Map<String, String> headers) throws IOException {
        HttpGet get = new HttpGet(url);
        if (headers != null && !headers.isEmpty()) {
            for (Entry<String, String> entry : headers.entrySet()) {
                get.addHeader(entry.getKey(), entry.getValue());
            }
        }
        HttpClient client = getHttpClient(url, get);
        HttpResponse res = client.execute(get);
        return IOUtils.toByteArray(res.getEntity().getContent());
    }

    private static HttpClient getHttpClient(String url, HttpRequestBase request) {
        Builder customReqConf = RequestConfig.custom();
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
        return url != null && url.startsWith("https");
    }

}