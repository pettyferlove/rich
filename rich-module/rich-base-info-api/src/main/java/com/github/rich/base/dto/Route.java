package com.github.rich.base.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Petty
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Route implements Serializable {
    private static final long serialVersionUID = 5185952920075364986L;

    private String code;
    /**
     * Route JSON
     */
    private String route;

}
