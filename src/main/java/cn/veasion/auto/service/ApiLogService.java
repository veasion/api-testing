package cn.veasion.auto.service;

import cn.veasion.auto.model.ApiLogPO;
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

    Page<ApiLogPO> listPage(ApiLogVO apiLog, int pageIndex, int pageSize);

    int addWithNewTx(ApiLogPO apiLogPO);

    void updateWithNewTx(ApiLogPO apiLogPO);

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
