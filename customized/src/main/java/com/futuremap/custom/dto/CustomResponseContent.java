package com.futuremap.custom.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomResponseContent {

    private Integer code;

    private Integer status;

    private Object data;

    private String message;
}
