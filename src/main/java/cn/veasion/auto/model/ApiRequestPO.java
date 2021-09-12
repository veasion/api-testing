package cn.veasion.auto.model;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * 请求接口
 *
 * @author veasion
 * @date 2021-09-10
 */
@Data
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
}
