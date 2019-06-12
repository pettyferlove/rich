package com.github.rich.common.core.dto.message;

import com.github.rich.common.core.dto.Message;
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
public class ServiceStatusChangeMessage extends Message implements Serializable{
    private static final long serialVersionUID = 8703554218364207017L;
    private String serviceId;
    private String exception;
    private String exceptionMessage;
    private long version;
    private String status;
    private Map<String, Object> info;
}
