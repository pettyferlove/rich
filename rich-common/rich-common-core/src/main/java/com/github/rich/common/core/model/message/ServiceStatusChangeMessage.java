package com.github.rich.common.core.model.message;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;
import java.util.Map;

/**
 properties:
 mail:
 smtp:
 auth: true
 starttls:
 enable: true
 required: true
 * 服务状态变更消息
 * @author Petty
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ServiceStatusChangeMessage extends Message implements Serializable{
    private static final long serialVersionUID = 8703554218364207017L;
    private String serviceId;
    private String exception;
    private String exceptionMessage;
    private long version;
    private String status;
    private Map<String, Object> info;
}
