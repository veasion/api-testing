package cn.veasion.auto.utils;

import cn.veasion.auto.exception.ScriptException;
import com.alibaba.fastjson.JSON;
import jdk.nashorn.api.scripting.ScriptObjectMirror;
import jdk.nashorn.internal.objects.NativeArray;
import jdk.nashorn.internal.runtime.Property;
import jdk.nashorn.internal.runtime.ScriptFunction;
import jdk.nashorn.internal.runtime.ScriptObject;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * JavaScriptUtils
 *
 * @author luozhuowei
 * @date 2020/6/11
 */
@SuppressWarnings({"unused", "restriction"})
public class JavaScriptUtils {

    public static <T> List<T> toArray(ScriptObjectMirror o, Class<T> c) {
        if (o == null || o.isFunction()) {
            return null;
        }
        if (o.isEmpty()) {
            return new ArrayList<>();
        }
        return JSON.parseArray(JSON.toJSONString(o.values()), c);
    }

    public static <T> T toObject(ScriptObjectMirror o, Class<T> c) {
        if (o == null || o.isFunction()) {
            return null;
        }
        if (o.isArray()) {
            throw new ClassCastException("object is array");
        } else {
            return JSON.parseObject(JSON.toJSONString(o), c);
        }
    }

    public static Object toJavaObject(Object obj) {
        if (obj instanceof ScriptObjectMirror) {
            ScriptObjectMirror mirror = (ScriptObjectMirror) obj;
            if (mirror.isFunction()) {
                return mirror;
            }
            if (mirror.isArray()) {
                List<Object> list = new ArrayList<>(mirror.size());
                for (Object value : mirror.values()) {
                    list.add(toJavaObject(value));
                }
                return list;
            } else {
                Map<String, Object> map = new HashMap<>(mirror.size());
                for (String key : mirror.keySet()) {
                    map.put(key, toJavaObject(mirror.get(key)));
                }
                return map;
            }
        }
        return obj;
    }

    public static ScriptException getJavaScriptException(Exception e) {
        return getJavaScriptException(null, e);
    }

    public static ScriptException getJavaScriptException(String fileName, Exception e) {
        Throwable throwable = e;
        if (e instanceof RuntimeException && e.getCause() != null) {
            throwable = e.getCause();
        }
        if (throwable instanceof InvocationTargetException) {
            throwable = ((InvocationTargetException) throwable).getTargetException();
        }
        return new ScriptException(fileName, throwable != null ? throwable : e);
    }

    public static boolean isNull(Object object) {
        return object == null || "undefined".equals(String.valueOf(object));
    }

    public static boolean isEmpty(String str) {
        return isNull(str) || "".equals(str);
    }

    public static String log(String name, Object[] args) {
        StringBuilder message = new StringBuilder();
        if (args != null && args.length > 0) {
            message.append("execute => ").append(name).append("(");
            try {
                for (Object arg : args) {
                    appendObject(message, arg);
                    message.append(", ");
                }
                message.setLength(message.length() - 2);
            } catch (Exception e) {
                message.append("...");
            }
            message.append(")");
        } else {
            message.append("execute => ").append(name).append("()");
        }
        return message.toString();
    }

    public static void appendObject(StringBuilder sb, Object obj) {
        if (obj instanceof String) {
            if (((String) obj).indexOf("\n") > 0) {
                sb.append("...");
            } else {
                sb.append("\"").append(obj).append("\"");
            }
        } else if (obj instanceof ScriptFunction) {
            sb.append("[function]");
        } else if (obj instanceof NativeArray) {
            appendObject(sb, ((NativeArray) obj).asObjectArray());
        } else if (obj instanceof Object[]) {
            sb.append("[");
            Object[] array = (Object[]) obj;
            for (int i = 0; i < array.length; i++) {
                appendObject(sb, array[i]);
                if (i < array.length - 1) {
                    sb.append(", ");
                }
            }
            sb.append("]");
        } else if (obj instanceof ScriptObjectMirror) {
            ScriptObjectMirror mirror = (ScriptObjectMirror) obj;
            if (mirror.isArray()) {
                appendObject(sb, mirror.values().toArray());
            } else if (mirror.isFunction()) {
                sb.append("[function]");
            } else {
                sb.append("{");
                for (String key : mirror.keySet()) {
                    sb.append(key).append(": ");
                    appendObject(sb, mirror.get(key));
                    sb.append(", ");
                }
                if (mirror.keySet().size() > 0) {
                    sb.setLength(sb.length() - 2);
                }
                sb.append("}");
            }
        } else if (obj instanceof ScriptObject && obj.getClass().getSimpleName().startsWith("JO")) {
            sb.append("{");
            Property[] properties = ((ScriptObject) obj).getMap().getProperties();
            for (int i = 0; i < properties.length; i++) {
                sb.append(" ").append(properties[i].getKey()).append(": ");
                appendObject(sb, properties[i].getObjectValue((ScriptObject) obj, null));
                if (i < properties.length - 1) {
                    sb.append(", ");
                } else {
                    sb.append(" ");
                }
            }
            sb.append("}");
        } else {
            sb.append(obj);
        }
    }

    public static String formatToString(Object obj) {
        if (obj == null || obj instanceof String) {
            return (String) obj;
        }
        StringBuilder sb = new StringBuilder();
        appendObject(sb, obj);
        return sb.toString();
    }

}
