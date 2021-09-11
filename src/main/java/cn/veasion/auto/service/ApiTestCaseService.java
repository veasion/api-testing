package cn.veasion.auto.service;

import cn.veasion.auto.model.ApiTestCasePO;
import com.github.pagehelper.Page;

/**
 * ApiTestCaseService
 *
 * @author luozhuowei
 * @date 2021/9/10
 */
public interface ApiTestCaseService {

    ApiTestCasePO getById(int id);

    Page<ApiTestCasePO> listPage(ApiTestCasePO apiTestCasePO, int pageIndex, int pageSize);

    void saveOrUpdate(ApiTestCasePO apiTestCasePO);

    int delete(int id);

}
