package cn.veasion.auto.model;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 执行策略
 *
 * @author veasion
 * @date 2021-09-10
 */
@Data
public class ApiExecuteStrategyPO extends BasePO<Integer> {

    public static final Integer TYPE_ALL_CASE = 1;
    public static final Integer TYPE_CASES = 2;
    public static final Integer TYPE_SCRIPT = 3;
    public static final Integer STRATEGY_JOB = 1;
    public static final Integer STRATEGY_PRESSURE = 2;
    public static final Integer STATUS_PART_SUC = 1;
    public static final Integer STATUS_ALL_SUC = 2;
    public static final Integer STATUS_FAIL = 3;
    public static final Integer THREAD_STRATEGY_TIME = 1;
    public static final Integer THREAD_STRATEGY_LOOP = 2;
    public static final Integer THREAD_ENV_TYPE_DEFAULT = 1;
    public static final Integer THREAD_ENV_TYPE_CUSTOM = 2;

    /**
     * 项目id
     */
    private Integer projectId;
    /**
     * 名称
     */
    private String name;
    /**
     * 描述
     */
    private String desc;
    /**
     * 类型: 1 所有case 2 指定case 3 自定义脚本
     */
    private Integer type;
    /**
     * 执行策略: 1 定时任务 2 压测
     */
    private Integer strategy;
    /**
     * 任务执行CRON
     */
    private String jobCron;
    /**
     * 并发线程数
     */
    private Integer threadCount;
    /**
     * 线程创建策略（瞬时创建、间隔增量创建、指定时间内创建）
     */
    private String threadStrategyJson;
    /**
     * js脚本
     */
    private String script;
    /**
     * 最后一次执行状态：1 部分成功 2 全部成功 3 失败
     */
    private Integer status;

	private List<ApiTestCasePO> successCaseList;
    private List<StrategyExceptionVO> exceptionList;

	public void appendSuccess(ApiTestCasePO testCase) {
		if (successCaseList == null) {
			successCaseList = new ArrayList<>();
		}
		testCase.setScript(null);
		successCaseList.add(testCase);
	}
	
    public void appendException(ApiTestCasePO testCase, List<ApiLogPO> apiLogList, String error) {
        if (exceptionList == null) {
            exceptionList = new ArrayList<>();
        }
        StrategyExceptionVO strategyExceptionVO = new StrategyExceptionVO();
        testCase.setScript(null);
        strategyExceptionVO.setError(error);
        strategyExceptionVO.setTestCase(testCase);
        strategyExceptionVO.setApiLogList(apiLogList);
        exceptionList.add(strategyExceptionVO);
    }

    public ThreadStrategy toThreadStrategy() {
        if (!StringUtils.hasText(threadStrategyJson)) {
            return null;
        }
        return JSON.parseObject(threadStrategyJson, ThreadStrategy.class);
    }

    @Data
    public static class ThreadStrategy {
        Integer type; // 压测类型：1 按时长 2 按次数
        Integer loopCount; // 执行多少次
        Long timeInMillis; // 压测时间
        Long intervalInMillis; // 并发间隔时间
        Integer userEnvType; // 用户环境类型: 1 使用项目默认用户 2 使用自定义用户
        List<Map<String, Object>> userEnvMaps; // 压测用户信息（账号密码等变量）
    }

}
