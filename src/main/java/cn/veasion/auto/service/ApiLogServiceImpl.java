package cn.veasion.auto.service;

import cn.veasion.auto.mapper.ApiLogMapper;
import cn.veasion.auto.model.ApiLogPO;
import cn.veasion.auto.model.ApiLogVO;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Map;

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

    @Override
    public Page<ApiLogPO> listPage(ApiLogVO apiLog, int pageIndex, int pageSize) {
        PageHelper.startPage(pageIndex, pageSize);
        return (Page<ApiLogPO>) apiLogMapper.queryList(apiLog);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public int addWithNewTx(ApiLogPO apiLogPO) {
        return apiLogMapper.insert(apiLogPO);
    }

    @Async
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateWithNewTx(ApiLogPO apiLogPO) {
        apiLogMapper.update(apiLogPO);
    }

    @Override
    public List<Map<String, Object>> countStatus(ApiLogVO apiLog) {
        if (apiLog == null) {
            apiLog = new ApiLogVO();
        }
        List<Map<String, Object>> list = apiLogMapper.countStatus(apiLog);
        return list != null ? list : Collections.emptyList();
    }

    @Override
    public List<Map<String, Object>> groupStatusCount(ApiLogVO apiLog) {
        if (apiLog == null) {
            apiLog = new ApiLogVO();
        }
        List<Map<String, Object>> list = apiLogMapper.groupStatusCount(apiLog);
        return list != null ? list : Collections.emptyList();
    }

    @Override
    public List<Map<String, Object>> listRanking(ApiLogVO apiLog) {
        if (apiLog == null) {
            apiLog = new ApiLogVO();
        }
        return apiLogMapper.listRanking(apiLog);
    }

}
