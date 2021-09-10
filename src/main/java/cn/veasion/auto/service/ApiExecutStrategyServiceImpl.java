package cn.veasion.auto.service;

import cn.veasion.auto.mapper.ApiExecutStrategyMapper;
import cn.veasion.auto.mapper.StrategyCaseRelationMapper;
import cn.veasion.auto.model.ApiExecutStrategyPO;
import cn.veasion.auto.model.ApiTestCasePO;
import cn.veasion.auto.model.StrategyCaseRelationPO;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * ApiExecutStrategyServiceImpl
 *
 * @author luozhuowei
 * @date 2021/9/10
 */
@Service
public class ApiExecutStrategyServiceImpl implements ApiExecutStrategyService {

    @Resource
    private ApiExecutStrategyMapper apiExecutStrategyMapper;
    @Resource
    private StrategyCaseRelationMapper strategyCaseRelationMapper;

    @Override
    public ApiExecutStrategyPO getById(int id) {
        return apiExecutStrategyMapper.queryById(id);
    }

    @Override
    public Page<ApiExecutStrategyPO> listPage(ApiExecutStrategyPO apiExecutStrategyPO, int pageIndex, int pageSize) {
        PageHelper.startPage(pageIndex, pageSize);
        return (Page<ApiExecutStrategyPO>) apiExecutStrategyMapper.queryList(apiExecutStrategyPO);
    }

    @Override
    public void saveOrUpdateWithTx(ApiExecutStrategyPO apiExecutStrategyPO) {
        if (apiExecutStrategyPO.getId() == null) {
            apiExecutStrategyMapper.insert(apiExecutStrategyPO);
        } else {
            apiExecutStrategyMapper.update(apiExecutStrategyPO);
        }
    }

    @Override
    public List<ApiTestCasePO> listApiTestCase(int id) {
        return strategyCaseRelationMapper.queryList(id);
    }

    @Override
    public int deleteCasesWithTx(int id, List<Integer> caseIds) {
        return strategyCaseRelationMapper.delete(id, caseIds);
    }

    @Override
    public int addCasesWithTx(int id, List<Integer> caseIds) {
        List<StrategyCaseRelationPO> list = new ArrayList<>(caseIds.size());
        for (Integer caseId : caseIds) {
            StrategyCaseRelationPO relationPO = new StrategyCaseRelationPO();
            relationPO.setCaseId(caseId);
            relationPO.setExecutStrategyId(id);
            list.add(relationPO);
        }
        return strategyCaseRelationMapper.insertAll(list);
    }
}
