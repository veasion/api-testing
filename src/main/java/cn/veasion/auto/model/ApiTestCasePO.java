package cn.veasion.auto.model;

import lombok.Data;

/**
 * 测试用例
 *
 * @author veasion
 * @date 2021-09-10
 */
@Data
public class ApiTestCasePO extends BasePO {

    /**
     * 项目id
     */
    private Integer projectId;
    /**
     * 用例名称
     */
    private String caseName;
    /**
     * 用例描述
     */
    private String caseDesc;
    /**
     * 模块
     */
    private String module;
    /**
     * 作者
     */
    private String author;
    /**
     * js脚本
     */
    private String script;

}
