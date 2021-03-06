package cn.veasion.auto.core.bind;

import cn.veasion.auto.exception.AssertException;
import cn.veasion.auto.utils.JavaScriptUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * AssertionsScriptBindBean
 *
 * @author luozhuowei
 * @date 2021/9/14
 */
@Component
public class AssertionsScriptBindBean extends AbstractScriptBindBean {

    public void assertJsonPath(String jsonPath, Object object) {
        assertJsonPath(jsonPath, object, "");
    }

    public void assertJsonPath(String jsonPath, Object object, String message) {
        Object value = ((CommonScriptBindBean) scriptContext.getRoot().get("common")).jsonValue(jsonPath, object);
        if (JavaScriptUtils.isNull(value) || "".equals(value)) {
            try {
                String str = JSON.toJSONString(JavaScriptUtils.toJavaObject(object));
                throwException(message + " => JSON 期望 " + jsonPath + " 不为空，实际JSON数据：" + str);
            } catch (JSONException e) {
                throwException(message + " => JSON 期望 " + jsonPath + " 不为空，实际JSON格式错误：" + e.getMessage());
            }
        }
    }

    public void assertTrue(boolean condition) {
        assertTrue(condition, "");
    }

    public void assertTrue(boolean condition, String message) {
        if (!condition) {
            throwException(message + " => 期望为 true 结果是 false");
        }
    }

    public void assertFalse(boolean condition) {
        assertFalse(condition, "");
    }

    public void assertFalse(boolean condition, String message) {
        if (condition) {
            throwException(message + " => 期望为 false 结果是 true");
        }
    }

    public void assertNull(Object obj) {
        assertNull(obj, "");
    }

    public void assertNull(Object obj, String message) {
        if (!JavaScriptUtils.isNull(obj)) {
            throwException(message + " => 期望为 null 但结果是 " + obj);
        }
    }

    public void assertEmpty(Object obj) {
        assertEmpty(obj, "");
    }

    public void assertEmpty(Object obj, String message) {
        String str = obj == null ? null : String.valueOf(obj);
        if (!JavaScriptUtils.isEmpty(str)) {
            throwException(message + " => 期望为空字符串，但结果是 " + obj);
        }
    }

    public void assertNotNull(Object obj) {
        assertNotNull(obj, "");
    }

    public void assertNotNull(Object obj, String message) {
        if (JavaScriptUtils.isNull(obj)) {
            throwException(message + " => 期望有值但结果是 null");
        }
    }

    public void assertNotEmpty(Object obj) {
        assertNotEmpty(obj, "");
    }

    public void assertNotEmpty(Object obj, String message) {
        String str = obj == null ? null : String.valueOf(obj);
        if (JavaScriptUtils.isEmpty(str)) {
            throwException(message + " => 期望非空字符串，但结果是 " + obj);
        }
    }

    public void assertEquals(Object obj1, Object obj2) {
        assertEquals(obj1, obj2, "");
    }

    public void assertEquals(Object obj1, Object obj2, String message) {
        if (Objects.equals(obj1, obj2)) {
            return;
        }
        if (String.valueOf(obj1).equals(String.valueOf(obj2))) {
            return;
        }
        throwException(message + " => 期望值相等，但结果不相等 " + obj1 + " != " + obj2);
    }

    public void throwException(String message) {
        if (message.length() > 500) {
            message = message.substring(0, 500) + "...";
        }
        throw new AssertException(message);
    }

    @Override
    public boolean root() {
        return true;
    }

}
