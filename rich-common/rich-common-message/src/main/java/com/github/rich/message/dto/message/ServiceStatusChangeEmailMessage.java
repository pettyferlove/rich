package com.github.rich.message.dto.message;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;
import java.util.Map;

/**
 * @author Petty
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ServiceStatusChangeEmailMessage extends EmailMessage implements Serializable{
    private static final long serialVersionUID = 8703554218364207017L;
    private String serviceId;
    private String exception;
    private String exceptionMessage;
    private long version;
    private String status;
    private Map<String, Object> info;
}
