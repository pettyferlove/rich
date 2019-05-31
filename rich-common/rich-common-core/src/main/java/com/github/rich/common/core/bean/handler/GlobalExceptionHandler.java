package com.github.rich.common.core.bean.handler;

import com.github.rich.common.core.constant.CommonConstant;
import com.github.rich.common.core.exception.BaseException;
import com.github.rich.common.core.exception.BaseRuntimeException;
import com.github.rich.common.core.model.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 公共异常处理
 *
 * @author Petty
 */
@Slf4j
@ControllerAdvice("com.github.rich" )
@ResponseBody
public class GlobalExceptionHandler {
    /**
     * 处理BaseRuntimeException异常
     *
     * @param request  请求对象
     * @param response 响应对象
     * @param ex       异常
     * @return data
     */
    @ExceptionHandler(BaseRuntimeException.class)
    public R baseExceptionHandler(HttpServletRequest request, HttpServletResponse response, BaseRuntimeException ex) {
        log.error(ex.getMessage(), ex);
        response.setStatus(ex.getStatus());
        return new R(ex, ex.getStatus());
    }

    /**
     * 处理RuntimeException异常
     *
     * @param request  请求对象
     * @param response 响应对象
     * @param ex       异常
     * @return data
     */
    @ExceptionHandler(RuntimeException.class)
    public R runtimeExceptionHandler(HttpServletRequest request, HttpServletResponse response, RuntimeException ex) {
        log.error(ex.getMessage(), ex);
        response.setStatus(CommonConstant.EX_OTHER_CODE);
        return new R(ex);
    }

    /**
     * 处理BaseException异常
     *
     * @param request  请求对象
     * @param response 响应对象
     * @param ex       异常
     * @return data
     */
    @ExceptionHandler(BaseException.class)
    public R otherExceptionHandler(HttpServletRequest request, HttpServletResponse response, BaseException ex) {
        log.error(ex.getMessage(), ex);
        response.setStatus(ex.getStatus());
        return new R(ex, ex.getStatus());
    }


    /**
     * 处理Exception异常
     *
     * @param request  请求对象
     * @param response 响应对象
     * @param ex       异常
     * @return data
     */
    @ExceptionHandler(Exception.class)
    public R exceptionHandler(HttpServletRequest request, HttpServletResponse response, Exception ex) {
        log.error(ex.getMessage(), ex);
        response.setStatus(CommonConstant.EX_OTHER_CODE);
        return new R(ex);
    }
}
