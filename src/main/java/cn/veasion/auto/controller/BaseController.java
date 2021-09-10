package cn.veasion.auto.controller;

import cn.veasion.auto.utils.JwtTokenUtils;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;

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

}
