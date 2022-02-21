package cn.veasion.auto.model;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * 请求接口
 *
 * @author veasion
 * @date 2021-09-10
 */
public class ApiRequestPO extends BasePO<Integer> {

    /**
     * 项目id
     */
    private Integer projectId;
    /**
     * 命名
     */
    private String apiName;
    /**
     * 分组
     */
    private String apiGroup;
    /**
     * 请求描述
     */
    private String apiDesc;
    /**
     * 请求方法
     */
    private String method;
    /**
     * 请求url
     */
    private String url;
    /**
     * 请求头
     */
    private String headersJson;
    /**
     * 请求body
     */
    private String body;
    /**
     * http脚本
     */
    private String script;

    public Map<String, Object> toHeaders() {
        if (StringUtils.hasText(headersJson)) {
            return JSON.parseObject(headersJson);
        }
        return null;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getApiName() {
        return apiName;
    }

    public void setApiName(String apiName) {
        this.apiName = apiName;
    }

    public String getApiGroup() {
        return apiGroup;
    }

    public void setApiGroup(String apiGroup) {
        this.apiGroup = apiGroup;
    }

    public String getApiDesc() {
        return apiDesc;
    }

    public void setApiDesc(String apiDesc) {
        this.apiDesc = apiDesc;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHeadersJson() {
        return headersJson;
    }

    public void setHeadersJson(String headersJson) {
        this.headersJson = headersJson;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setBody(JSONObject body) {
        this.body = body.toJSONString();
    }

    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script;
    }
}
