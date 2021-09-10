package cn.veasion.auto.exception;

/**
 * EvalException
 *
 * @author luozhuowei
 * @date 2021/9/10
 */
public class EvalException extends RuntimeException {

    public EvalException(String message) {
        super(message);
    }

    public EvalException(String message, Throwable cause) {
        super(message, cause);
    }

    public EvalException(Throwable cause) {
        super(cause);
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
