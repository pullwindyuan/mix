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
package com.futuremap.erp.service.job;

import com.futuremap.erp.BaseTest;
import com.futuremap.erp.module.job.OperationDataExportJob;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Administrator
 * @title OperationDataExportJobTest
 * @description TODO
 * @date 2021/6/3 8:47
 */
public class OperationDataExportJobTest extends BaseTest {
    @Autowired
    OperationDataExportJob operationDataExportJob;
    @Test
    public void testOperationDataExportJob(){
            operationDataExportJob.startOperationDataExportJob();
    }
    @Test
    public void testHistoryOperationDataExportJob(){
            operationDataExportJob.startHistoryOperationDataExportJob();
    }
}
