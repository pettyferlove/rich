package com.github.rich.log.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.rich.log.constants.OperateType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Petty
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class OperateLogInfo implements Serializable {
    private static final long serialVersionUID = 3170507945353919151L;

    private String userId;

    private Integer operateType;

    private String description;

    private LocalDateTime operationTime;

    private String requestIp;

    private String userAgent;

    private String requestMethod;

}
