package com.futuremap.erp.common.warning;

import cn.afterturn.easypoi.excel.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="警示信息", description="")
public class WarningBean {
    @ApiModelProperty(value = "警示名称")
    private String name;
    @ApiModelProperty(value = "警示编码")
    private Integer code;
    @ApiModelProperty(value = "警示等级")
    private Integer level;
    @ApiModelProperty(value = "提示颜色")
    private String color;
    @ApiModelProperty(value = "警示信息描述")
    @Excel(name = "警示信息")
    @Getter
    private String desc;
}
