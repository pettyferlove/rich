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
public class EmailMessage extends Message {
    private static final long serialVersionUID = -1319965212586428898L;
    private String subject;
}
