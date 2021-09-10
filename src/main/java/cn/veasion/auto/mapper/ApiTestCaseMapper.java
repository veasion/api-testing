package cn.veasion.auto.mapper;

import org.apache.ibatis.annotations.Mapper;
import cn.veasion.auto.model.ApiTestCasePO;
import java.util.List;

/**
 * 测试用例
 *
 * @author veasion
 * @date 2021-09-10
 */
@Mapper
public interface ApiTestCaseMapper {

    /**
     * 新增
     */
    int insert(ApiTestCasePO obj);

    /**
     * 批量新增
     */
    int insertAll(List<ApiTestCasePO> list);

    /**
     * 修改不为空字段
     */
    int update(ApiTestCasePO obj);

    /**
     * 修改全部字段
     */
    int updateAll(ApiTestCasePO obj);

    /**
     * 删除
     */
    int deleteById(Integer id);

    /**
     * 根据id查询
     */
    ApiTestCasePO queryById(Integer id);

    /**
     * 查询list
     */
    List<ApiTestCasePO> queryList();

}
