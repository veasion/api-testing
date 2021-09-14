package cn.veasion.auto;

import cn.veasion.auto.core.StrategyExecutor;
import cn.veasion.auto.model.ApiExecuteStrategyPO;
import cn.veasion.auto.model.ApiLogPO;
import cn.veasion.auto.model.ApiLogVO;
import cn.veasion.auto.model.ApiRequestPO;
import cn.veasion.auto.model.ApiTestCasePO;
import cn.veasion.auto.model.ProjectConfigPO;
import cn.veasion.auto.model.ProjectPO;
import cn.veasion.auto.service.ApiExecuteStrategyService;
import cn.veasion.auto.service.ApiLogService;
import cn.veasion.auto.utils.Constants;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.github.pagehelper.Page;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import javax.annotation.Resource;

/**
 * 整体业务测试
 *
 * @author luozhuowei
 * @date 2021/9/12
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BusinessProcessTest extends BaseTest {

    private static JSONObject context = new JSONObject();

    @Resource
    private ApiLogService apiLogService;
    @Resource
    private StrategyExecutor strategyExecutor;
    @Resource
    private ApiExecuteStrategyService apiExecuteStrategyService;

    @Test
    @Order(2)
    @DisplayName("新增项目")
    public void addProject() throws Exception {
        ProjectPO projectPO = new ProjectPO();
        projectPO.setName("测试项目" + randCode());
        projectPO.setDescription("描述");
        projectPO.setIsAvailable(1);

        JSONObject data = postRequest("/api/project/add", projectPO);
        System.out.println("新增项目：" + data);
        assertResponse(data);
        context.put("projectId", data.getString(JSON_DATA_KEY));
    }

    @Test
    @Order(4)
    @DisplayName("新增项目配置")
    public void addProjectConfig() throws Exception {
        Integer projectId = context.getInteger("projectId");
        ProjectConfigPO projectConfigPO = new ProjectConfigPO();
        projectConfigPO.setOpenReqLog(Constants.YES);
        projectConfigPO.setBeforeScript("log.info('脚本前执行');");
        projectConfigPO.setAfterScript("log.info('脚本后执行');");
        projectConfigPO.setExceptionScript("log.error('脚本异常执行');");
        JSONObject global = new JSONObject();
        global.put("baseUrl", "http://127.0.0.1:8080");
        projectConfigPO.setGlobalVarJson(global.toJSONString());

        ProjectPO projectPO = new ProjectPO();
        projectPO.setId(projectId);
        projectPO.setProjectConfig(projectConfigPO);
        JSONObject data = postRequest("/api/project/update", projectPO);
        assertResponse(data);
    }

    @Test
    @Order(6)
    @DisplayName("新增api请求")
    public void addApiRequest() throws Exception {
        Integer projectId = context.getInteger("projectId");
        ApiRequestPO apiRequestPO = new ApiRequestPO();
        apiRequestPO.setProjectId(projectId);
        apiRequestPO.setApiName(projectId + "_baidu");
        apiRequestPO.setApiDesc("百度一下");
        apiRequestPO.setUrl("http://www.baidu.com");
        apiRequestPO.setMethod("GET");
        apiRequestPO.setHeadersJson("{\"baseUrl\": \"${baseUrl}\"}");
        apiRequestPO.setIsAvailable(1);

        JSONObject data = postRequest("/api/apiRequest/add", apiRequestPO);
        System.out.println("api请求：" + data);
        assertResponse(data);
        context.put("apiName", apiRequestPO.getApiName());
        context.put("apiRequestId", data.getString(JSON_DATA_KEY));
    }

    @Test
    @Order(8)
    @DisplayName("新增测试用例")
    public void addApiTestCase() throws Exception {
        String apiName = context.getString("apiName");
        Integer projectId = context.getInteger("projectId");
        ApiTestCasePO apiTestCasePO = new ApiTestCasePO();
        apiTestCasePO.setProjectId(projectId);
        apiTestCasePO.setCaseName("测试百度接口");
        apiTestCasePO.setCaseDesc("测试 http://baidu.com");
        apiTestCasePO.setModule("通用");
        apiTestCasePO.setAuthor("luozhuowei");
        apiTestCasePO.setScript(String.format("http.request('%s');", apiName));

        JSONObject data = postRequest("/api/apiTestCase/add", apiTestCasePO);
        System.out.println("测试用例：" + data);
        assertResponse(data);
        context.put("apiTestCaseId", data.getInteger(JSON_DATA_KEY));
    }

    @Test
    @Order(10)
    @DisplayName("新增策略_指定case_定时任务")
    public void addStrategy_2_1() throws Exception {
        Integer projectId = context.getInteger("projectId");
        Integer apiTestCaseId = context.getInteger("apiTestCaseId");
        ApiExecuteStrategyPO strategyPO = new ApiExecuteStrategyPO();
        strategyPO.setProjectId(projectId);
        strategyPO.setName("策略_指定case_定时任务");
        strategyPO.setDesc("定时执行");
        strategyPO.setType(ApiExecuteStrategyPO.TYPE_CASES);
        strategyPO.setStrategy(ApiExecuteStrategyPO.STRATEGY_JOB);
        strategyPO.setIsAvailable(1);
        strategyPO.setJobCron("0 */10 * * * ?");

        JSONObject data = postRequest("/api/apiExecuteStrategy/add", strategyPO);
        System.out.println("新增策略: " + data);
        assertResponse(data);
        Integer apiExecuteStrategyId = data.getInteger(JSON_DATA_KEY);
        context.put("apiExecuteStrategyId", apiExecuteStrategyId);

        JSONObject relation = new JSONObject();
        relation.put("id", apiExecuteStrategyId);
        relation.put("caseIds", new Integer[]{apiTestCaseId});

        data = postRequest("/api/apiExecuteStrategy/addCasesWithTx", relation);
        System.out.println("新增用例策略关联: " + data);
        assertResponse(data);
    }

    @Test
    @Order(12)
    @DisplayName("执行策略")
    public void strategyExecutor() throws InterruptedException {
        Integer apiExecuteStrategyId = context.getInteger("apiExecuteStrategyId");
        strategyExecutor.run(apiExecuteStrategyService.getById(apiExecuteStrategyId));
        Thread.sleep(1500);
        Page<ApiLogPO> page = apiLogService.queryByStrategyId(apiExecuteStrategyId, null, 1, 1);
        System.out.println("策略执行日志：\n" + JSONObject.toJSONString(page, SerializerFeature.PrettyFormat));
        if (page.size() > 0) {
            ApiLogVO apiLogVO = new ApiLogVO();
            apiLogVO.setRefId(page.get(0).getId());
            System.out.println();
            page = apiLogService.listPage(apiLogVO, 1, Constants.MAX_PAGE_SIZE);
            System.out.println("详细执行日志：\n" + JSONObject.toJSONString(page, SerializerFeature.PrettyFormat));
        }
    }

}
