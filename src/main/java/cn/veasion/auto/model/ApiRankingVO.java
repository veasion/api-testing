package cn.veasion.auto.model;

import lombok.Data;

/**
 * ApiRankingVO
 *
 * @author luozhuowei
 * @date 2021/9/12
 */
@Data
public class ApiRankingVO extends ApiLogPO {

    private String apiName;
    private String apiDesc;
    private String method;

    private String projectName;
    private String executeStrategyName;
    private String testCaseName;

}
