package com.futuremap.erp.module.orderprocess.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.futuremap.erp.common.page.IPageWithExtra;
import com.futuremap.erp.common.page.PageWithExtra;
import com.futuremap.erp.module.auth.service.DataFilterByRolePermission;
import com.futuremap.erp.module.orderprocess.dto.PurchBillDto;
import com.futuremap.erp.module.orderprocess.entity.PurchBillQuery;
import com.futuremap.erp.module.orderprocess.service.impl.PurchBillServiceImpl;
import com.futuremap.erp.utils.EasyPoiExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author futuremap
 * @since 2021-07-14
 */
@RestController
@RequestMapping("/orderprocess/purchSaleBill")
@Api(tags = {"获取收付款对应表"})
public class PurchBillController  extends DataFilterByRolePermission {
    @Autowired
    PurchBillServiceImpl purchBillService;
    /**
     * 采购账单表purch_bill（对应付款项）和销售账单sale_bill（对应收款项）连表查询
     * @param page
     * @param purchBillQuery
     * @return
     */
    @PostMapping("/listPage/{current}/{size}")
    @ApiOperation("获取收付款对应表数据(分页)")
    public IPageWithExtra<PurchBillDto> getCloseOrderlistPage(PageWithExtra<PurchBillDto> page, @RequestBody PurchBillQuery purchBillQuery) {
        initRoleUserColumnPermission("purch_sale_bill_mapper");
        rowFilter(purchBillQuery);
        //获取汇总数据
        List<PurchBillDto> sumList = purchBillService.sum(purchBillQuery);

        if(sumList.size() > 0 && sumList.get(0) != null) {
            PurchBillDto purchBillSumSaleBill = purchBillService.sumSaleBill(purchBillQuery).get(0);
            PurchBillDto purchBillSum = sumList.get(0);
            if(purchBillSumSaleBill != null) {
                purchBillSum.setConlectionMoney(purchBillSumSaleBill.getConlectionMoney());
                purchBillSum.setConlectionNatMoney(purchBillSumSaleBill.getConlectionNatMoney());
                purchBillSum.setSaleBillNatMoney(purchBillSumSaleBill.getSaleBillNatMoney());
                purchBillSum.setBillMoney(purchBillSumSaleBill.getBillMoney());
                purchBillSum.setNoConlectionMoney(purchBillSumSaleBill.getNoConlectionMoney());
            }
            page.setExtra((JSONObject) JSON.toJSON(purchBillSum));
        }

        //对数据的警示信息进行处理
        IPageWithExtra<PurchBillDto> pageWithExtra = purchBillService.findList(page,purchBillQuery);
        pageWithExtra.getRecords().forEach(e->{
            e.initWarningInfo();
            try {
                columnFilter( e);
            } catch (IllegalAccessException illegalAccessException) {
                illegalAccessException.printStackTrace();
            }
        });
        return pageWithExtra;
    }

    @PostMapping("/download/{current}/{size}")
    @ApiOperation("收付款对应表下载")
    public void downloadFile(HttpServletResponse response, PageWithExtra<PurchBillDto> page, @RequestBody PurchBillQuery purchBillQuery) throws IOException {
        initRoleUserColumnPermission("purch_sale_bill_mapper");
            rowFilter(purchBillQuery);
        page.setSize(Integer.MAX_VALUE);
        IPageWithExtra<PurchBillDto> pageWithExtra = purchBillService.findListFromMapper(page,purchBillQuery);
        List<PurchBillDto> records = pageWithExtra.getRecords();
        records.forEach(e->{
            try {
                columnFilter(e);
            } catch (IllegalAccessException illegalAccessException) {
                illegalAccessException.printStackTrace();
            }
        });

        EasyPoiExcelUtil.exportExcel(records, "收付款对应表", "收付款对应表", PurchBillDto.class, "PurchBill", true, response);
    }
}
