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
import com.futuremap.erp.module.quotation.entity.Quotation;
import com.futuremap.erp.utils.EasyPoiExcelUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 * @title OfferExportTest
 * @description TODO
 * @date 2021/6/8 17:42
 */
public class OfferExportTest {

    public static void main(String[] args) {
        try {
//            String filePath = ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX+"quotation.xlsx").getPath();
//            String filePath = OfferExportTest.class.getResource("/quotation.xlsx").getPath();
            String filePath = "F:\\mingo\\workspace\\futuremap-erp\\src\\main\\resources\\quotation.xlsx";
//            File file= new File(filePath);
//            XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(file));
//
//            XSSFSheet sheet = wb.getSheetAt(1);

            ExcelReader reader = ExcelUtil.getReader(filePath);
            //获取第四行第三列单元格内容
            Object commissionPercent =reader.readCellValue(4,1);
            String s = String.valueOf(commissionPercent);


            System.out.println(commissionPercent);
            Object salemanNames =reader.readCellValue(2,1);
            System.out.println(salemanNames);
            Object saleOrder=reader.readCellValue(0,1);
            System.out.println(saleOrder);
            List<Quotation> quotations = EasyPoiExcelUtil.importExcel(filePath, 3, 1, Quotation.class);
            List<Quotation> resultList = new ArrayList<>();
            quotations.stream().forEach(e->{
                if(e.getCinvcode()!=null){
                    e.setCompanyCode("9999");
                    e.setCommissionPercent(new BigDecimal(String.valueOf(commissionPercent)));
                    e.setCsocode(String.valueOf(saleOrder));
                    e.setSalemanName(String.valueOf(salemanNames));
                    resultList.add(e);
                }
            });
//
            System.out.println(resultList.size());
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
