package cn.veasion.auto.core.bind;

import cn.veasion.auto.utils.HttpUtils;
import cn.veasion.auto.utils.JavaScriptUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import jdk.nashorn.api.scripting.ScriptObjectMirror;
import lombok.Data;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * JiraScriptBindBean
 *
 * @author luozhuowei
 * @date 2022/2/23
 */
@Component
public class JiraScriptBindBean extends AbstractScriptBindBean {

    /**
     * 登录 JIRA
     */
    public JiraBean login(String jiraUrl, String username, String password) throws Exception {
        if (jiraUrl.endsWith("/")) {
            jiraUrl = jiraUrl.substring(0, jiraUrl.length() - 1);
        }

        JiraBean jiraBean = new JiraBean();
        jiraBean.setJiraUrl(jiraUrl);

        HttpUtils.HttpResponse response = request(jiraBean, "/rest/gadget/1.0/login", new HashMap<String, Object>() {{
            put("os_username", username);
            put("os_password", password);
        }});

        Map<String, String> headers = response.getHeaders();
        String value = headers.get("Set-Cookie");
        String[] cookies = value.split(";");
        StringBuilder cookieValue = new StringBuilder();
        for (String cookie : cookies) {
            cookie = cookie.trim();
            if (!cookie.startsWith("Path") && !cookie.startsWith("HttpOnly")) {
                cookieValue.append(cookie).append(";");
            }
        }
        if (cookieValue.length() > 0) {
            cookieValue.setLength(cookieValue.length() - 1);
        }
        jiraBean.setCookie(cookieValue.toString());
        return jiraBean;
    }

    /**
     * 创建问题
     */
    @SuppressWarnings("unchecked")
    public JSONObject createIssue(JiraBean jiraBean, Object object) throws Exception {
        // 加载表单信息
        this.loadFormInfo(jiraBean);

        // 构建表单
        Map<String, Object> map;
        if (object instanceof ScriptObjectMirror) {
            map = (Map<String, Object>) JavaScriptUtils.toJavaObject(object);
        } else if (object instanceof Map) {
            map = (Map<String, Object>) object;
        } else {
            map = JSON.parseObject(object.toString());
        }

        Map<String, Object> params = new HashMap<>();

        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (jiraBean.getLabelIdFieldMap().containsKey(entry.getKey())) {
                String id = jiraBean.getLabelIdFieldMap().get(entry.getKey());
                if (id != null) {
                    params.put(id, entry.getValue());
                    params.put("fieldsToRetain", id);
                }
            } else {
                params.put(entry.getKey(), entry.getValue());
            }
        }

        params.put("pid", params.get("project"));
        params.put("atl_token", jiraBean.getAtlToken());
        params.put("formToken", jiraBean.getFormToken());

        // 创建问题
        HttpUtils.HttpResponse response = request(jiraBean, "/secure/QuickCreateIssue.jspa?decorator=none", params);
        return JSON.parseObject(response.getResponseToString());
    }

    private void loadFormInfo(JiraBean jiraBean) throws Exception {
        HttpUtils.HttpResponse response = request(jiraBean, "/secure/QuickCreateIssue!default.jspa?decorator=none", null);
        JSONObject json = JSON.parseObject(response.getResponseToString());
        jiraBean.setAtlToken(json.getString("atl_token"));
        jiraBean.setFormToken(json.getString("formToken"));
        jiraBean.setLabelIdFieldMap(new HashMap<>());
        JSONArray fields = json.getJSONArray("fields");
        if (fields != null) {
            for (int i = 0; i < fields.size(); i++) {
                JSONObject field = fields.getJSONObject(i);
                jiraBean.getLabelIdFieldMap().put(field.getString("label"), field.getString("id"));
            }
        }
    }

    private HttpUtils.HttpResponse request(JiraBean jiraBean, String uri, Map<String, Object> params) throws Exception {
        HttpUtils.HttpRequest request = new HttpUtils.HttpRequest();
        request.setMethod("POST");
        request.setUrl(jiraBean.getJiraUrl() + uri);
        request.setHeaders(new HashMap<String, String>() {{
            if (params != null) {
                put(HttpUtils.CONTENT_TYPE, HttpUtils.CONTENT_TYPE_FORM_DATA);
            }
            if (jiraBean.getCookie() != null) {
                put("Cookie", jiraBean.getCookie());
            }
        }});
        if (params != null && !params.isEmpty()) {
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(mapToParameters(params), "UTF-8");
            request.setBody(entity);
        }
        return HttpUtils.request(request);
    }

    private List<BasicNameValuePair> mapToParameters(Map<String, Object> map) {
        List<BasicNameValuePair> parameters = new ArrayList<>();
        if (map != null && !map.isEmpty()) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                parameters.add(new BasicNameValuePair(entry.getKey(), entry.getValue() != null ? entry.getValue().toString() : null));
            }
        }
        return parameters;
    }

    @Data
    public static class JiraBean {
        private String jiraUrl;
        private String cookie;
        private String atlToken;
        private String formToken;
        private Map<String, String> labelIdFieldMap;
    }

    public static void main(String[] args) throws Exception {
        JiraScriptBindBean jiraScriptBindBean = new JiraScriptBindBean();
        JiraBean jiraBean = jiraScriptBindBean.login("http://jira.odianyun.local", "test_robot", "12345678");
        JSONObject result = jiraScriptBindBean.createIssue(jiraBean, new HashMap<String, Object>() {{
            put("项目", "13701");
            put("问题类型", "10102");
            put("主题", "【接口自动化测试】标题");
            put("项目阶段", "13900");
            put("所属项目", "15402");
            put("模块名称", "17125");
            put("描述", "xxx");
            put("发现阶段", "10920");
            put("回归BUG", "11104");
            put("缺陷类型", "10924");
            put("经办人", "luozhuowei");
            put("报告人", "test_robot");
        }});
        System.out.println(result);
        String bugCode = result.getJSONObject("createdIssueDetails").getString("key");
        System.out.println(bugCode);
    }

}
