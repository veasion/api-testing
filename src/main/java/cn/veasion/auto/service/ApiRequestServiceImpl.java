package cn.veasion.auto.service;

import cn.veasion.auto.exception.BusinessException;
import cn.veasion.auto.mapper.ApiRequestMapper;
import cn.veasion.auto.mapper.ApiTestCaseMapper;
import cn.veasion.auto.model.ApiRequestPO;
import cn.veasion.auto.model.ApiRequestVO;
import cn.veasion.auto.model.ApiTestCasePO;
import cn.veasion.auto.model.ProjectPO;
import cn.veasion.auto.utils.StringUtils;
import cn.veasion.auto.utils.UserUtils;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ApiRequestServiceImpl
 *
 * @author luozhuowei
 * @date 2021/9/10
 */
@Service
public class ApiRequestServiceImpl implements ApiRequestService {

    @Resource
    private ApiRequestMapper apiRequestMapper;
    @Resource
    private ApiTestCaseMapper apiTestCaseMapper;

    @Override
    public ApiRequestPO getById(int id) {
        return apiRequestMapper.queryById(id);
    }

    @Override
    public ApiRequestPO queryByApiName(String apiName, Integer projectId) {
        return apiRequestMapper.queryByApiName(apiName, projectId, null);
    }

    @Override
    public Page<ApiRequestVO> listPage(ApiRequestVO apiRequestVO, int pageIndex, int pageSize) {
        PageHelper.startPage(pageIndex, pageSize);
        return (Page<ApiRequestVO>) apiRequestMapper.queryList(apiRequestVO);
    }

    @Override
    public List<ApiRequestVO> search(Integer projectId, String query, Integer limit) {
        return apiRequestMapper.search(projectId, query, limit);
    }

    @Override
    public void saveOrUpdate(ApiRequestPO apiRequestPO) {
        apiRequestPO.setUpdateUsername(UserUtils.getUsername());
        if (apiRequestPO.getProjectId() == null) {
            throw new BusinessException("projectId不能为空");
        }
        if (StringUtils.hasText(apiRequestPO.getHeadersJson())) {
            try {
                JSON.parseObject(apiRequestPO.getHeadersJson());
            } catch (Exception e) {
                throw new BusinessException("请求头格式错误，必须为JSON格式");
            }
        }
        if (apiRequestPO.getApiName() != null) {
            ApiRequestPO obj = apiRequestMapper.queryByApiName(apiRequestPO.getApiName(), apiRequestPO.getProjectId(), apiRequestPO.getId());
            if (obj != null) {
                throw new BusinessException("api命名已存在");
            }
        }
        if (apiRequestPO.getId() == null) {
            apiRequestPO.init();
            apiRequestMapper.insert(apiRequestPO);
        } else {
            apiRequestPO.setUpdateTime(new Date());
            apiRequestMapper.update(apiRequestPO);
        }
    }

    @Override
    public int delete(int id) {
        return apiRequestMapper.deleteById(id);
    }

    @Override
    public List<String> queryAllApiName(Integer projectId) {
        return apiRequestMapper.queryAllApiName(projectId);
    }

    @Override
    public int importWithTx(ProjectPO projectPO, List<ApiRequestPO> list, boolean autoCase) {
        List<String> apiNames = list.stream().map(ApiRequestPO::getApiName).collect(Collectors.toList());
        List<String> hasList = apiRequestMapper.queryByApiNames(projectPO.getId(), apiNames);
        if (!hasList.isEmpty()) {
            throw new BusinessException("命名" + Arrays.toString(hasList.toArray()) + "已存在");
        }
        int count = apiRequestMapper.insertAll(list);
        if (autoCase && count > 0) {
            List<ApiTestCasePO> caseList = new ArrayList<>();
            ApiTestCasePO apiTestCasePO;
            for (ApiRequestPO apiRequestPO : list) {
                apiTestCasePO = new ApiTestCasePO();
                apiTestCasePO.init();
                apiTestCasePO.setProjectId(projectPO.getId());
                apiTestCasePO.setCaseName(apiRequestPO.getApiDesc());
                apiTestCasePO.setCaseDesc(apiRequestPO.getApiName());
                apiTestCasePO.setScript(String.format("http.request('%s')", apiRequestPO.getApiName()));
                caseList.add(apiTestCasePO);
            }
            apiTestCaseMapper.insertAll(caseList);
        }
        return count;
    }

}
