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
public class Cven {
    private String cvencode;//供应商编码
    private String cvenname;//供应商名称

}
