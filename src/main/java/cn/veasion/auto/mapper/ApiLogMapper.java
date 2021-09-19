package cn.veasion.auto.mapper;

import cn.veasion.auto.model.ApiLogQueryVO;
import cn.veasion.auto.model.ApiLogVO;
import org.apache.ibatis.annotations.Mapper;
import cn.veasion.auto.model.ApiLogPO;
import org.apache.ibatis.annotations.Param;

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
    int deleteLogs(String beforeCreateTime);

    /**
     * 根据id查询
     */
    ApiLogPO queryById(String id);

    /**
     * 查询list
     */
    List<ApiLogVO> queryList(ApiLogQueryVO apiLog);

    /**
     * 根据策略ID查询单策略执行日志
     */
    List<ApiLogPO> queryByStrategyId(@Param("strategyId") Integer strategyId, @Param("logId") String logId);

    /**
     * 统计各状态数量
     */
    List<Map<String, Object>> countStatus(ApiLogQueryVO apiLog);

    /**
     * 按天统计各状态数量
     */
    List<Map<String, Object>> groupDayStatusCount(ApiLogQueryVO apiLog);

    /**
     * 接口耗时排行榜（支持定时任务，排除压测数据）
     */
    List<ApiLogVO> listRanking(ApiLogQueryVO apiLog);

    /**
     * 汇总time
     */
    ApiLogPO sumTimeByRefId(@Param("refId") String refId);

}
