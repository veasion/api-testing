package cn.veasion.auto.model;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import org.springframework.util.StringUtils;

/**
 * 执行策略
 *
 * @author veasion
 * @date 2021-09-10
 */
@Data
public class ApiExecuteStrategyPO extends BasePO {

    public static final Integer TYPE_ALL_CASE = 1;
    public static final Integer TYPE_CASES = 2;
    public static final Integer TYPE_SCRIPT = 3;

    public static final Integer STRATEGY_JOB = 1;
    public static final Integer STRATEGY_PRESSURE = 2;

    public static final Integer THREAD_STRATEGY_TIME = 1;
    public static final Integer THREAD_STRATEGY_LOOP = 2;

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

    public ThreadStrategy toThreadStrategy() {
        if (!StringUtils.hasText(threadStrategyJson)) {
            return null;
        }
        return JSON.parseObject(threadStrategyJson, ThreadStrategy.class);
    }

    @Data
    public static class ThreadStrategy {
        Integer type; // 压测类型：1 按时长 2 按次数
        Integer loopCount; // 执行多少次
        Long timeInMillis; // 压测时间
        Long intervalInMillis; // 并发间隔时间
    }

}
