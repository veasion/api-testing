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
    public boolean root() {
        return false;
    }

    @Override
    public String var() {
		String name = getClass().getSimpleName().replace(ScriptBindBean.class.getSimpleName(), "");
        return name.substring(0, 1).toLowerCase() + name.substring(1);
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
