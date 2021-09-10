package cn.veasion.auto.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 策略case关联
 *
 * @author veasion
 * @date 2021-09-10
 */
public class StrategyCaseRelationPO implements Serializable {

    private static final long serialVersionUID = -1L;

    private Integer id;
    /**
     * 策略id
     */
    private Integer executStrategyId;
    /**
     * 用例id
     */
    private Integer caseId;
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

    public Integer getExecutStrategyId() {
        return executStrategyId;
    }

    public void setExecutStrategyId(Integer executStrategyId) {
        this.executStrategyId = executStrategyId;
    }

    public Integer getCaseId() {
        return caseId;
    }

    public void setCaseId(Integer caseId) {
        this.caseId = caseId;
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
