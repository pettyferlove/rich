package com.github.rich.message.dto.message;

import com.github.rich.message.dto.Message;
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
