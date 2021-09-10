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

    Page<ApiRequestPO> listPage(ApiRequestPO apiRequestPO, int pageIndex, int pageSize);

    void saveOrUpdateWithTx(ApiRequestPO apiRequestPO);
}
