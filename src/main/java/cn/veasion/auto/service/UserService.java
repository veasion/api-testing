package cn.veasion.auto.service;

import cn.veasion.auto.model.UserPO;
import com.github.pagehelper.Page;

/**
 * UserService
 *
 * @author luozhuowei
 * @date 2021/9/10
 */
public interface UserService {

    UserPO getById(int id);

    UserPO queryByUsername(String username);

    Page<UserPO> listPage(UserPO userPO, int pageIndex, int pageSize);

    void saveOrUpdate(UserPO userPO);

    int delete(int id);
    
}
