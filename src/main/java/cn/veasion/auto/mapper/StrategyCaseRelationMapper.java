package cn.veasion.auto.mapper;

import cn.veasion.auto.model.ApiTestCasePO;
import org.apache.ibatis.annotations.Mapper;
import cn.veasion.auto.model.StrategyCaseRelationPO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 策略case关联
 *
 * @author veasion
 * @date 2021-09-10
 */
@Mapper
public interface StrategyCaseRelationMapper {

    /**
     * 新增
     */
    int insert(StrategyCaseRelationPO obj);

    /**
     * 批量新增
     */
    int insertAll(List<StrategyCaseRelationPO> list);

    /**
     * 修改不为空字段
     */
    int update(StrategyCaseRelationPO obj);

    /**
     * 修改全部字段
     */
    int updateAll(StrategyCaseRelationPO obj);

    /**
     * 删除
     */
    int deleteById(Integer id);

    /**
     * 根据id查询
     */
    StrategyCaseRelationPO queryById(Integer id);

    /**
     * 查询list
     */
    List<ApiTestCasePO> queryList(Integer executStrategyId);

    /**
     * 删除
     */
    int delete(@Param("executStrategyId") int executStrategyId, @Param("caseIds") List<Integer> caseIds);

}
