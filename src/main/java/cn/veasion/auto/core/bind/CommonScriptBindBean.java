package cn.veasion.auto.core.bind;

import cn.veasion.auto.exception.ScriptException;
import jdk.nashorn.api.scripting.ScriptObjectMirror;
import jdk.nashorn.internal.objects.NativeDate;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * CommonScriptBindBean
 *
 * @author luozhuowei
 * @date 2021/9/11
 */
@Component
public class CommonScriptBindBean extends AbstractScriptBindBean {

    private static final Random RAND = new Random(System.currentTimeMillis());

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

    public void assertResult(boolean flag, Object message) {
        String msg = String.valueOf(message);
        if (!flag) {
            throw new ScriptException(msg);
        }
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

}
