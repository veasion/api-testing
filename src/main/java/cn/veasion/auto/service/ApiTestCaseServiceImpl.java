package cn.veasion.auto.service;

import cn.veasion.auto.mapper.ApiTestCaseMapper;
import cn.veasion.auto.model.ApiTestCasePO;
import cn.veasion.auto.model.ApiTestCaseVO;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * ApiTestCaseServiceImpl
 *
 * @author luozhuowei
 * @date 2021/9/10
 */
@Service
public class ApiTestCaseServiceImpl implements ApiTestCaseService {

    @Resource
    private ApiTestCaseMapper apiTestCaseMapper;

    @Override
    public ApiTestCasePO getById(int id) {
        return apiTestCaseMapper.queryById(id);
    }

    @Override
    public Page<ApiTestCaseVO> listPage(ApiTestCaseVO apiTestCaseVO, int pageIndex, int pageSize) {
        PageHelper.startPage(pageIndex, pageSize);
        return (Page<ApiTestCaseVO>) apiTestCaseMapper.queryList(apiTestCaseVO);
    }

    @Override
    public void saveOrUpdate(ApiTestCasePO apiTestCasePO) {
        if (apiTestCasePO.getId() == null) {
            apiTestCasePO.init();
            apiTestCaseMapper.insert(apiTestCasePO);
        } else {
            apiTestCasePO.setUpdateTime(new Date());
            apiTestCaseMapper.update(apiTestCasePO);
        }
    }

    @Override
    public int delete(int id) {
        return apiTestCaseMapper.deleteById(id);
    }
}
