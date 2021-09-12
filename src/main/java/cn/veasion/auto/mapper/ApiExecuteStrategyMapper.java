package cn.veasion.auto.mapper;

import org.apache.ibatis.annotations.Mapper;
import cn.veasion.auto.model.ApiExecuteStrategyPO;
import java.util.List;

/**
 * 执行策略
 *
 * @author veasion
 * @date 2021-09-10
 */
@Mapper
public interface ApiExecuteStrategyMapper {

    /**
     * 新增
     */
    int insert(ApiExecuteStrategyPO obj);

    /**
     * 批量新增
     */
    int insertAll(List<ApiExecuteStrategyPO> list);

    /**
     * 修改不为空字段
     */
    int update(ApiExecuteStrategyPO obj);

    /**
     * 修改全部字段
     */
    int updateAll(ApiExecuteStrategyPO obj);

    /**
     * 删除
     */
    int deleteById(Integer id);

    /**
     * 根据id查询
     */
    ApiExecuteStrategyPO queryById(Integer id);

    /**
     * 查询list
     */
    List<ApiExecuteStrategyPO> queryList(ApiExecuteStrategyPO apiExecuteStrategyPO);

    List<ApiExecuteStrategyPO> queryByIds(List<Integer> ids);

}
