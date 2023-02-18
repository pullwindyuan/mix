package com.futuremap.erp.module.orderCost.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * @author rubio@futuremap.com.cn
 * @date 2021/8/9 10:16
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Bom {
    private String bomId;
    private String invCode;//物料
    private BigDecimal baseQtyN;//数量

}
