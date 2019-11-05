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

    private long timestamp;

    private String message;

    private T data;

    public R() {
        super();
        this.message = CommonConstant.SUCCESS_MESSAGE;
        this.timestamp = System.currentTimeMillis();
    }

    public R(T data) {
        super();
        this.data = data;
        this.message = CommonConstant.SUCCESS_MESSAGE;
        this.timestamp = System.currentTimeMillis();
    }

    public R(T data, String message) {
        super();
        this.data = data;
        this.message = message;
        this.timestamp = System.currentTimeMillis();
    }

    public R(String message) {
        this.message = message;
        this.timestamp = System.currentTimeMillis();
    }

    public R(Throwable e) {
        super();
        this.message = e.getMessage();
        this.timestamp = System.currentTimeMillis();
    }


}
