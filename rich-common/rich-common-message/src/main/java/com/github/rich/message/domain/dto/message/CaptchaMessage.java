package com.github.rich.message.domain.dto.message;

import com.github.rich.message.domain.dto.Message;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author Petty
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CaptchaMessage extends Message {
    private static final long serialVersionUID = -7579802995588252306L;

    private String captchaCode;
}
