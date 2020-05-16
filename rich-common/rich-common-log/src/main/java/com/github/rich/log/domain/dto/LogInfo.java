package com.github.rich.log.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Petty
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogInfo implements Serializable {

    private static final long serialVersionUID = 1260581614882346927L;
    private String userId;

    private String clientId;

    private String requestIp;

    private String requestUrl;

    private String userAgent;

    private String requestMethod;

    private Integer logType;

}
