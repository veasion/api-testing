package cn.veasion.auto;

import cn.veasion.auto.core.StrategyExecutor;
import cn.veasion.auto.model.ApiExecuteStrategyPO;
import cn.veasion.auto.model.ApiExecuteStrategyVO;
import cn.veasion.auto.model.ApiLogPO;
import cn.veasion.auto.model.ProjectPO;
import cn.veasion.auto.service.ApiExecuteStrategyService;
import cn.veasion.auto.service.ApiLogService;
import cn.veasion.auto.service.ProjectService;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.github.pagehelper.Page;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 压测
 *
 * @author luozhuowei
 * @date 2021/9/12
 */
@Slf4j
public class StrategyPressureTest extends BaseTest {

    @Resource
    private ApiLogService apiLogService;
    @Resource
    private ProjectService projectService;
    @Resource
    private StrategyExecutor strategyExecutor;
    @Resource
    private ApiExecuteStrategyService apiExecuteStrategyService;

    @Test
    @DisplayName("执行策略_压测")
    public void pressure() throws InterruptedException {
        Page<ProjectPO> projects = projectService.listPage(new ProjectPO(), 1, 1);
        if (projects == null || projects.isEmpty()) {
            log.info("无项目列表");
            return;
        }
        ProjectPO projectPO = projects.get(0);
        ApiExecuteStrategyVO strategy = new ApiExecuteStrategyVO();
        strategy.setProjectId(projectPO.getId());
        strategy.setName("策略_脚本_压测");
        strategy.setDesc("压测脚本");
        strategy.setType(ApiExecuteStrategyPO.TYPE_SCRIPT);
        strategy.setStrategy(ApiExecuteStrategyPO.STRATEGY_PRESSURE);
        strategy.setScript("http.get('${baseUrl}/index.html');");
        // 10个并发压测20秒
        strategy.setThreadCount(10);
        ApiExecuteStrategyPO.ThreadStrategy threadStrategy = new ApiExecuteStrategyPO.ThreadStrategy();
        threadStrategy.setType(ApiExecuteStrategyPO.THREAD_STRATEGY_TIME);
        threadStrategy.setTimeInMillis(20 * 1000L);
        strategy.setThreadStrategyJson(JSONObject.toJSONString(threadStrategy));
        strategy.setIsAvailable(1);
        apiExecuteStrategyService.saveOrUpdate(strategy);
        Assertions.assertNotNull(strategy.getId());
        strategyExecutor.run(strategy);
        Thread.sleep(1500);
        Page<ApiLogPO> page = apiLogService.queryByStrategyId(strategy.getId(), null, 1, 1);
        System.out.println("策略执行日志：\n" + JSONObject.toJSONString(page, SerializerFeature.PrettyFormat));
        Map<String, Object> pressureResult = apiLogService.pressureResult(strategy.getId(), page.get(0).getId());
        System.out.println("压测结果分析：\n" + JSONObject.toJSONString(pressureResult, SerializerFeature.PrettyFormat));
    }

}
