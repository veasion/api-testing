package cn.veasion.auto.utils;

import java.util.UUID;

/**
 * StringUtils
 *
 * @author luozhuowei
 * @date 2021/9/12
 */
public class StringUtils {

    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    public static boolean hasText(String str) {
        return org.springframework.util.StringUtils.hasText(str);
    }

    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

}
