package cn.veasion.auto.core;

import cn.veasion.auto.core.bind.EnvScriptBindBean;
import cn.veasion.auto.core.bind.ScriptBindBean;
import cn.veasion.auto.model.ApiExecuteStrategyPO;
import cn.veasion.auto.model.ApiLogPO;
import cn.veasion.auto.model.ApiRequestPO;
import cn.veasion.auto.model.ApiTestCasePO;
import cn.veasion.auto.model.ProjectPO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ScriptContext
 *
 * @author luozhuowei
 * @date 2021/9/10
 */
public class ScriptContext {

    private String refId;
    private Integer projectId;
    private ProjectPO project;
    private ApiExecuteStrategyPO strategy;
    private final List<ApiLogPO> apiLogList = new ArrayList<>();
    private final Map<String, ScriptBindBean> root = new HashMap<>();
    private final ThreadLocal<ApiTestCasePO> threadLocalCase = new ThreadLocal<>();

    public String getRefId() {
        return refId;
    }

    void setRefId(String refId) {
        this.refId = refId;
    }

    public Integer getProjectId() {
        return projectId;
    }

    void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public ProjectPO getProject() {
        return project;
    }

    void setProject(ProjectPO project) {
        this.project = project;
    }

    public ApiExecuteStrategyPO getStrategy() {
        return strategy;
    }

    void setStrategy(ApiExecuteStrategyPO strategy) {
        this.strategy = strategy;
    }

    public Map<String, ScriptBindBean> getRoot() {
        return root;
    }

    public EnvScriptBindBean getEnv() {
        for (ScriptBindBean scriptBindBean : getRoot().values()) {
            if (scriptBindBean instanceof EnvScriptBindBean) {
                return (EnvScriptBindBean) scriptBindBean;
            }
        }
        return null;
    }

    public ThreadLocal<ApiTestCasePO> getThreadLocalCase() {
        return threadLocalCase;
    }

    public ApiLogPO buildApiLog(ApiRequestPO requestPO) {
        return buildApiLog(requestPO, true);
    }

    public ApiLogPO buildApiLog(ApiRequestPO requestPO, boolean batch) {
        ApiLogPO apiLog = new ApiLogPO();
        apiLog.init();
        apiLog.setRefId(refId == null ? "0" : refId);
        apiLog.setProjectId(projectId);
        if (strategy != null) {
            apiLog.setExecuteStrategyId(strategy.getId());
        }
        ApiTestCasePO apiTestCasePO = getThreadLocalCase().get();
        if (apiTestCasePO != null) {
            apiLog.setTestCaseId(apiTestCasePO.getId());
        }
        if (requestPO != null) {
            apiLog.setApiRequestId(requestPO.getId());
            apiLog.setUrl(requestPO.getUrl());
        }
        apiLog.setStatus(ApiLogPO.STATUS_RUNNING);
        if (batch) {
            synchronized (apiLogList) {
                apiLogList.add(apiLog);
            }
        }
        return apiLog;
    }

    public List<ApiLogPO> getApiLogList() {
        return apiLogList;
    }

}
