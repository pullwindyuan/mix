package com.futuremap.datamanager.bean.BO;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 运算器
 * @author Pullwind
 */
@Data
public class FormulaInputBO implements Serializable {
    /**
     * 输入数据的存储类型
     */
    String type;
    /**
     * 第一操作数
     */
    BigDecimal first;
    /**
     * 第二操作数
     */
    BigDecimal second;
}
