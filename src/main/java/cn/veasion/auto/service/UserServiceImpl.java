package cn.veasion.auto.service;

import cn.veasion.auto.mapper.UserMapper;
import cn.veasion.auto.model.UserPO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * UserServiceImpl
 *
 * @author luozhuowei
 * @date 2021/9/10
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public UserPO queryByUsername(String username) {
        return userMapper.queryByUsername(username);
    }
}
