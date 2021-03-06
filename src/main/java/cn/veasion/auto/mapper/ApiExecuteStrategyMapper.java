package cn.veasion.auto.mapper;

import cn.veasion.auto.model.ApiExecuteStrategyVO;
import org.apache.ibatis.annotations.Mapper;
import cn.veasion.auto.model.ApiExecuteStrategyPO;
import org.apache.ibatis.annotations.Param;

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
     * 修改策略状态
     */
    int updateStatus(@Param("id") Integer id, @Param("status") Integer status);

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
    List<ApiExecuteStrategyVO> queryList(ApiExecuteStrategyVO apiExecuteStrategy);

    /**
     * 查询可执行策略
     */
    List<ApiExecuteStrategyVO> queryCronExecutableStrategy(@Param("projectId") Integer projectId);

    /**
     * 查询根据 ids
     */
    List<ApiExecuteStrategyPO> queryByIds(List<Integer> ids);

}
