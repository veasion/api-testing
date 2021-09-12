package cn.veasion.auto.model;

import java.io.Serializable;

/**
 * R
 *
 * @author luozhuowei
 * @date 2021/9/10
 */
public class R<T> implements Serializable {

    private static final int SUCCESS_CODE = 200;
    private static final int FAIL_CODE = 500;
    private static final R<Object> SUCCESS = new R<>(null);

    private int code;
    private String message;
    private T data;

    public R(T data) {
        this.data = data;
        this.code = SUCCESS_CODE;
    }

    public R(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static R<Object> ok() {
        return SUCCESS;
    }

    public static <T> R<T> ok(T data) {
        return new R<>(data);
    }

    public static <T> R<T> error(String message) {
        return new R<>(FAIL_CODE, message, null);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
