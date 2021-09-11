package cn.veasion.auto.model;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import org.springframework.util.StringUtils;

/**
 * 全局配置
 *
 * @author veasion
 * @date 2021-09-10
 */
@Data
public class ProjectConfigPO extends BasePO {

    /**
     * 项目id
     */
    private Integer projectId;
    /**
     * 全局变量
     */
    private String globalVarJson;
    /**
     * 脚本: 策略异常执行
     */
    private String exceptionScript;
    /**
     * 脚本: 策略执行前
     */
    private String beforeScript;
    /**
     * 脚本: 策略执行后
     */
    private String afterScript;

    public JSONObject toGlobalVarJson() {
        if (StringUtils.hasText(globalVarJson)) {
            return JSONObject.parseObject(globalVarJson);
        }
        return null;
    }
}
