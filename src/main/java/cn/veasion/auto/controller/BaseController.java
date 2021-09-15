package cn.veasion.auto.controller;

import cn.veasion.auto.exception.BusinessException;
import cn.veasion.auto.utils.JwtTokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;

/**
 * BaseController
 *
 * @author luozhuowei
 * @date 2021/9/10
 */
@Slf4j
public class BaseController {

    public Integer getUserId(HttpServletRequest request) {
        String token = request.getHeaders(JwtTokenUtils.TOKEN_HEADER).nextElement().replace(JwtTokenUtils.TOKEN_PREFIX, "");
        return JwtTokenUtils.getUserId(token);
    }

    protected void notNull(Object obj) {
        notNull(obj, "参数不能为空");
    }

    protected void notNull(Object obj, String message) {
        if (obj == null) {
            throw new BusinessException(message);
        }
    }

    protected void notEmpty(String str, String message) {
        if (!StringUtils.hasText(str)) {
            throw new BusinessException(message);
        }
    }

    protected void notEmpty(Collection list, String message) {
        if (list == null || list.isEmpty()) {
            throw new BusinessException(message);
        }
    }

    protected void checkNotNull(String message, Supplier<?>... suppliers) {
        for (Supplier<?> supplier : suppliers) {
            if (supplier.get() == null) {
                throw new BusinessException(message);
            }
        }
    }
}
