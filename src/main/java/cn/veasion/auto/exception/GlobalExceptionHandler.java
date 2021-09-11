package cn.veasion.auto.exception;

import cn.veasion.auto.model.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public R<Object> handleException(Exception e) {
        log.error("系统异常", e);
        if (e instanceof BusinessException) {
            return R.error(e.getMessage());
        } else {
            return R.error("系统异常: " + e.getMessage());
        }
    }

}
