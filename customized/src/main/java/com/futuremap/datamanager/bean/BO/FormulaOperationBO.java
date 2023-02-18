package com.futuremap.datamanager.bean.BO;

import lombok.Data;

import java.io.Serializable;

/**
 * 运算器
 * @author Pullwind
 */
@Data
public class FormulaOperationBO implements Serializable {
    /**
     * 计算符号
     */
    String operator;
    /**
     * 精度
     */
    FormulaScaleBO scale;
}
