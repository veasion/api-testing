package cn.veasion.auto.service;

import cn.veasion.auto.model.ApiExecuteStrategyPO;
import cn.veasion.auto.model.ApiTestCasePO;
import com.github.pagehelper.Page;

import java.util.List;

/**
 * ApiExecuteStrategyService
 *
 * @author luozhuowei
 * @date 2021/9/10
 */
public interface ApiExecuteStrategyService {

    ApiExecuteStrategyPO getById(int id);

    List<ApiExecuteStrategyPO> list(ApiExecuteStrategyPO apiExecuteStrategyPO);

    Page<ApiExecuteStrategyPO> listPage(ApiExecuteStrategyPO apiExecuteStrategyPO, int pageIndex, int pageSize);

    void saveOrUpdate(ApiExecuteStrategyPO apiExecuteStrategyPO);

    List<ApiTestCasePO> listApiTestCase(int id);

    int deleteCases(int id, List<Integer> caseIds);

    int addCasesWithTx(int id, List<Integer> caseIds);

    int delete(int id);

    List<ApiExecuteStrategyPO> queryCronExecutableStrategy(Integer projectId);

    void triggerCronUpdate(Integer projectId, boolean isAdd);

    void runStrategy(ApiExecuteStrategyPO strategy);

}
