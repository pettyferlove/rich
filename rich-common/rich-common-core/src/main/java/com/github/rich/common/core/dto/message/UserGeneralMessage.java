package com.github.rich.common.core.dto.message;

import com.github.rich.common.core.dto.Message;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author Petty
 * @see com.github.rich.message.entity.SystemMessage
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class UserGeneralMessage extends Message {

    private static final long serialVersionUID = 3246234137698968545L;

    private Integer type;

    private Integer level;

    private String businessName;

    private String businessId;

    private String businessPageAddress;

    private String editPageAddress;

    private String viewPageAddress;
}
