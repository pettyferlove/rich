package com.github.rich.message.vo;

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
public class ClientMessage implements Serializable {

    private static final long serialVersionUID = -3000666697910927220L;
    private String name;

}
