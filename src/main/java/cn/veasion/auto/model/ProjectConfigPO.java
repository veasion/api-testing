package cn.veasion.auto.model;

import cn.veasion.auto.utils.Constants;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import org.springframework.util.StringUtils;

/**
 * 全局配置
 *
 * @author veasion
 * @date 2021-09-10
 */
@Data
public class ProjectConfigPO extends BasePO<Integer> {

    /**
     * 项目id
     */
    private Integer projectId;
    /**
     * 记录请求日志
     */
    private Integer openReqLog;
    /**
     * 全局变量（脚本中可以通过${xxx}方式使用全局变量）
     */
    private String globalVarJson;
    /**
     * 前置脚本（策略开始前执行，用来设置登录信息和拦截修改请求响应）
     */
    private String beforeScript;
    /**
     * 后置脚本（策略正常结束后执行，用来监听脚本执行情况，发送邮件通知等）
     */
    private String afterScript;
    /**
     * 异常执行脚本（策略代码异常时执行）
     */
    private String exceptionScript;

    public JSONObject toGlobalVarJson() {
        if (StringUtils.hasText(globalVarJson)) {
            return JSONObject.parseObject(globalVarJson);
        }
        return null;
    }

    @Override
    public void init() {
        super.init();
        if (openReqLog == null) {
            openReqLog = Constants.NO;
        }
    }
}
