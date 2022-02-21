package cn.veasion.auto.service;

import cn.veasion.auto.model.ApiRequestPO;
import cn.veasion.auto.model.ApiRequestVO;
import cn.veasion.auto.model.ProjectPO;
import com.github.pagehelper.Page;

import java.util.List;

/**
 * ApiRequestService
 *
 * @author luozhuowei
 * @date 2021/9/10
 */
public interface ApiRequestService {

    ApiRequestPO getById(int id);

    ApiRequestPO queryByApiName(String apiName, Integer projectId);

    Page<ApiRequestVO> listPage(ApiRequestVO apiRequestVO, int pageIndex, int pageSize);

    List<ApiRequestVO> search(Integer projectId, String query, Integer limit);

    void saveOrUpdate(ApiRequestPO apiRequestPO);

    int delete(int id);

    List<String> queryAllApiName(Integer projectId);

    int importWithTx(ProjectPO projectPO, List<ApiRequestPO> list, boolean autoCase);

}
