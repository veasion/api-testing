package cn.veasion.auto.exception;

/**
 * AssertException
 *
 * @author luozhuowei
 * @date 2021/9/10
 */
public class AssertException extends RuntimeException {

    public AssertException(String message) {
        super(message);
    }

    public AssertException(String message, Throwable cause) {
        super(message, cause);
    }

    public AssertException(Throwable cause) {
        super(cause);
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }

}
