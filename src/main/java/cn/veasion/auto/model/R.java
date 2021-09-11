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
    private String msg;
    private T content;

    public R(T content) {
        this.content = content;
        this.code = SUCCESS_CODE;
    }

    public R(int code, String msg, T content) {
        this.code = code;
        this.msg = msg;
        this.content = content;
    }

    public static R<Object> ok() {
        return SUCCESS;
    }

    public static <T> R<T> ok(T content) {
        return new R<>(content);
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

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }
}
