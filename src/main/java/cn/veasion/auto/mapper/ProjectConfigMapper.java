package cn.veasion.auto.mapper;

import org.apache.ibatis.annotations.Mapper;
import cn.veasion.auto.model.ProjectConfigPO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 全局配置
 *
 * @author veasion
 * @date 2021-09-10
 */
@Mapper
public interface ProjectConfigMapper {

    /**
     * 新增
     */
    int insert(ProjectConfigPO obj);

    /**
     * 批量新增
     */
    int insertAll(List<ProjectConfigPO> list);

    /**
     * 修改不为空字段
     */
    int update(ProjectConfigPO obj);

    /**
     * 修改全部字段
     */
    int updateAll(ProjectConfigPO obj);

    /**
     * 删除
     */
    int deleteById(Integer id);

    /**
     * 根据id查询
     */
    ProjectConfigPO queryById(Integer id);

    /**
     * 查询list
     */
    List<ProjectConfigPO> queryList();

    @Select("select * from project_config where project_id = #{projectId} and is_deleted = 0")
    ProjectConfigPO queryByProjectId(@Param("projectId") Integer projectId);

    @Select("select global_var_json from project_config where project_id = #{projectId} and is_deleted = 0")
    String queryGlobalVarJson(@Param("projectId") Integer projectId);

}
