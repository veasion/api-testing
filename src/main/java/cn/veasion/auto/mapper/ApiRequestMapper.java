package cn.veasion.auto.mapper;

import cn.veasion.auto.model.ApiRequestVO;
import org.apache.ibatis.annotations.Mapper;
import cn.veasion.auto.model.ApiRequestPO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 请求接口
 *
 * @author veasion
 * @date 2021-09-10
 */
@Mapper
public interface ApiRequestMapper {

    /**
     * 新增
     */
    int insert(ApiRequestPO obj);

    /**
     * 批量新增
     */
    int insertAll(List<ApiRequestPO> list);

    /**
     * 修改不为空字段
     */
    int update(ApiRequestPO obj);

    /**
     * 修改全部字段
     */
    int updateAll(ApiRequestPO obj);

    /**
     * 删除
     */
    int deleteById(Integer id);

    /**
     * 根据id查询
     */
    ApiRequestPO queryById(Integer id);

    /**
     * 查询根据apiName
     */
    ApiRequestPO queryByApiName(@Param("apiName") String apiName, @Param("projectId") Integer projectId, @Param("notEqId") Integer notEqId);

    /**
     * 查询list
     */
    List<ApiRequestVO> queryList(ApiRequestVO apiRequestVO);

    /**
     * 搜索
     */
    List<ApiRequestVO> search(@Param("projectId") Integer projectId, @Param("query") String query, @Param("limit") Integer limit);

    List<String> queryAllApiName(@Param("projectId") Integer projectId);

    List<String> queryByApiNames(@Param("projectId") Integer projectId, @Param("apiNames") List<String> apiNames);

    List<ApiRequestPO> queryByIds(List<Integer> ids);

}
