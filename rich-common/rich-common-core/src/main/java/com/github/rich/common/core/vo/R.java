package com.github.rich.common.core.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.rich.common.core.constants.CommonConstant;
import lombok.Data;

import java.io.Serializable;

/**
 * 响应数据1包装
 * @author Petty
 * @param <T>
 */

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class R<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    private int status = CommonConstant.SUCCESS;

    private long timestamp;

    private String message;

    private T data;

    public R() {
        super();
    }

    public R(T data) {
        super();
        this.data = data;
        this.timestamp = System.currentTimeMillis();
    }

    public R(T data, String message) {
        super();
        this.data = data;
        this.message = message;
        this.timestamp = System.currentTimeMillis();
    }

    public R(int status, String message) {
        this.status = status;
        this.message = message;
        this.timestamp = System.currentTimeMillis();
    }

    public R(Throwable e) {
        super();
        this.message = e.getMessage();
        this.status = CommonConstant.FAIL;
        this.timestamp = System.currentTimeMillis();
    }

    public R(Throwable e, int code) {
        super();
        this.message = e.getMessage();
        this.status = code;
        this.timestamp = System.currentTimeMillis();
    }


}
