package cn.veasion.auto.model;

import lombok.Data;

import java.util.List;

/**
 * ApiExecuteStrategyVO
 *
 * @author luozhuowei
 * @date 2021/9/14
 */
@Data
public class ApiExecuteStrategyVO extends ApiExecuteStrategyPO {

    private String projectName;
    private List<Integer> caseIds;

    private String refLogId;

}
