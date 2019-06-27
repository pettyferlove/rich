package com.github.rich.message.vo;

import com.github.rich.common.core.constants.CommonConstant;
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
public class ServerMessage implements Serializable {
    private static final long serialVersionUID = -4144501047469456841L;

    private int status = CommonConstant.WS_SUCCESS;

    private long timestamp;

    private String message;

    private int level;

    private String date;

    public ServerMessage(String message) {
        this.timestamp = System.currentTimeMillis();
        this.message = message;
    }

    public ServerMessage(String message, int level) {
        this.timestamp = System.currentTimeMillis();
        this.message = message;
        this.level = level;
    }

    public ServerMessage(String message, int level, String date) {
        this.timestamp = System.currentTimeMillis();
        this.message = message;
        this.level = level;
        this.date = date;
    }
}
