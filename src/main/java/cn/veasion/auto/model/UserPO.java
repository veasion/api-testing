package cn.veasion.auto.model;

import lombok.Data;

/**
 * User
 *
 * @author luozhuowei
 * @date 2021/9/10
 */
@Data
public class UserPO extends BasePO<Integer> {

    private String username;
    private String password;
    private String role;

}
