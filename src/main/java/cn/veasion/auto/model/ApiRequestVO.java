package cn.veasion.auto.model;

import lombok.Data;

/**
 * ApiRequestVO
 *
 * @author luozhuowei
 * @date 2021/9/14
 */
@Data
public class ApiRequestVO extends ApiRequestPO {

    private String projectName;
    private String query;

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
}
