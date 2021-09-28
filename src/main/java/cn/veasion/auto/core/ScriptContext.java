package cn.veasion.auto.core;

import cn.veasion.auto.core.bind.EnvScriptBindBean;
import cn.veasion.auto.core.bind.ScriptBindBean;
import cn.veasion.auto.model.ApiExecuteStrategyPO;
import cn.veasion.auto.model.ApiLogPO;
import cn.veasion.auto.model.ApiRequestPO;
import cn.veasion.auto.model.ApiTestCasePO;
import cn.veasion.auto.model.ProjectPO;
import cn.veasion.auto.utils.HttpUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * ScriptContext
 *
 * @author luozhuowei
 * @date 2021/9/10
 */
public class ScriptContext {

    private ApiLogPO refLog;
    private ProjectPO project;
    private ApiExecuteStrategyPO strategy;
    private final List<ApiLogPO> apiLogList = new ArrayList<>();
    private final Map<String, ScriptBindBean> root = new HashMap<>();
    private final Map<String, Object> contextMap = new ConcurrentHashMap<>();
    private final ThreadLocal<ApiTestCasePO> threadLocalCase = new ThreadLocal<>();
    private final List<RequestProcessor> requestProcessors = new ArrayList<>();
    private final List<ResponseProcessor> responseProcessors = new ArrayList<>();

    public ApiLogPO getRefLog() {
        return refLog;
    }

    public Integer getProjectId() {
        return this.getProject().getId();
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

    public Map<String, Object> getContextMap() {
        return contextMap;
    }

    public EnvScriptBindBean getEnv() {
        for (ScriptBindBean scriptBindBean : getRoot().values()) {
            if (scriptBindBean instanceof EnvScriptBindBean) {
                return (EnvScriptBindBean) scriptBindBean;
            }
        }
        return null;
    }

    ThreadLocal<ApiTestCasePO> getThreadLocalCase() {
        return threadLocalCase;
    }

    public ApiTestCasePO getTestCase() {
        return threadLocalCase.get();
    }

    public ApiLogPO buildApiLog(ApiRequestPO requestPO) {
        return buildApiLog(requestPO, false);
    }

    public ApiLogPO buildApiLog(ApiRequestPO requestPO, boolean refLog) {
        ApiLogPO apiLog = new ApiLogPO();
        apiLog.init();
        if (refLog) {
            apiLog.setRefId("0");
        } else if (this.refLog != null) {
            apiLog.setRefId(this.refLog.getId());
        }
        apiLog.setProjectId(getProjectId());
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
        if (refLog) {
            this.refLog = apiLog;
        } else {
            synchronized (apiLogList) {
                apiLogList.add(apiLog);
            }
        }
        return apiLog;
    }

    void setRefLog(ApiLogPO refLog) {
        this.refLog = refLog;
    }

    public List<ApiLogPO> getApiLogList() {
        return apiLogList;
    }

    public ScriptContext addRequestProcessor(RequestProcessor method) {
        if (method != null) {
            this.requestProcessors.add(method);
        }
        return this;
    }

    public ScriptContext addResponseProcessor(ResponseProcessor method) {
        if (method != null) {
            this.responseProcessors.add(method);
        }
        return this;
    }

    public void requestProcessor(HttpUtils.HttpRequest request) {
        requestProcessors.forEach(p -> p.handle(request));
    }

    public void responseProcessor(Object response, int httpStatus, ApiLogPO log) {
        responseProcessors.forEach(p -> p.handle(response, httpStatus, log));
    }

    @FunctionalInterface
    public interface RequestProcessor {
        void handle(HttpUtils.HttpRequest request);
    }

    @FunctionalInterface
    public interface ResponseProcessor {
        void handle(Object response, int httpStatus, ApiLogPO log);
    }

}
