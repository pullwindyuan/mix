package com.futuremap.erp.module.orderCost.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "SwitchOrder", description = "")
public class SwitchOrder implements Serializable {


    private static final long serialVersionUID = 1L;
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 序号
     */
    @ApiModelProperty(value = "序号")
    private Integer orderIdx;


    /**
     * 订单号
     */
    @ApiModelProperty(value = "订单号")
    @Excel(name = "订单号")
    private String orderDetailCode;

    /**
     * 订单行号
     */
    @ApiModelProperty(value = "订单行号")
    @Excel(name = "订单行号")
    private String orderNumber;

    /**
     * 月份
     */
    @ApiModelProperty(value = "月份")
    @Excel(name = "月份")
    private String ddmonth;
    /**
     * 挪货数量
     */
    @ApiModelProperty(value = "挪货数量")
    @Excel(name = "挪货数量")
    private Integer orderCount;
    /**
     * 存货编码
     */
    @ApiModelProperty(value = "存货编码")
    @Excel(name = "存货编码")
    private String productCode;


    /**
     * 存货名称
     */
    @ApiModelProperty(value = "存货名称")
    @Excel(name = "存货名称")
    private String productName;


    /**
     * 当月发票数量
     */
    @ApiModelProperty(value = "当月发票数量")
    private Integer invoiceCount;


    /**
     * 挪货订单号
     */
    @ApiModelProperty(value = "挪货订单号")
    @Excel(name = "挪货订单号")
    private String orderSwitch;

    /**
     * 挪货订单行号
     */
    @ApiModelProperty(value = "挪货订单行号")
    @Excel(name = "挪货订单行号")
    private String orderNumberSwitch;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String note;


}
