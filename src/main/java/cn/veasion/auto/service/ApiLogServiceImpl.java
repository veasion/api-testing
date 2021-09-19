package cn.veasion.auto.service;

import cn.veasion.auto.mapper.ApiExecuteStrategyMapper;
import cn.veasion.auto.mapper.ApiLogMapper;
import cn.veasion.auto.mapper.ApiRequestMapper;
import cn.veasion.auto.mapper.ApiTestCaseMapper;
import cn.veasion.auto.mapper.ProjectMapper;
import cn.veasion.auto.model.ApiExecuteStrategyPO;
import cn.veasion.auto.model.ApiLogPO;
import cn.veasion.auto.model.ApiLogQueryVO;
import cn.veasion.auto.model.ApiLogVO;
import cn.veasion.auto.model.ApiRequestPO;
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
    private ApiRequestMapper apiRequestMapper;
    @Resource
    private ApiTestCaseMapper apiTestCaseMapper;
    @Resource
    private ApiExecuteStrategyMapper apiExecuteStrategyMapper;

    private static final int SPLIT_COUNT = 200;

    @Override
    public Page<ApiLogVO> listPage(ApiLogQueryVO apiLog, int pageIndex, int pageSize) {
        PageHelper.startPage(pageIndex, pageSize);
        Page<ApiLogVO> list = (Page<ApiLogVO>) apiLogMapper.queryList(apiLog);
        loadOtherData(list, true, true, true, true);
        return list;
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
    public Page<ApiLogPO> queryByStrategyId(Integer strategyId, String logId, int pageIndex, int pageSize) {
        PageHelper.startPage(pageIndex, pageSize);
        return (Page<ApiLogPO>) apiLogMapper.queryByStrategyId(strategyId, logId);
    }

    @Override
    public Map<Integer, Integer> countStatus(ApiLogQueryVO apiLog) {
        if (apiLog == null) {
            apiLog = new ApiLogQueryVO();
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
    public List<Map<String, Object>> groupDayStatusCount(ApiLogQueryVO apiLog) {
        if (apiLog == null) {
            apiLog = new ApiLogQueryVO();
        }
        List<Map<String, Object>> list = apiLogMapper.groupDayStatusCount(apiLog);
        return list != null ? list : Collections.emptyList();
    }

    @Override
    public List<ApiLogVO> listRanking(ApiLogQueryVO apiLog) {
        if (apiLog == null) {
            apiLog = new ApiLogQueryVO();
        }
        if (apiLog.getStartCreateDate() == null) {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_MONTH, -7);
            apiLog.setStartCreateDate(DateUtils.formatDate(calendar.getTime(), "yyyy-MM-dd 00:00:00"));
        }
        List<ApiLogVO> list = apiLogMapper.listRanking(apiLog);
        if (list == null || list.isEmpty()) {
            return list;
        }
        loadOtherData(list, false, false, true, false);
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
        ApiLogQueryVO apiLog = new ApiLogQueryVO();
        apiLog.setRefId(logId);
        Map<Integer, Integer> countMap = countStatus(apiLog);

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

    private void loadOtherData(List<ApiLogVO> list, boolean project, boolean strategy, boolean testCase, boolean request) {
        if (project) {
            List<Integer> projectIds = list.stream().map(ApiLogVO::getProjectId).filter(Objects::nonNull).collect(Collectors.toList());
            if (projectIds.size() > 0) {
                List<ProjectPO> poList = projectMapper.queryByIds(projectIds);
                Map<Integer, ProjectPO> map = poList.stream().collect(Collectors.toMap(ProjectPO::getId, Function.identity(), (t1, t2) -> t1));
                for (ApiLogVO apiLogVO : list) {
                    if (map.containsKey(apiLogVO.getProjectId())) {
                        apiLogVO.setProjectName(map.get(apiLogVO.getProjectId()).getName());
                    }
                }
            }
        }
        if (strategy) {
            List<Integer> strategyIds = list.stream().map(ApiLogVO::getExecuteStrategyId).filter(Objects::nonNull).collect(Collectors.toList());
            if (strategyIds.size() > 0) {
                List<ApiExecuteStrategyPO> poList = apiExecuteStrategyMapper.queryByIds(strategyIds);
                Map<Integer, ApiExecuteStrategyPO> map = poList.stream().collect(Collectors.toMap(ApiExecuteStrategyPO::getId, Function.identity(), (t1, t2) -> t1));
                for (ApiLogVO apiLogVO : list) {
                    if (map.containsKey(apiLogVO.getExecuteStrategyId())) {
                        apiLogVO.setExecuteStrategyName(map.get(apiLogVO.getExecuteStrategyId()).getName());
                    }
                }
            }
        }
        if (testCase) {
            List<Integer> testCaseIds = list.stream().map(ApiLogVO::getTestCaseId).filter(Objects::nonNull).collect(Collectors.toList());
            if (testCaseIds.size() > 0) {
                List<ApiTestCasePO> poList = apiTestCaseMapper.queryByIds(testCaseIds);
                Map<Integer, ApiTestCasePO> map = poList.stream().collect(Collectors.toMap(ApiTestCasePO::getId, Function.identity(), (t1, t2) -> t1));
                for (ApiLogVO apiLogVO : list) {
                    if (map.containsKey(apiLogVO.getTestCaseId())) {
                        apiLogVO.setTestCaseName(map.get(apiLogVO.getTestCaseId()).getCaseName());
                    }
                }
            }
        }
        if (request) {
            List<Integer> requestIds = list.stream().map(ApiLogVO::getApiRequestId).filter(Objects::nonNull).collect(Collectors.toList());
            if (requestIds.size() > 0) {
                List<ApiRequestPO> poList = apiRequestMapper.queryByIds(requestIds);
                Map<Integer, ApiRequestPO> map = poList.stream().collect(Collectors.toMap(ApiRequestPO::getId, Function.identity(), (t1, t2) -> t1));
                for (ApiLogVO apiLogVO : list) {
                    if (map.containsKey(apiLogVO.getApiRequestId())) {
                        apiLogVO.setApiName(map.get(apiLogVO.getApiRequestId()).getApiName());
                        apiLogVO.setApiDesc(map.get(apiLogVO.getApiRequestId()).getApiDesc());
                    }
                }
            }
        }
    }
}
