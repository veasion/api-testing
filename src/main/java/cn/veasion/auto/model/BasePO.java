package cn.veasion.auto.model;

import cn.veasion.auto.utils.Constants;
import cn.veasion.auto.utils.UserUtils;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * BasePO
 *
 * @author luozhuowei
 * @date 2021/9/10
 */
@Getter
@Setter
public class BasePO<T extends Serializable> implements Serializable {

    private T id;
    private Integer isAvailable;
    private Integer isDeleted;
    private String createUsername;
    private Date createTime;
    private Date updateTime;

    public void init() {
        if (isAvailable == null) {
            isAvailable = Constants.YES;
        }
        if (isDeleted == null) {
            isDeleted = Constants.NO;
        }
        if (createUsername == null) {
            createUsername = UserUtils.getUsername();
        }
        if (createTime == null) {
            createTime = new Date();
        }
        if (updateTime == null) {
            updateTime = new Date();
        }
    }
}
