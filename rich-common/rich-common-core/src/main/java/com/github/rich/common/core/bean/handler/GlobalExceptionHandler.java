package com.github.rich.common.core.bean.handler;

import com.github.rich.common.core.constants.CommonConstant;
import com.github.rich.common.core.exception.BaseException;
import com.github.rich.common.core.exception.BaseRuntimeException;
import com.github.rich.common.core.model.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
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
@ControllerAdvice("com.github.rich")
@ResponseBody
public class GlobalExceptionHandler {
    /**
     * 处理BaseRuntimeException异常
     *
     * @param response 响应对象
     * @param ex       异常
     * @return data
     */
    @ExceptionHandler(BaseRuntimeException.class)
    public R baseExceptionHandler(HttpServletResponse response, BaseRuntimeException ex) {
        log.error(ex.getMessage(), ex);
        response.setStatus(ex.getStatus());
        return new R(ex, ex.getStatus());
    }

    /**
     * 处理RuntimeException异常
     *
     * @param response 响应对象
     * @param ex       异常
     * @return data
     */
    @ExceptionHandler(RuntimeException.class)
    public R runtimeExceptionHandler(HttpServletResponse response, RuntimeException ex) {
        log.error(ex.getMessage(), ex);
        response.setStatus(CommonConstant.EX_OTHER_CODE);
        return new R(ex);
    }

    /**
     * 处理BaseException异常
     *
     * @param response 响应对象
     * @param ex       异常
     * @return data
     */
    @ExceptionHandler(BaseException.class)
    public R otherExceptionHandler(HttpServletResponse response, BaseException ex) {
        log.error(ex.getMessage(), ex);
        response.setStatus(ex.getStatus());
        return new R(ex, ex.getStatus());
    }


    /**
     * 处理Exception异常
     *
     * @param response 响应对象
     * @param ex       异常
     * @return data
     */
    @ExceptionHandler(Exception.class)
    public R exceptionHandler(HttpServletResponse response, Exception ex) {
        log.error(ex.getMessage(), ex);
        response.setStatus(CommonConstant.EX_OTHER_CODE);
        return new R(ex);
    }

    /**
     * 处理数据验证异常
     *
     * @param response 响应对象
     * @param ex       异常
     * @return data
     */
    @ExceptionHandler(BindException.class)
    public R methodArgumentNotValidExceptionHandler(HttpServletResponse response, BindException ex) {
        log.error(ex.getMessage(), ex);
        BindingResult bindingResult = ex.getBindingResult();
        StringBuilder errorMessage = new StringBuilder(bindingResult.getFieldErrors().size() << 4);
        errorMessage.append("invalid request:");
        for (int i = 0; i < bindingResult.getFieldErrors().size(); i++) {
            if (i > 0) {
                errorMessage.append(",");
            }
            FieldError error = bindingResult.getFieldErrors().get(i);
            errorMessage.append(error.getField());
            errorMessage.append(":");
            errorMessage.append(error.getDefaultMessage());
        }
        response.setStatus(CommonConstant.FAIL);
        return new R(CommonConstant.FAIL, errorMessage.toString());
    }

}
