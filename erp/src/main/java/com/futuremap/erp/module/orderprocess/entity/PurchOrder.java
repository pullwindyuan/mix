package com.futuremap.erp.module.orderprocess.entity;

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

/**
 * <p>
 * 
 * </p>
 *
 * @author futuremap
 * @since 2021-06-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="PurchOrder对象", description="")
public class PurchOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 采购订单日期
     */
    @ApiModelProperty(value = "采购订单日期")
    private LocalDateTime dpodate;

    /**
     * 公司id
     */
    @ApiModelProperty(value = "公司id")
    private String companyCode;

    /**
     * 公司名称
     */
    @ApiModelProperty(value = "公司名称")
    private String companyName;

    /**
     * 采购员编码
     */
    @ApiModelProperty(value = "采购员编码")
    @Excel(name = "采购员编码")
    private String cpersoncode;

    /**
     * 采购员名称
     */
    @ApiModelProperty(value = "采购员名称")
    @Excel(name = "采购员名称")
    private String cpersonname;

    /**
     * 销售订单号
     */
    @ApiModelProperty(value = "销售订单号")
    private String csocode;

    /**
     * 销售订单行号
     */
    @ApiModelProperty(value = "销售订单行号")
    private String irowno;

    /**
     * 采购订单号
     */
    @ApiModelProperty(value = "采购订单号")
    private String cpoid;

    /**
     * 采购子订单id
     */
    @ApiModelProperty(value = "采购子订单id")
    private String autoid;

    /**
     * 供应商简称
     */
    @ApiModelProperty(value = "供应商简称")
    private String cvenAbbname;

    /**
     * 供应商名称
     */
    @ApiModelProperty(value = "供应商名称")
    private String cvenName;

    /**
     * 存货编号
     */
    @ApiModelProperty(value = "存货编号")
    private String cinvcode;

    /**
     * 存货名称
     */
    @ApiModelProperty(value = "存货名称")
    private String cinvname;

    /**
     * 存货规格
     */
    @ApiModelProperty(value = "存货规格")
    private String cinvstd;

    /**
     * 主计量
     */
    @ApiModelProperty(value = "主计量")
    private String cinvmUnit;

    /**
     * 数量
     */
    @ApiModelProperty(value = "数量")
    private BigDecimal iquantity;

    /**
     * 付款币种名称
     */
    @ApiModelProperty(value = "付款币种名称")
    private String cexchName;

    /**
     * 原币含税单价
     */
    @ApiModelProperty(value = "原币含税单价")
    private BigDecimal itaxprice;

    /**
     * 原币价税合计
     */
    @ApiModelProperty(value = "原币价税合计")
    private BigDecimal isum;

    /**
     * 本币价税合计
     */
    @ApiModelProperty(value = "本币价税合计")
    private BigDecimal inatsum;

    /**
     * 累计入库数量
     */
    @ApiModelProperty(value = "累计入库数量")
    private BigDecimal ireceivedqty;
   /**
     * 入库数量
     */
    @ApiModelProperty(value = "入库数量")
    private BigDecimal receivedqty;

    /**
     * 预计到货日期
     */
    @ApiModelProperty(value = "预计到货日期")
    private LocalDateTime darriveDate;

    /**
     * 采购退货数量
     */
    @ApiModelProperty(value = "采购退货数量")
    private BigDecimal puarrIquantity;

    /**
     * 拒收数量
     */
    @ApiModelProperty(value = "拒收数量")
    private BigDecimal refuseIquantity;


    /**
     * 是否委外
     */
    @ApiModelProperty(value = "是否委外")
    private Integer outSourceFlag;


}
