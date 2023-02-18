package com.futuremap.erp.module.operation.controller;


import com.futuremap.erp.common.exception.FuturemapBaseException;
import com.futuremap.erp.common.page.IPageWithExtra;
import com.futuremap.erp.common.page.PageWithExtra;
import com.futuremap.erp.module.auth.service.DataFilterByRolePermission;
import com.futuremap.erp.module.job.OperationDataExportJob;
import com.futuremap.erp.module.operation.entity.OperatingStatement;
import com.futuremap.erp.module.operation.entity.OperatingStatementQuery;
import com.futuremap.erp.module.operation.entity.OperatingStatementSubQuery;
import com.futuremap.erp.module.operation.service.impl.OperatingStatementServiceImpl;
import com.futuremap.erp.module.orderprocess.dto.PurchBillDto;
import com.futuremap.erp.module.orderprocess.entity.PurchBill;
import com.futuremap.erp.module.orderprocess.entity.PurchBillQuery;
import com.futuremap.erp.utils.EasyPoiExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author futuremap
 * @since 2021-06-01
 */
@RestController
@RequestMapping("/operation/operatingStatement")
@Slf4j
@Api(tags = {"经营表数据（订单全过程）"})
public class OperatingStatementController  extends DataFilterByRolePermission {
    @Autowired
    OperatingStatementServiceImpl operatingStatementService;
    @Autowired
    OperationDataExportJob operationDataExportJob;
    /**
     * 对经营状况表operating_statement进行查询即可
     * @param operatingStatementQuery
     * @return
     */
    @PostMapping("/list")
    @ApiOperation("获取经营表数据(不分页)")
    public Map<String, List<OperatingStatement>> getOperatingStatementlist(@RequestBody OperatingStatementQuery operatingStatementQuery) {
        return operatingStatementService.findList(operatingStatementQuery);
    }

    @PostMapping("/subList")
    @ApiOperation("获取经营表所有子公司数据(不分页)")
    public Map<String, List<OperatingStatement>> getSubCompanyOperatingStatementList(@RequestBody OperatingStatementSubQuery operatingStatementQuery) {
        return operatingStatementService.findListByNotInCompanyCode(operatingStatementQuery);
    }

    @PostMapping("/subListByClassCode")
    @ApiOperation("通过classCode字段值获取经营表所有子公司数据(不分页)")
    public Map<String, List<OperatingStatement>> getSubCompanyOperatingStatementListByClassCode(@RequestBody OperatingStatementQuery operatingStatementQuery) {
        return operatingStatementService.findListByNotInCompanyCodeAndClassCode(operatingStatementQuery);
    }

    @PostMapping("/download")
    @ApiOperation("收付款对应表下载")
    public void downloadFile(HttpServletResponse response,@RequestBody OperatingStatementQuery operatingStatementQuery) throws IOException {
        Map<String, List<OperatingStatement>> operatingStatementServiceList = operatingStatementService.findList(operatingStatementQuery);
        //TODO 转换成List
        EasyPoiExcelUtil.exportExcel(null, "收付款对应表", "收付款对应表", PurchBill.class, "PurchBill", true, response);
    }

    @PostMapping("/update")
    @ApiOperation("更新经营表数据")
    public void updateOperatingStatement(@RequestBody List<OperatingStatement> operatingStatementList, HttpServletRequest request) {
        if(checkWritePermission(request) == false) {
            log.error("更新销售订单数据失败：您没有操作的权限");
            throw new FuturemapBaseException("更新销售订单数据失败：您没有操作的权限");
        }else {
            operatingStatementService.updateBatchById(operatingStatementList);
        }
    }
}





