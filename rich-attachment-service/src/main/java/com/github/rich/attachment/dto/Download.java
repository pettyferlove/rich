package com.github.rich.attachment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.InputStream;
import java.io.Serializable;

/**
 * @author Petty
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Download implements Serializable {
    private static final long serialVersionUID = 7338869685391377248L;

    private String fileName;

    private String contentType;

    private InputStream inputStream;

}
