package cn.veasion.auto.mapper;

import org.apache.ibatis.annotations.Mapper;
import cn.veasion.auto.model.ProjectPO;

import java.util.List;

/**
 * 项目
 *
 * @author veasion
 * @date 2021-09-10
 */
@Mapper
public interface ProjectMapper {

    /**
     * 新增
     */
    int insert(ProjectPO obj);

    /**
     * 批量新增
     */
    int insertAll(List<ProjectPO> list);

    /**
     * 修改不为空字段
     */
    int update(ProjectPO obj);

    /**
     * 修改全部字段
     */
    int updateAll(ProjectPO obj);

    /**
     * 删除
     */
    int deleteById(Integer id);

    /**
     * 根据id查询
     */
    ProjectPO queryById(Integer id);

    /**
     * 查询list
     */
    List<ProjectPO> queryList(ProjectPO projectPO);

    List<ProjectPO> queryByIds(List<Integer> ids);

}
