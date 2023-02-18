package com.futuremap.salseReport.salesDaily;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SalesDailyDTO {
    @ApiModelProperty(value = "时间", notes = "\"dateTime\": \"2021-12-13 00:00:00\"", required = true)
    private String dateTime;
}