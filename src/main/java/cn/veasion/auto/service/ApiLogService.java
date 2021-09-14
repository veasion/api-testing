package cn.veasion.auto.service;

import cn.veasion.auto.model.ApiLogPO;
import cn.veasion.auto.model.ApiLogVO;
import cn.veasion.auto.model.ApiRankingVO;
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

    Page<ApiLogPO> listPage(ApiLogVO apiLog, int pageIndex, int pageSize);

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
    Map<Integer, Integer> countStatus(ApiLogVO apiLog);

    /**
     * 按天统计各状态数量
     */
    List<Map<String, Object>> groupDayStatusCount(ApiLogVO apiLog);

    /**
     * 接口耗时排行榜
     */
    List<ApiRankingVO> listRanking(ApiLogVO apiLog);

    /**
     * 压测结果分析
     */
    Map<String, Object> pressureResult(Integer strategyId, String logId);

}
