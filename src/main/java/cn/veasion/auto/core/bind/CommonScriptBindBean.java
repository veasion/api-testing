package cn.veasion.auto.core.bind;

import cn.veasion.auto.utils.EvalAnalysisUtils;
import cn.veasion.auto.utils.JavaScriptUtils;
import cn.veasion.auto.utils.RSAUtils;
import com.alibaba.fastjson.JSON;
import jdk.nashorn.api.scripting.ScriptObjectMirror;
import jdk.nashorn.internal.objects.NativeDate;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * CommonScriptBindBean
 *
 * @author luozhuowei
 * @date 2021/9/11
 */
@Component
public class CommonScriptBindBean extends AbstractScriptBindBean {

    private static final Random RAND = new Random(System.currentTimeMillis());

    public Object jsonValue(String jsonPath, Object jsonObject) {
        Object object;
        if (jsonObject instanceof String) {
            object = JSON.parse((String) jsonObject);
        } else {
            object = JavaScriptUtils.toJavaObject(jsonObject);
        }
        return EvalAnalysisUtils.parse(jsonPath, new HashMap<String, Object>() {{
            put("$", object);
        }});
    }

    public Object eval(String str, Object obj) {
        return EvalAnalysisUtils.eval(str, JavaScriptUtils.toJavaObject(obj));
    }

    public int randInt(int max, int min) {
        return (int) (min + Math.random() * (max - min + 1));
    }

    public String randMobile() {
        return "1" + randCode(10);
    }

    public void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public String md5(String str) {
        return DigestUtils.md5Hex(str.getBytes(Charset.forName("UTF-8")));
    }

    public String base64(String str) {
        return RSAUtils.base64Encode(str.getBytes(Charset.forName("UTF-8")));
    }

    public String rsa(String str, String publicKey) throws Exception {
        return RSAUtils.encrypt(str, publicKey, null, false);
    }

    public String rsa2(String str, String publicKey) throws Exception {
        return RSAUtils.encrypt(str, publicKey, null, true);
    }

    public String randCode(Integer length) {
        if (length == null) {
            length = 8;
        }
        StringBuilder sb = new StringBuilder();
        while (length > 0) {
            if (length >= 8) {
                length -= 8;
                sb.append(String.format("%08d", RAND.nextInt(100000000)));
            } else {
                length -= 1;
                sb.append(RAND.nextInt(10));
            }
        }
        return sb.toString();
    }

    public String maxsub(String str, int maxLen, String end) {
        if (str != null && str.length() > maxLen) {
            return str.substring(0, maxLen) + (end != null ? end : "");
        }
        return str;
    }

    public String formatDate(Object date, String pattern) {
        Date newDate = null;
        if (date instanceof NativeDate) {
            newDate = new Date((long) NativeDate.getTime(date));
        } else if (date instanceof Date) {
            newDate = (Date) date;
        } else if (date instanceof Number) {
            newDate = new Date(((Number) date).longValue());
        } else if (date instanceof ScriptObjectMirror) {
            ScriptObjectMirror obj = (ScriptObjectMirror) date;
            if ("Date".equalsIgnoreCase(obj.getClassName())) {
                double time = (double) obj.callMember("getTime");
                newDate = new Date((long) time);
            }
        }
        if (newDate == null) {
            return String.valueOf(date);
        }
        return new SimpleDateFormat(pattern).format(newDate);
    }

    public Object randomOneElement(Object array) {
        Object[] objects = randomElements(array, 1);
        return objects != null && objects.length > 0 ? objects[0] : null;
    }

    public Object[] randomElements(Object array, int randomCount) {
        Object[] objects = null;
        if (array instanceof List) {
            objects = ((List) array).toArray();
        } else if (array instanceof Object[]) {
            objects = (Object[]) array;
        } else if (array instanceof ScriptObjectMirror) {
            ScriptObjectMirror mirror = (ScriptObjectMirror) array;
            if (mirror.isArray()) {
                objects = mirror.values().toArray();
            }
        }
        if (objects == null) {
            return null;
        }
        if (objects.length <= randomCount) {
            return objects;
        }
        List<Integer> indexList = IntStream.range(0, objects.length).boxed().collect(Collectors.toList());
        int newIdx = 0;
        Object[] newArray = new Object[randomCount];
        do {
            int randomIdx = (int) (Math.random() * indexList.size());
            newArray[newIdx++] = objects[indexList.remove(randomIdx)];
        } while (--randomCount > 0);
        return newArray;
    }

}
