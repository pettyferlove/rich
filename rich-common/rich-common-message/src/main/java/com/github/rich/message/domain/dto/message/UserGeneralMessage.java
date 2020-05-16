package com.github.rich.message.domain.dto.message;

import com.github.rich.message.domain.dto.Message;
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

    private String id;

    private Integer type;

    private Integer level;

    private String avatar;

    private String businessName;

    private String businessId;

    private String businessPageAddress;

    private String editPageAddress;

    private String viewPageAddress;
}
