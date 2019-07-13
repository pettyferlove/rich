package com.github.rich.log.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * @author Petty
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class OperateLogInfo extends LogInfo {
    private static final long serialVersionUID = 3170507945353919151L;

    private String trace;

    private Integer operateType;

    private String description;

    private LocalDateTime operateTime;

}
