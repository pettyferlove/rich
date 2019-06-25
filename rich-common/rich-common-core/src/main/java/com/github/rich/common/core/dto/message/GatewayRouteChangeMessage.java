package com.github.rich.common.core.dto.message;

import com.github.rich.common.core.dto.Message;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author Petty
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class GatewayRouteChangeMessage extends Message {
    private static final long serialVersionUID = 1238686113901700573L;

    String routeId;

}
