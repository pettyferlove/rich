package com.github.rich.message.dto;

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
public class Message implements Serializable {
    private static final long serialVersionUID = 5741649455277084479L;

    /**
     * 消息简要描述（可作为标题）
     */
    private String message;
    private String time;
    private String deliver;
    private String receiver;
    /**
     * 具体内容
     */
    private String content;
}
