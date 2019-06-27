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
public class GatewayRouteChangeStatusMessage extends Message {
    private static final long serialVersionUID = -8892199101520418673L;

    private String routeName;

    private String instanceId;

    private String serviceId;

    private String host;

    private String port;

    private Integer status;

}
