package cn.veasion.auto.service;

import cn.veasion.auto.mapper.ApiLogMapper;
import cn.veasion.auto.model.ApiLogPO;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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
    public Page<ApiLogPO> listPage(ApiLogPO apiLogPO, int pageIndex, int pageSize) {
        PageHelper.startPage(pageIndex, pageSize);
        return (Page<ApiLogPO>) apiLogMapper.queryList(apiLogPO);
    }

    @Override
    public int addAllWithTx(List<ApiLogPO> list) {
        return apiLogMapper.insertAll(list);
    }
}
