package com.github.rich.log.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.rich.log.constants.OperateType;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Petty
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class OperateLogInfo extends LogInfo {
    private static final long serialVersionUID = 3170507945353919151L;

    private Integer operateType;

    private String description;

    private LocalDateTime operateTime;

}
