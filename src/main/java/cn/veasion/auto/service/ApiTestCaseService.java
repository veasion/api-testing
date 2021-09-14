package cn.veasion.auto.service;

import cn.veasion.auto.model.ApiTestCasePO;
import cn.veasion.auto.model.ApiTestCaseVO;
import com.github.pagehelper.Page;

/**
 * ApiTestCaseService
 *
 * @author luozhuowei
 * @date 2021/9/10
 */
public interface ApiTestCaseService {

    ApiTestCasePO getById(int id);

    Page<ApiTestCaseVO> listPage(ApiTestCaseVO apiTestCaseVO, int pageIndex, int pageSize);

    void saveOrUpdate(ApiTestCasePO apiTestCasePO);

    int delete(int id);

}
