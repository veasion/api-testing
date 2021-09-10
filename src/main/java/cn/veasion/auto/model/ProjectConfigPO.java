package cn.veasion.auto.model;

import lombok.Data;

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
     * 脚本: 异常监听
     */
    private String exceptionScript;
    /**
     * 脚本: 请求前
     */
    private String beforeScript;
    /**
     * 脚本: 请求中
     */
    private String doScript;
    /**
     * 脚本: 请求后
     */
    private String afterScript;
    /**
     * 脚本：通知
     */
    private String notifyScript;

}
