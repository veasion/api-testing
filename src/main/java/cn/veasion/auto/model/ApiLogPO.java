package cn.veasion.auto.model;

import cn.veasion.auto.utils.Constants;
import cn.veasion.auto.utils.StringUtils;
import lombok.Data;

import java.util.Date;

/**
 * 日志
 *
 * @author veasion
 * @date 2021-09-10
 */
@Data
public class ApiLogPO {

    public static final Integer STATUS_RUNNING = 1;
    public static final Integer STATUS_SUC = 2;
    public static final Integer STATUS_FAIL = 3;

    private String id;
    private Integer isDeleted;
    private Date createTime;
    private Date updateTime;

    /**
     * 关联日志id
     */
    private String refId;
    /**
     * 项目id
     */
    private Integer projectId;
    /**
     * 策略id
     */
    private Integer executeStrategyId;
    /**
     * 用例id
     */
    private Integer testCaseId;
    /**
     * 接口请求id
     */
    private Integer apiRequestId;
    /**
     * 请求url
     */
    private String url;
    /**
     * 日志
     */
    private String msg;
    /**
     * 运行状态: 1 执行中 2 执行成功 3 执行失败
     */
    private Integer status;
    /**
     * 请求接口耗时(ms)
     */
    private Integer time;
    /**
     * 执行任务耗时(ms)
     */
    private Integer execTime;

    public void init() {
        if (isDeleted == null) {
            isDeleted = Constants.NO;
        }
        if (createTime == null) {
            createTime = new Date();
        }
        if (updateTime == null) {
            updateTime = new Date();
        }
        if (getId() == null) {
            setId(StringUtils.getUUID());
        }
    }

    public synchronized void appendLog(String log) {
        if (log == null) {
            return;
        }
        if (this.msg == null) {
            this.msg = log;
        } else {
            this.msg += Constants.LINE + log;
        }
    }

}
