package cn.veasion.auto.service;

import cn.veasion.auto.model.ApiExecutStrategyPO;
import cn.veasion.auto.model.ApiTestCasePO;
import com.github.pagehelper.Page;

import java.util.List;

/**
 * ApiExecutStrategyService
 *
 * @author luozhuowei
 * @date 2021/9/10
 */
public interface ApiExecutStrategyService {

    ApiExecutStrategyPO getById(int id);

    Page<ApiExecutStrategyPO> listPage(ApiExecutStrategyPO apiExecutStrategyPO, int pageIndex, int pageSize);

    void saveOrUpdateWithTx(ApiExecutStrategyPO apiExecutStrategyPO);

    List<ApiTestCasePO> listApiTestCase(int id);

    int deleteCasesWithTx(int id, List<Integer> caseIds);

    int addCasesWithTx(int id, List<Integer> caseIds);

}
