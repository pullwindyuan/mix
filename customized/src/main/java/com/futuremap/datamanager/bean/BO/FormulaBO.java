package com.futuremap.datamanager.bean.BO;

import lombok.Data;

import java.io.Serializable;

/**
 * 运算器
 * @author Pullwind
 */
@Data
public class FormulaBO implements Serializable {
    /**
     * 输入操作数
     */
    FormulaInputBO input;
    /**
     *计算操作
     */
    FormulaOperationBO operation;

    /**
     * 等于的结果
     */
    FormulaEqualsBO equals;
}
