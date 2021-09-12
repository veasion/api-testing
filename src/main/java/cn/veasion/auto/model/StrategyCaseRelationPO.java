package cn.veasion.auto.model;

import lombok.Data;

/**
 * 策略case关联
 *
 * @author veasion
 * @date 2021-09-10
 */
@Data
public class StrategyCaseRelationPO extends BasePO<Integer> {

    /**
     * 策略id
     */
    private Integer executeStrategyId;
    /**
     * 用例id
     */
    private Integer caseId;

}
