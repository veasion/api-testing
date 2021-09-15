package cn.veasion.auto.service;

import cn.veasion.auto.model.ApiLogPO;
import cn.veasion.auto.model.ApiLogQueryVO;
import cn.veasion.auto.model.ApiLogVO;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

/**
 * ApiLogService
 *
 * @author luozhuowei
 * @date 2021/9/10
 */
public interface ApiLogService {

    Page<ApiLogVO> listPage(ApiLogQueryVO apiLog, int pageIndex, int pageSize);

    int addWithNewTx(ApiLogPO apiLogPO);

    int addAllWithNewTx(List<ApiLogPO> list);

    void updateWithNewTx(ApiLogPO apiLogPO);

    int updateRefTimeStatusWithNewTx(ApiLogPO apiLogPO);

    int deleteLogs(String beforeCreateTime);

    /**
     * 根据策略ID查询单策略执行日志
     */
    Page<ApiLogPO> queryByStrategyId(Integer strategyId, String logId, int pageIndex, int pageSize);

    /**
     * 统计各状态数量
     */
    Map<Integer, Integer> countStatus(ApiLogQueryVO apiLog);

    /**
     * 按天统计各状态数量
     */
    List<Map<String, Object>> groupDayStatusCount(ApiLogQueryVO apiLog);

    /**
     * 接口耗时排行榜
     */
    List<ApiLogVO> listRanking(ApiLogQueryVO apiLog);

    /**
     * 压测结果分析
     */
    Map<String, Object> pressureResult(Integer strategyId, String logId);

}
