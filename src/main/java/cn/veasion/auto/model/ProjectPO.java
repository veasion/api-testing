package cn.veasion.auto.model;

import lombok.Data;

/**
 * 项目
 *
 * @author veasion
 * @date 2021-09-10
 */
@Data
public class ProjectPO extends BasePO<Integer> {

    /**
     * 项目名称
     */
    private String name;
    /**
     * 项目描述
     */
    private String description;

    /**
     * 项目配置
     */
    private ProjectConfigPO projectConfig;

}
