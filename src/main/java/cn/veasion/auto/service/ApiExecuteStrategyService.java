package cn.veasion.auto.service;

import cn.veasion.auto.model.ApiExecuteStrategyPO;
import cn.veasion.auto.model.ApiExecuteStrategyVO;
import cn.veasion.auto.model.ApiLogPO;
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

    ApiExecuteStrategyPO getById(Integer id);

    ApiExecuteStrategyVO queryStrategyById(Integer id);

    List<ApiExecuteStrategyVO> list(ApiExecuteStrategyVO apiExecuteStrategy);

    Page<ApiExecuteStrategyVO> listPage(ApiExecuteStrategyVO apiExecuteStrategy, int pageIndex, int pageSize);

    void saveOrUpdate(ApiExecuteStrategyVO apiExecuteStrategy);

    List<ApiTestCasePO> listApiTestCase(int id);

    int deleteCases(int id, List<Integer> caseIds);

    int addCasesWithTx(int id, List<Integer> caseIds);

    int delete(int id);

    List<ApiExecuteStrategyVO> queryCronExecutableStrategy(Integer projectId);

    void triggerCronUpdate(Integer projectId, boolean isAdd);

    void runStrategy(ApiExecuteStrategyVO strategy);

}
