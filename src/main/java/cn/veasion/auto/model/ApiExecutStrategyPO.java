package cn.veasion.auto.model;

import lombok.Data;
import java.util.Date;

/**
 * 执行策略
 *
 * @author veasion
 * @date 2021-09-10
 */
@Data
public class ApiExecutStrategyPO extends BasePO {

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
    private Integer type;
    /**
     * 执行策略: 1 定时任务 2 压测
     */
    private Integer strategy;
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

}
