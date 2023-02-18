package com.futuremap.datamanager.bean.BO;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 运算器
 * @author Pullwind
 */
@Data
public class FormulaEqualsBO implements Serializable {
    /**
     * 输出数据的存储类型
     */
    String type;
    /**
     * 计算结果
     */
    BigDecimal result;
    /**
     * 输出名称
     */
    String outName;
}
