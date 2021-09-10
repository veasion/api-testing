package cn.veasion.auto.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 全局配置
 *
 * @author veasion
 * @date 2021-09-10
 */
public class ProjectConfigPO implements Serializable {

    private static final long serialVersionUID = -1L;

    private Integer id;
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
    /**
     * 是否可用
     */
    private Integer isAvailable;
    /**
     * 是否删除
     */
    private Integer isDeleted;
    private Date createTime;
    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getGlobalVarJson() {
        return globalVarJson;
    }

    public void setGlobalVarJson(String globalVarJson) {
        this.globalVarJson = globalVarJson;
    }

    public String getExceptionScript() {
        return exceptionScript;
    }

    public void setExceptionScript(String exceptionScript) {
        this.exceptionScript = exceptionScript;
    }

    public String getBeforeScript() {
        return beforeScript;
    }

    public void setBeforeScript(String beforeScript) {
        this.beforeScript = beforeScript;
    }

    public String getDoScript() {
        return doScript;
    }

    public void setDoScript(String doScript) {
        this.doScript = doScript;
    }

    public String getAfterScript() {
        return afterScript;
    }

    public void setAfterScript(String afterScript) {
        this.afterScript = afterScript;
    }

    public String getNotifyScript() {
        return notifyScript;
    }

    public void setNotifyScript(String notifyScript) {
        this.notifyScript = notifyScript;
    }

    public Integer getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(Integer isAvailable) {
        this.isAvailable = isAvailable;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

}
