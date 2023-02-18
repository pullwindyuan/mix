/*
 * Copyright © 2017-2021 http://www.futuremap.com.cn/ All rights reserved.
 *
 * Statement: This document's code after sufficiently has not permitted does not have
 * any way dissemination and the change, once discovered violates the stipulation, will
 * receive the criminal sanction.
 * Address: Building A, block 1F,  Tian'an Yungu Industrial Park phase I,
 *          Xuegang Road, Bantian street, Longgang District, Shenzhen
 * Tel: 0755-22674916
 */
package com.futuremap.erp.service.offer;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.futuremap.erp.module.orderCost.entity.SwitchOrder;
import com.futuremap.erp.module.quotation.entity.Quotation;
import com.futuremap.erp.utils.EasyPoiExcelUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class SwitchOrderTest {

    public static void main(String[] args) {
        try {
//            String filePath = ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX+"quotation.xlsx").getPath();
//            String filePath = OfferExportTest.class.getResource("/quotation.xlsx").getPath();
            String filePath = "E:\\test\\1.xlsx";
//            File file= new File(filePath);
//            XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(file));
//
//            XSSFSheet sheet = wb.getSheetAt(1);

            ExcelReader reader = ExcelUtil.getReader(filePath);
            int columnCount = reader.getColumnCount();
            int rowCount = reader.getRowCount();
//            System.out.println(columnCount);
//            System.out.println(rowCount);

            for (int i = 1; i < rowCount; i++) {
                for (int j = 0; j < columnCount; j++) {
                    Object val = reader.readCellValue(j, i);
                    System.out.println(val);
                }
                System.out.println();
                System.out.println();
            }

            List<SwitchOrder> switchOrders = EasyPoiExcelUtil.importExcel(filePath, 0, 1, SwitchOrder.class);
            System.out.println(switchOrders);



        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
