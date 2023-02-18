package com.futuremap.datamanager.bean.BO;

import lombok.Data;

import java.io.Serializable;

/**
 * 运算器
 * @author Pullwind
 */
@Data
public class FormulaScaleBO implements Serializable {
    /**
     * 精确到小数位数
     */
    Integer accurateToDecimal;
    /**
     * 收舍
     */
    String round;
}
