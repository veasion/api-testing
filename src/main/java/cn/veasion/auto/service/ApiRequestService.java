package cn.veasion.auto.service;

import cn.veasion.auto.model.ApiRequestPO;
import com.github.pagehelper.Page;

/**
 * ApiRequestService
 *
 * @author luozhuowei
 * @date 2021/9/10
 */
public interface ApiRequestService {

    ApiRequestPO getById(int id);

    ApiRequestPO queryByApiName(String apiName, Integer projectId);

    Page<ApiRequestPO> listPage(ApiRequestPO apiRequestPO, int pageIndex, int pageSize);

    void saveOrUpdate(ApiRequestPO apiRequestPO);

    int delete(int id);

}
