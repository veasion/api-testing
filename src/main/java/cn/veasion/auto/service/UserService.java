package cn.veasion.auto.service;

import cn.veasion.auto.model.UserPO;

/**
 * UserService
 *
 * @author luozhuowei
 * @date 2021/9/10
 */
public interface UserService {

    UserPO queryByUsername(String username);

}
