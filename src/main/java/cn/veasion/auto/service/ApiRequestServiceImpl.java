package cn.veasion.auto.service;

import cn.veasion.auto.exception.BusinessException;
import cn.veasion.auto.mapper.ApiRequestMapper;
import cn.veasion.auto.model.ApiRequestPO;
import cn.veasion.auto.model.ApiRequestVO;
import cn.veasion.auto.utils.StringUtils;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

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
    public void saveOrUpdate(ApiRequestPO apiRequestPO) {
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
}
