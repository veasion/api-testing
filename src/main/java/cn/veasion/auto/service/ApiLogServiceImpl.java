package cn.veasion.auto.service;

import cn.veasion.auto.mapper.ApiExecuteStrategyMapper;
import cn.veasion.auto.mapper.ApiLogMapper;
import cn.veasion.auto.mapper.ApiTestCaseMapper;
import cn.veasion.auto.mapper.ProjectMapper;
import cn.veasion.auto.model.ApiExecuteStrategyPO;
import cn.veasion.auto.model.ApiLogPO;
import cn.veasion.auto.model.ApiLogVO;
import cn.veasion.auto.model.ApiRankingVO;
import cn.veasion.auto.model.ApiTestCasePO;
import cn.veasion.auto.model.ProjectPO;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.http.client.utils.DateUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * ApiLogServiceImpl
 *
 * @author luozhuowei
 * @date 2021/9/10
 */
@Service
public class ApiLogServiceImpl implements ApiLogService {

    @Resource
    private ApiLogMapper apiLogMapper;
    @Resource
    private ProjectMapper projectMapper;
    @Resource
    private ApiTestCaseMapper apiTestCaseMapper;
    @Resource
    private ApiExecuteStrategyMapper apiExecuteStrategyMapper;

    private static final int SPLIT_COUNT = 200;

    @Override
    public Page<ApiLogPO> listPage(ApiLogVO apiLog, int pageIndex, int pageSize) {
        PageHelper.startPage(pageIndex, pageSize);
        return (Page<ApiLogPO>) apiLogMapper.queryList(apiLog);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public int addWithNewTx(ApiLogPO apiLogPO) {
        if (apiLogPO.getId() == null) {
            apiLogPO.init();
        }
        return apiLogMapper.insert(apiLogPO);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public int addAllWithNewTx(List<ApiLogPO> list) {
        for (ApiLogPO apiLogPO : list) {
            if (apiLogPO.getId() == null) {
                apiLogPO.init();
            }
        }
        if (list.size() > SPLIT_COUNT) {
            int count = 0;
            for (int i = 0; i < list.size(); i += SPLIT_COUNT) {
                int eIndex = i + SPLIT_COUNT;
                if (eIndex > list.size()) {
                    eIndex = list.size();
                }
                List<ApiLogPO> logs = list.subList(i, eIndex);
                if (!logs.isEmpty()) {
                    count += apiLogMapper.insertAll(logs);
                }
            }
            return count;
        } else {
            return apiLogMapper.insertAll(list);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateWithNewTx(ApiLogPO apiLogPO) {
        apiLogPO.setUpdateTime(new Date());
        apiLogMapper.update(apiLogPO);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public int updateRefTimeStatusWithNewTx(ApiLogPO apiLogPO) {
        if (apiLogPO.getTime() == null && "0".equals(apiLogPO.getRefId())) {
            ApiLogPO sum = apiLogMapper.sumTimeByRefId(apiLogPO.getId());
            if (sum != null) {
                apiLogPO.setTime(sum.getTime());
                apiLogPO.setStatus(sum.getStatus());
            }
        }
        apiLogPO.setUpdateTime(new Date());
        return apiLogMapper.update(apiLogPO);
    }

    @Override
    public int deleteLogs(String beforeCreateTime) {
        return apiLogMapper.deleteLogs(beforeCreateTime);
    }

    @Override
    public Page<ApiLogPO> queryByStrategyId(Integer executeStrategyId, String logId, int pageIndex, int pageSize) {
        PageHelper.startPage(pageIndex, pageSize);
        return (Page<ApiLogPO>) apiLogMapper.queryByStrategyId(executeStrategyId, logId);
    }

    @Override
    public Map<Integer, Integer> countStatus(ApiLogVO apiLog) {
        if (apiLog == null) {
            apiLog = new ApiLogVO();
        }
        List<Map<String, Object>> list = apiLogMapper.countStatus(apiLog);
        Map<Integer, Integer> statusMap = new HashMap<>();
        if (list != null && list.size() > 0) {
            for (Map<String, Object> map : list) {
                Number status = (Number) map.get("status");
                Number count = (Number) map.get("count");
                statusMap.put(status != null ? status.intValue() : null, Optional.ofNullable(count).orElse(0).intValue());
            }
        }
        return statusMap;
    }

    @Override
    public List<Map<String, Object>> groupDayStatusCount(ApiLogVO apiLog) {
        if (apiLog == null) {
            apiLog = new ApiLogVO();
        }
        List<Map<String, Object>> list = apiLogMapper.groupDayStatusCount(apiLog);
        return list != null ? list : Collections.emptyList();
    }

    @Override
    public List<ApiRankingVO> listRanking(ApiLogVO apiLog) {
        if (apiLog == null) {
            apiLog = new ApiLogVO();
        }
        if (apiLog.getStartCreateDate() == null) {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_MONTH, -7);
            apiLog.setStartCreateDate(DateUtils.formatDate(calendar.getTime(), "yyyy-MM-dd 00:00:00"));
        }
        List<ApiRankingVO> list = apiLogMapper.listRanking(apiLog);
        if (list == null || list.isEmpty()) {
            return list;
        }
        List<Integer> projectIds = list.stream().map(ApiRankingVO::getProjectId).filter(Objects::nonNull).collect(Collectors.toList());
        if (projectIds.size() > 0) {
            List<ProjectPO> poList = projectMapper.queryByIds(projectIds);
            Map<Integer, ProjectPO> map = poList.stream().collect(Collectors.toMap(ProjectPO::getId, Function.identity(), (t1, t2) -> t1));
            for (ApiRankingVO apiRankingVO : list) {
                if (map.containsKey(apiRankingVO.getProjectId())) {
                    apiRankingVO.setProjectName(map.get(apiRankingVO.getProjectId()).getName());
                }
            }
        }
        List<Integer> strategyIds = list.stream().map(ApiRankingVO::getExecuteStrategyId).filter(Objects::nonNull).collect(Collectors.toList());
        if (strategyIds.size() > 0) {
            List<ApiExecuteStrategyPO> poList = apiExecuteStrategyMapper.queryByIds(strategyIds);
            Map<Integer, ApiExecuteStrategyPO> map = poList.stream().collect(Collectors.toMap(ApiExecuteStrategyPO::getId, Function.identity(), (t1, t2) -> t1));
            for (ApiRankingVO apiRankingVO : list) {
                if (map.containsKey(apiRankingVO.getExecuteStrategyId())) {
                    apiRankingVO.setExecuteStrategyName(map.get(apiRankingVO.getExecuteStrategyId()).getName());
                }
            }
        }
        List<Integer> testCaseIds = list.stream().map(ApiRankingVO::getTestCaseId).filter(Objects::nonNull).collect(Collectors.toList());
        if (testCaseIds.size() > 0) {
            List<ApiTestCasePO> poList = apiTestCaseMapper.queryByIds(strategyIds);
            Map<Integer, ApiTestCasePO> map = poList.stream().collect(Collectors.toMap(ApiTestCasePO::getId, Function.identity(), (t1, t2) -> t1));
            for (ApiRankingVO apiRankingVO : list) {
                if (map.containsKey(apiRankingVO.getTestCaseId())) {
                    apiRankingVO.setTestCaseName(map.get(apiRankingVO.getTestCaseId()).getCaseName());
                }
            }
        }
        return list;
    }

    @Override
    public Map<String, Object> pressureResult(Integer strategyId, String logId) {
        ApiExecuteStrategyPO strategyPO = apiExecuteStrategyMapper.queryById(strategyId);
        Map<String, Object> result = new HashMap<>();
        PageHelper.startPage(1, 1);
        List<ApiLogPO> list = apiLogMapper.queryByStrategyId(strategyId, logId);
        if (strategyPO == null || list.isEmpty()) {
            return result;
        }
        ApiLogPO apiLogPO = list.get(0);
        ApiLogVO apiLogVO = new ApiLogVO();
        apiLogVO.setRefId(logId);
        Map<Integer, Integer> countMap = countStatus(apiLogVO);

        int threadCount = Optional.ofNullable(strategyPO.getThreadCount()).orElse(1);
        int reqCount = countMap.values().stream().reduce(Integer::sum).orElse(0);
        int avgTime = reqCount > 0 ? apiLogPO.getTime() / reqCount : 0;

        // 并发数
        result.put("threadCount", threadCount);
        // 总请求时长
        result.put("totalTime", apiLogPO.getTime());
        // 平均请求时长
        result.put("avgTime", avgTime);
        // 总执行时长
        result.put("execTime", apiLogPO.getExecTime());
        // 各状态数量
        result.put("statusCountList", countMap);
        // 总请求数
        result.put("reqCount", reqCount);
        // QPS(TPS)
        result.put("tps", avgTime > 0 ? ((threadCount * 1000) / avgTime) : null);
        // 开始时间
        result.put("startTime", DateUtils.formatDate(apiLogPO.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
        // 开始时间
        result.put("endTime", DateUtils.formatDate(apiLogPO.getUpdateTime(), "yyyy-MM-dd HH:mm:ss"));

        return result;
    }
}
