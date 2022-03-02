package cn.veasion.auto.core.bind;

import cn.veasion.auto.core.ScriptContext;
import cn.veasion.auto.core.ScriptExecutor;
import cn.veasion.auto.exception.ScriptException;
import cn.veasion.auto.model.ApiTestCasePO;
import cn.veasion.auto.model.ProjectConfigPO;
import cn.veasion.auto.model.ProjectPO;
import cn.veasion.auto.service.ApiTestCaseService;
import cn.veasion.auto.service.ProjectService;
import cn.veasion.auto.utils.Constants;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Map;

/**
 * TestCaseScriptBindBean
 *
 * @author luozhuowei
 * @date 2022/3/2
 */
@Component
public class TestCaseScriptBindBean extends AbstractScriptBindBean {

    @Resource
    private ScriptExecutor scriptExecutor;
    @Resource
    private ProjectService projectService;
    @Resource
    private ApiTestCaseService apiTestCaseService;

    public Object runScript(Object testCaseId) throws Exception {
        return runScript(testCaseId, null);
    }

    public Object runScript(Object testCaseId, Object params) throws Exception {
        ApiTestCasePO testCasePO = getTestCase(testCaseId);
        ProjectPO projectPO = projectService.getById(testCasePO.getProjectId());
        ScriptContext scriptContext = scriptExecutor.createScriptContext(projectPO);
        if (params != null) {
            scriptContext.getContextMap().put(Constants.TEST_CASE_PARAMS_KEY, params);
        }
        ProjectConfigPO projectConfig = projectPO.getProjectConfig();
        if (projectConfig != null && StringUtils.hasText(projectConfig.getBeforeScript())) {
            scriptExecutor.executeScript(projectConfig.getBeforeScript(), scriptContext);
        }
        try {
            scriptExecutor.execute(testCasePO, scriptContext);
        } catch (Exception e) {
            throw new ScriptException("执行依赖用例“" + testCasePO.getCaseName() + "”失败：" + e.getMessage(), e);
        }
        return scriptContext.getContextMap().getOrDefault(Constants.TEST_CASE_RESULT_KEY, Collections.EMPTY_MAP);
    }

    public Object getParam() {
        return scriptContext.getContextMap().get(Constants.TEST_CASE_PARAMS_KEY);
    }

    public Object getParamValue(String key) {
        return getParamValue(key, null);
    }

    public Object getParamValue(String key, Object defaultValue) {
        Object value = scriptContext.getContextMap().get(Constants.TEST_CASE_PARAMS_KEY);
        if (value instanceof Map && ((Map) value).containsKey(key)) {
            return ((Map) value).get(key);
        }
        return defaultValue;
    }

    public void setResult(Object object) {
        scriptContext.getContextMap().put(Constants.TEST_CASE_RESULT_KEY, object);
    }

    private ApiTestCasePO getTestCase(Object testCaseId) {
        if (testCaseId == null) {
            throw new ScriptException("测试用例ID为空");
        }
        ApiTestCasePO testCasePO = apiTestCaseService.getById(Integer.parseInt(testCaseId.toString()));
        if (testCasePO == null) {
            throw new ScriptException("策略不存在");
        }
        return testCasePO;
    }

}
