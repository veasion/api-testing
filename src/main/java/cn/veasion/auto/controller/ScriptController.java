package cn.veasion.auto.controller;

import cn.veasion.auto.core.ScriptContext;
import cn.veasion.auto.core.ScriptExecutor;
import cn.veasion.auto.model.R;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * ScriptController
 *
 * @author luozhuowei
 * @date 2021/9/13
 */
@RestController
@RequestMapping("/api/script")
public class ScriptController extends BaseController {

    @Resource
    private ScriptExecutor scriptExecutor;

    @PostMapping("/runScript")
    public R<Object> runScript(@RequestBody JSONObject body) {
        notNull(body);
        String script = body.getString("script");
        Integer projectId = body.getInteger("projectId");
        ScriptContext scriptContext = scriptExecutor.createScriptContext(projectId);
        Object result = scriptExecutor.executeScript(script, scriptContext);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("result", result);
        resultMap.put("logs", scriptContext.getApiLogList());
        return R.ok(resultMap);
    }

}
