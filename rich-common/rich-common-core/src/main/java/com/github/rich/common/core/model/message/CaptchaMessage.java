package com.github.rich.common.core.model.message;

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
}
