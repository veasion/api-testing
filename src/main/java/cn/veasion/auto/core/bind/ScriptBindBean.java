package cn.veasion.auto.core.bind;

import cn.veasion.auto.core.ScriptContext;

/**
 * ScriptBindBean
 *
 * @author luozhuowei
 * @date 2021/9/11
 */
public interface ScriptBindBean {

    boolean root();

    String var();

    void setScriptContext(ScriptContext scriptContext);

    default ScriptBindBean getObject() {
        return this;
    }

}
