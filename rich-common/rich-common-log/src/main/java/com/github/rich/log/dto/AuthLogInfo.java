package com.github.rich.log.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
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
public class AuthLogInfo implements Serializable {
    private static final long serialVersionUID = -7348389195027603683L;

    private String userId;

    private String loginName;

    private LocalDateTime loginTime;

    private String loginType;

    private String loginIp;

    private String userAgent;

    private String requestMethod;

}
