package cn.veasion.auto.core.bind;

import cn.veasion.auto.model.ApiLogPO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * LogScriptBindBean
 *
 * @author luozhuowei
 * @date 2021/9/11
 */
@Slf4j
@Component
public class LogScriptBindBean extends AbstractScriptBindBean {

    public void info(String message) {
        log.info(message);
        recordLog(message);
    }

    public void debug(String message) {
        log.debug(message);
        recordLog(message);
    }

    public void error(String message) {
        log.error(message);
        recordLog(message);
    }

    private void recordLog(String message) {
        ApiLogPO refLog = scriptContext.getRefLog();
        if (refLog != null) {
            refLog.appendLog(message);
        }
    }
}
