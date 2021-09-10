package cn.veasion.auto.model;

import lombok.Data;
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
public class BasePO implements Serializable {

    private Integer id;
    private Integer isAvailable = 1;
    private Integer isDeleted = 0;
    private Date createTime;
    private Date updateTime;

}
