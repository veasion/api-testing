package cn.veasion.auto.core.bind;

import cn.veasion.auto.core.ScriptContext;

/**
 * AbstractScriptBindBean
 *
 * @author luozhuowei
 * @date 2021/9/11
 */
public abstract class AbstractScriptBindBean implements ScriptBindBean {

    protected ScriptContext scriptContext;

    @Override
    public String var() {
        return getClass().getSimpleName().replace(ScriptBindBean.class.getSimpleName(), "").toLowerCase();
    }

    @Override
    public void setScriptContext(ScriptContext scriptContext) {
        this.scriptContext = scriptContext;
    }

    @Override
    public ScriptBindBean getObject() {
        return this;
    }
}
