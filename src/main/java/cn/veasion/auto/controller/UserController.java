package cn.veasion.auto.controller;

import cn.veasion.auto.model.Page;
import cn.veasion.auto.model.R;
import cn.veasion.auto.model.UserPO;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * UserController
 *
 * @author luozhuowei
 * @date 2021/9/10
 */
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Resource
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/pageList")
    public Page<UserPO> pageList(@RequestParam(required = false, defaultValue = "1") int current,
                                 @RequestParam(required = false, defaultValue = "10") int size,
                                 String username) {
        return Page.ok(new ArrayList<>(), 0);
    }

    @GetMapping("/list")
    public R<List<UserPO>> list(String username) {
        return R.ok(null);
    }

    @GetMapping("/getUserById")
    public R<UserPO> selectById(@RequestParam("userId") Integer userId) {
        return R.ok(null);
    }

    @PostMapping("/add")
    public R<Object> add(@RequestBody UserPO jobUser) {
        jobUser.setUsername(jobUser.getUsername().trim());
        jobUser.setPassword(jobUser.getPassword().trim());
        if (!(jobUser.getPassword().length() >= 4 && jobUser.getPassword().length() <= 20)) {
            return R.error("密码长度必须在[4-20]范围内");
        }
        jobUser.setPassword(bCryptPasswordEncoder.encode(jobUser.getPassword()));
        return R.ok();
    }

    @PostMapping(value = "/update")
    public R<Object> update(@RequestBody UserPO jobUser) {
        if (StringUtils.hasText(jobUser.getPassword())) {
            jobUser.setPassword(bCryptPasswordEncoder.encode(jobUser.getPassword()));
        }
        return R.ok();
    }

    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public R<Object> remove(Integer id) {
        return R.ok();
    }

    @PostMapping(value = "/updatePwd")
    public R<Object> updatePwd(@RequestBody UserPO jobUser) {
        String password = jobUser.getPassword();
        if (password == null || password.trim().length() == 0) {
            return R.error("密码不可为空");
        }
        password = password.trim();
        if (!(password.length() >= 4 && password.length() <= 20)) {
            return R.error("密码长度必须在[4-20]范围内");
        }
        jobUser.setPassword(bCryptPasswordEncoder.encode(password));
        return R.ok();
    }

}
