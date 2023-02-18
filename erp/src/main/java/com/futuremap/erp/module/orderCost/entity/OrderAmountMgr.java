package com.futuremap.erp.module.orderCost.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;



@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="客户账期", description="客户账期")
public class OrderAmountMgr {


    private static final long serialVersionUID = 1L;
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 客户编号
     */
    @ApiModelProperty(value = "客户编号")
    @Excel(name = "客户编号")
    private String customerCode;

    /**
     * 客户名称
     */
    @ApiModelProperty(value = "客户名称")
    @Excel(name = "客户")
    private String customerName;

    /**
     * 账期（天）
     */
    @ApiModelProperty(value = "账期(天)")
    @Excel(name = "账期(天)")
    private Integer backDay;



}
