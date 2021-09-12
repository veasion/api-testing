package cn.veasion.auto.service;

import cn.veasion.auto.exception.BusinessException;
import cn.veasion.auto.mapper.UserMapper;
import cn.veasion.auto.model.UserPO;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

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
    public UserPO getById(int id) {
        return userMapper.queryById(id);
    }

    @Override
    public UserPO queryByUsername(String username) {
        return userMapper.queryByUsername(username, null);
    }

    @Override
    public Page<UserPO> listPage(UserPO userPO, int pageIndex, int pageSize) {
        PageHelper.startPage(pageIndex, pageSize);
        return (Page<UserPO>) userMapper.queryList(userPO);
    }

    @Override
    public void saveOrUpdate(UserPO userPO) {
        if (userPO.getUsername() != null) {
            UserPO u = userMapper.queryByUsername(userPO.getUsername(), userPO.getId());
            if (u != null) {
                throw new BusinessException("账号已存在");
            }
        }
        if (userPO.getId() == null) {
            userPO.init();
            userMapper.insert(userPO);
        } else {
            userPO.setUpdateTime(new Date());
            userMapper.update(userPO);
        }
    }

    @Override
    public int delete(int id) {
        return userMapper.deleteById(id);
    }
}
