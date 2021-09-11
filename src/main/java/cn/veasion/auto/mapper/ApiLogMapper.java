package cn.veasion.auto.mapper;

import cn.veasion.auto.model.ApiLogVO;
import org.apache.ibatis.annotations.Mapper;
import cn.veasion.auto.model.ApiLogPO;

import java.util.List;
import java.util.Map;

/**
 * 日志
 *
 * @author veasion
 * @date 2021-09-10
 */
@Mapper
public interface ApiLogMapper {

    /**
     * 新增
     */
    int insert(ApiLogPO obj);

    /**
     * 批量新增
     */
    int insertAll(List<ApiLogPO> list);

    /**
     * 修改不为空字段
     */
    int update(ApiLogPO obj);

    /**
     * 修改全部字段
     */
    int updateAll(ApiLogPO obj);

    /**
     * 删除
     */
    int deleteById(Integer id);

    /**
     * 根据id查询
     */
    ApiLogPO queryById(Integer id);

    /**
     * 查询list
     */
    List<ApiLogPO> queryList(ApiLogVO apiLog);

    /**
     * 统计各状态数量
     */
    List<Map<String, Object>> countStatus(ApiLogVO apiLog);

    /**
     * 按天统计各状态数量
     */
    List<Map<String, Object>> groupStatusCount(ApiLogVO apiLog);

    /**
     * 接口耗时排行榜
     */
    List<Map<String, Object>> listRanking(ApiLogVO apiLog);

}
