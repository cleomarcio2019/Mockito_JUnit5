package com.marcio.tech.api.resources.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter

@AllArgsConstructor
public class StandardError {

    private LocalDateTime timesatmp;
    private Integer status;
    private String error;
    private String path;

}
