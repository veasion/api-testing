package cn.veasion.auto.mapper;

import org.apache.ibatis.annotations.Mapper;
import cn.veasion.auto.model.UserPO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户
 *
 * @author veasion
 * @date 2021-09-10
 */
@Mapper
public interface UserMapper {

    /**
     * 新增
     */
    int insert(UserPO obj);

    /**
     * 批量新增
     */
    int insertAll(List<UserPO> list);

    /**
     * 修改不为空字段
     */
    int update(UserPO obj);

    /**
     * 修改全部字段
     */
    int updateAll(UserPO obj);

    /**
     * 删除
     */
    int deleteById(Integer id);

    /**
     * 根据id查询
     */
    UserPO queryById(Integer id);

    /**
     * 根据id查询
     */
    UserPO queryByUsername(@Param("username") String username, @Param("notEqId") Integer notEqId);

    /**
     * 查询list
     */
    List<UserPO> queryList(UserPO userPO);

}
