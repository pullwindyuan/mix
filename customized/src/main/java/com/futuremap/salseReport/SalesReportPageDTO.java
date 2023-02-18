package com.futuremap.salseReport;

import com.futuremap.base.bean.PageDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.domain.Sort;

@EqualsAndHashCode(callSuper = true)
@Data
public class SalesReportPageDTO extends PageDTO {
    @ApiModelProperty(value = "时间", notes = "\"dateTime\": \"2021-12-13 00:00:00\"", required = true)
    private String dateTime;
}
