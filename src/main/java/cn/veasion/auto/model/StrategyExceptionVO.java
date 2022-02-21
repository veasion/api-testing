package cn.veasion.auto.model;

import java.util.List;

/**
 * StrategyExceptionVO
 *
 * @author luozhuowei
 * @date 2022/2/21
 */
public class StrategyExceptionVO {

    private ApiTestCasePO testCase;
    private List<ApiLogPO> apiLogList;
    private String error;

    public ApiTestCasePO getTestCase() {
        return testCase;
    }

    public void setTestCase(ApiTestCasePO testCase) {
        this.testCase = testCase;
    }

    public List<ApiLogPO> getApiLogList() {
        return apiLogList;
    }

    public void setApiLogList(List<ApiLogPO> apiLogList) {
        this.apiLogList = apiLogList;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
