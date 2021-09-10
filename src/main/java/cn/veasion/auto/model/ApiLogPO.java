package cn.veasion.auto.model;

import lombok.Data;

/**
 * 日志
 *
 * @author veasion
 * @date 2021-09-10
 */
@Data
public class ApiLogPO extends BasePO {

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

}
