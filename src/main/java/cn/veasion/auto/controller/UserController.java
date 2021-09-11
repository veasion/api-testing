package cn.veasion.auto.controller;

import cn.veasion.auto.model.Page;
import cn.veasion.auto.model.R;
import cn.veasion.auto.model.UserPO;
import cn.veasion.auto.service.UserService;
import cn.veasion.auto.utils.Constants;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * UserController
 *
 * @author luozhuowei
 * @date 2021/9/10
 */
@RestController
@RequestMapping("/api/user")
public class UserController extends BaseController {

    @Resource
    private UserService userService;
    @Resource
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/pageList")
    public Page<UserPO> pageList(@RequestParam(required = false, defaultValue = "1") int current,
                                 @RequestParam(required = false, defaultValue = "10") int size,
                                 @RequestParam(required = false) String username) {
        UserPO userPO = new UserPO();
        userPO.setUsername(username);
        return Page.ok(userService.listPage(userPO, current, size));
    }

    @GetMapping("/list")
    public R<List<UserPO>> list(@RequestParam(required = false) String username) {
        UserPO userPO = new UserPO();
        userPO.setUsername(username);
        return R.ok(userService.listPage(userPO, 1, Constants.MAX_PAGE_SIZE));
    }

    @GetMapping({"/getById", "/getUserById"})
    public R<UserPO> getById(@RequestParam(required = false) Integer userId,
                                @RequestParam(required = false) Integer id) {
        if (userId == null) {
            userId = id;
        }
        notNull(userId);
        return R.ok(userService.getById(userId));
    }

    @PostMapping("/add")
    public R<Object> add(@RequestBody UserPO userPO) {
        userPO.setId(null);
        notEmpty(userPO.getUsername(), "账号不能为空");
        notEmpty(userPO.getPassword(), "密码不能为空");
        userPO.setUsername(userPO.getUsername().trim());
        userPO.setPassword(userPO.getPassword().trim());
        if (!(userPO.getPassword().length() >= 4 && userPO.getPassword().length() <= 20)) {
            return R.error("密码长度必须在[4-20]范围内");
        }
        userPO.setPassword(bCryptPasswordEncoder.encode(userPO.getPassword()));
        userService.saveOrUpdate(userPO);
        return R.ok(userPO.getId());
    }

    @PostMapping("/update")
    public R<Object> update(@RequestBody UserPO userPO) {
        notNull(userPO.getId(), "id不能为空");
        if (StringUtils.hasText(userPO.getPassword())) {
            userPO.setPassword(bCryptPasswordEncoder.encode(userPO.getPassword()));
        }
        userService.saveOrUpdate(userPO);
        return R.ok();
    }

    @PostMapping("/remove")
    public R<Object> remove(Integer id) {
        userService.delete(id);
        return R.ok();
    }

    @PostMapping("/updatePwd")
    public R<Object> updatePwd(@RequestBody UserPO userPO) {
        notNull(userPO.getId(), "id不能为空");
        String password = userPO.getPassword();
        notEmpty(password, "密码不可为空");
        password = password.trim();
        if (!(password.length() >= 4 && password.length() <= 20)) {
            return R.error("密码长度必须在[4-20]范围内");
        }
        userPO.setPassword(bCryptPasswordEncoder.encode(password));
        userService.saveOrUpdate(userPO);
        return R.ok();
    }

}
