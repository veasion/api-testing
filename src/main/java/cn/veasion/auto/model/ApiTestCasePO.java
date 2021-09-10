package cn.veasion.auto.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 测试用例
 *
 * @author veasion
 * @date 2021-09-10
 */
public class ApiTestCasePO implements Serializable {

    private static final long serialVersionUID = -1L;

    private Integer id;
    /**
     * 项目id
     */
    private Integer projectId;
    /**
     * 用例名称
     */
    private String caseName;
    /**
     * 用例描述
     */
    private String caseDesc;
    /**
     * 模块
     */
    private String module;
    /**
     * 作者
     */
    private String author;
    /**
     * js脚本
     */
    private String script;
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

    public String getCaseName() {
        return caseName;
    }

    public void setCaseName(String caseName) {
        this.caseName = caseName;
    }

    public String getCaseDesc() {
        return caseDesc;
    }

    public void setCaseDesc(String caseDesc) {
        this.caseDesc = caseDesc;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script;
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
