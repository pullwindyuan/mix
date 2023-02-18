package com.futuremap.datamanager.bean.BO;

import lombok.Data;

import java.io.Serializable;
import java.util.TreeMap;

/**
 * 数据预处理
 * @author Pullwind
 */
@Data
public class ProcessorRuleBO implements Serializable {
    /**
     * 数据IO规则描述，IO是针对数据库数据的标准操作
     * IO: {
     *                                 type: "FIELD",
     *                                 map: {
     *                                     "CALENDAR_DAY": [
     *                                         {
     *                                             IN: "11",
     *                                             OUT: "year_month_day"
     *                                         }
     *                                     ],
     *                                     SUM: [
     *                                         {
     *                                             IN: "9",
     *                                             OUT: "9"
     *                                         },
     *                                         {
     *                                             IN: "10",
     *                                             OUT: "10"
     *                                         }
     *                                     ],
     *                                     AVG: [
     *                                         {
     *                                             IN: "9",
     *                                             OUT: "9_avg"
     *                                         },
     *                                         {
     *                                             IN: "10",
     *                                             OUT: "10_avg"
     *                                         }
     *                                     ],
     *                                     COUNT: [
     *                                         {
     *                                             IN: "*",
     *                                             OUT: "count"
     *                                         }
     *                                     ]
     *                                 }
     *                             }
     *                         }
     */
    DataIoBO dataBaseIO;
    /**
     *计算型规则，用于描述多个字段参与的加减乘除
     */
    TreeMap<Integer, FormulaBO> formulaMap;
}
