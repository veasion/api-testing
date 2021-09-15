package cn.veasion.auto.model;

import lombok.Data;

import java.util.List;

/**
 * ApiLogQueryVO
 *
 * @author luozhuowei
 * @date 2021/9/11
 */
@Data
public class ApiLogQueryVO {

    private String id;
    private String refId;
    private Integer projectId;
    private Integer executeStrategyId;
    private Integer testCaseId;
    private Integer apiRequestId;
    private Integer isAvailable;

    private String startCreateDate;
    private String endCreateTime;
    private Integer startTime;
    private Integer endTime;
    private Integer startExecTime;
    private Integer endExecTime;
    private Integer status;
    private List<Integer> statusList;
    private String msg;
    private String url;

}
