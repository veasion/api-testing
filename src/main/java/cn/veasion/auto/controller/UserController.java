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

    @GetMapping("/listPage")
    public Page<UserPO> listPage(@RequestParam(required = false, defaultValue = "1") int pageNo,
                                 @RequestParam(required = false, defaultValue = "10") int pageSize,
                                 UserPO userPO) {
        return Page.ok(userService.listPage(userPO, pageNo, pageSize));
    }

    @GetMapping("/list")
    public R<List<UserPO>> list(UserPO userPO) {
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
        notEmpty(userPO.getUsername(), "??????????????????");
        notEmpty(userPO.getPassword(), "??????????????????");
        userPO.setUsername(userPO.getUsername().trim());
        userPO.setPassword(userPO.getPassword().trim());
        if (!(userPO.getPassword().length() >= 4 && userPO.getPassword().length() <= 20)) {
            return R.error("?????????????????????[4-20]?????????");
        }
        userPO.setPassword(bCryptPasswordEncoder.encode(userPO.getPassword()));
        userService.saveOrUpdate(userPO);
        return R.ok(userPO.getId());
    }

    @PostMapping("/update")
    public R<Object> update(@RequestBody UserPO userPO) {
        notNull(userPO.getId(), "id????????????");
        if (StringUtils.hasText(userPO.getPassword())) {
            userPO.setPassword(bCryptPasswordEncoder.encode(userPO.getPassword()));
        }
        userService.saveOrUpdate(userPO);
        return R.ok();
    }

    @PostMapping("/delete")
    public R<Object> delete(@RequestBody Integer id) {
        userService.delete(id);
        return R.ok();
    }

    @PostMapping("/updatePwd")
    public R<Object> updatePwd(@RequestBody UserPO userPO) {
        notNull(userPO.getId(), "id????????????");
        String password = userPO.getPassword();
        notEmpty(password, "??????????????????");
        password = password.trim();
        if (!(password.length() >= 4 && password.length() <= 20)) {
            return R.error("?????????????????????[4-20]?????????");
        }
        userPO.setPassword(bCryptPasswordEncoder.encode(password));
        userService.saveOrUpdate(userPO);
        return R.ok();
    }

}
