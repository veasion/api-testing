package cn.veasion.auto.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 日志
 *
 * @author veasion
 * @date 2021-09-10
 */
public class ApiLogPO implements Serializable {

    private static final long serialVersionUID = -1L;

    private Integer id;
    /**
     * 项目id
     */
    private Integer projectId;
    /**
     * 策略id
     */
    private Integer executStrategyId;
    /**
     * 用例id
     */
    private Integer testCaseId;
    /**
     * 接口请求id
     */
    private Integer apiRequestId;
    /**
     * 日志
     */
    private String message;
    /**
     * 运行状态: 1 执行中 2 执行成功 3 执行失败
     */
    private Integer status;
    /**
     * 请求接口耗时(ms)
     */
    private Integer time;
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

    public Integer getExecutStrategyId() {
        return executStrategyId;
    }

    public void setExecutStrategyId(Integer executStrategyId) {
        this.executStrategyId = executStrategyId;
    }

    public Integer getTestCaseId() {
        return testCaseId;
    }

    public void setTestCaseId(Integer testCaseId) {
        this.testCaseId = testCaseId;
    }

    public Integer getApiRequestId() {
        return apiRequestId;
    }

    public void setApiRequestId(Integer apiRequestId) {
        this.apiRequestId = apiRequestId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
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
