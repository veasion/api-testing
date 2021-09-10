package cn.veasion.auto.service;

import cn.veasion.auto.model.ApiLogPO;
import com.github.pagehelper.Page;

import java.util.List;

/**
 * ApiLogService
 *
 * @author luozhuowei
 * @date 2021/9/10
 */
public interface ApiLogService {

    Page<ApiLogPO> listPage(ApiLogPO apiLogPO, int pageIndex, int pageSize);

    int addAllWithTx(List<ApiLogPO> list);

}
