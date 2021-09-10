package cn.veasion.auto.mapper;

import org.apache.ibatis.annotations.Mapper;
import cn.veasion.auto.model.ApiExecutStrategyPO;
import java.util.List;

/**
 * 执行策略
 *
 * @author veasion
 * @date 2021-09-10
 */
@Mapper
public interface ApiExecutStrategyMapper {

    /**
     * 新增
     */
    int insert(ApiExecutStrategyPO obj);

    /**
     * 批量新增
     */
    int insertAll(List<ApiExecutStrategyPO> list);

    /**
     * 修改不为空字段
     */
    int update(ApiExecutStrategyPO obj);

    /**
     * 修改全部字段
     */
    int updateAll(ApiExecutStrategyPO obj);

    /**
     * 删除
     */
    int deleteById(Integer id);

    /**
     * 根据id查询
     */
    ApiExecutStrategyPO queryById(Integer id);

    /**
     * 查询list
     */
    List<ApiExecutStrategyPO> queryList(ApiExecutStrategyPO apiExecutStrategyPO);

}
