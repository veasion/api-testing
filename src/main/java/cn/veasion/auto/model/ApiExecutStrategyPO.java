package cn.veasion.auto.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 执行策略
 *
 * @author veasion
 * @date 2021-09-10
 */
public class ApiExecutStrategyPO implements Serializable {

    private static final long serialVersionUID = -1L;

    private Integer id;
    /**
     * 项目id
     */
    private Integer projectId;
    /**
     * 名称
     */
    private String name;
    /**
     * 描述
     */
    private String desc;
    /**
     * 类型: 1 所有case 2 指定case 3 自定义脚本
     */
    private String type;
    /**
     * 执行策略: 1 定时任务 2 压测
     */
    private String strategy;
    /**
     * 任务执行CRON
     */
    private String jobCron;
    /**
     * 并发线程数
     */
    private Integer threadCount;
    /**
     * 线程创建策略（瞬时创建、间隔增量创建、指定时间内创建）
     */
    private String threadStrategyJson;
    /**
     * 压测时间(ms)
     */
    private Integer runTime;
    /**
     * 循环次数
     */
    private Integer loopCount;
    /**
     * js脚本
     */
    private String script;
    /**
     * 执行开始-时间
     */
    private Date startTime;
    /**
     * 执行结束-时间
     */
    private Date endTime;
    /**
     * 运行状态: 1 执行中 2 执行成功 3 执行失败
     */
    private Integer status;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStrategy() {
        return strategy;
    }

    public void setStrategy(String strategy) {
        this.strategy = strategy;
    }

    public String getJobCron() {
        return jobCron;
    }

    public void setJobCron(String jobCron) {
        this.jobCron = jobCron;
    }

    public Integer getThreadCount() {
        return threadCount;
    }

    public void setThreadCount(Integer threadCount) {
        this.threadCount = threadCount;
    }

    public String getThreadStrategyJson() {
        return threadStrategyJson;
    }

    public void setThreadStrategyJson(String threadStrategyJson) {
        this.threadStrategyJson = threadStrategyJson;
    }

    public Integer getRunTime() {
        return runTime;
    }

    public void setRunTime(Integer runTime) {
        this.runTime = runTime;
    }

    public Integer getLoopCount() {
        return loopCount;
    }

    public void setLoopCount(Integer loopCount) {
        this.loopCount = loopCount;
    }

    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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
