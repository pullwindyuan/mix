package com.futuremap.erp.module.saleorder.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.futuremap.erp.common.exception.FuturemapBaseException;
import com.futuremap.erp.common.page.IPageWithExtra;
import com.futuremap.erp.common.page.PageWithExtra;
import com.futuremap.erp.module.auth.service.DataFilterByRolePermission;
import com.futuremap.erp.module.saleorder.dto.SaleOrderDto;
import com.futuremap.erp.module.saleorder.entity.SaleOrder;
import com.futuremap.erp.module.saleorder.entity.SaleOrderQuery;
import com.futuremap.erp.module.saleorder.service.impl.SaleOrderServiceImpl;
import com.futuremap.erp.utils.EasyPoiExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author futuremap
 * @since 2021-05-28
 */
@RestController
@RequestMapping("/saleorder")
@Slf4j
@Api(tags = {"获取销售订单列表数据（订单全过程）"})
public class SaleOrderController extends DataFilterByRolePermission {
    @Autowired
    SaleOrderServiceImpl saleOrderService;
    /**
     * 对sale_order表查询即可
     * @param page
     * @param saleOrderQuery
     * @return
     */
    @PostMapping("/listPage/{current}/{size}")
    @ApiOperation("获取销售订单全过程数据(分页)")
    public IPageWithExtra<SaleOrderDto> getSaleOrderlistPage(PageWithExtra<SaleOrderDto> page, @RequestBody SaleOrderQuery saleOrderQuery) {
        initRoleUserColumnPermission("sale_order_process");
        try {
            rowFilter(saleOrderQuery);
            List<SaleOrder> saleOrderSumList = saleOrderService.sum(saleOrderQuery);
            if(saleOrderSumList.size() > 0) {
                page.setExtra((JSONObject) JSON.toJSON(saleOrderSumList.get(0)));
            }
            //对数据的警示信息进行处理
            IPageWithExtra<SaleOrderDto> pageWithExtra = saleOrderService.findListFromView(page,saleOrderQuery);
            pageWithExtra.getRecords().forEach(e->{
                e.initWarningInfo();
                try {
                    columnFilter(e);
                } catch (IllegalAccessException illegalAccessException) {
                    illegalAccessException.printStackTrace();
                }
            });
            return pageWithExtra;//saleOrderService.findList(page,saleOrderQuery);
        } catch (Exception e) {
            log.error("获取销售订单全过程数据",e);
            throw new FuturemapBaseException("获取销售订单数据失败");
        }
    }
    @PostMapping("/update")
    @ApiOperation("更新销售订单")
    public void update(@RequestBody List<SaleOrder> saleOrderList, HttpServletRequest request) {
        if(checkWritePermission(request) == false) {
            log.error("您没有操作权限");
            throw new FuturemapBaseException("您没有操作权限");
        }else {
            try {
                saleOrderService.updateBatchById(saleOrderList);
            } catch (Exception e) {
                log.error("更新销售订单数据失败", e);
                throw new FuturemapBaseException("更新销售订单数据失败");
            }
        }
    }

    @PostMapping("/download/{current}/{size}")
    @ApiOperation("销售订单表下载")
    public void downloadFile(HttpServletResponse response, PageWithExtra<SaleOrderDto> page, @RequestBody SaleOrderQuery saleOrderQuery) throws IOException {
        initRoleUserColumnPermission("sale_order_process");
        rowFilter(saleOrderQuery);
        page.setSize(Integer.MAX_VALUE);
        IPageWithExtra<SaleOrderDto> pageWithExtra = saleOrderService.findListFromView(page, saleOrderQuery);
        List<SaleOrderDto> records = pageWithExtra.getRecords();
        records.forEach(e->{
            try {
                columnFilter(e);
            } catch (IllegalAccessException illegalAccessException) {
                illegalAccessException.printStackTrace();
            }
        });
        EasyPoiExcelUtil.exportExcel(records, "销售订单表", "销售订单表明细", SaleOrderDto.class, "SaleOrder", true, response);
    }

    @GetMapping("/download")
    @ApiOperation("销售订单下载excel文件")
    public void downloadFile(HttpServletResponse response) throws FileNotFoundException {
        String fileName = "SaleOrderTemplet.xlsx";// 文件名
        if (fileName != null) {
            response.setContentType("application/force-download");// 设置强制下载不打开
            response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);// 设置文件名
            byte[] buffer = new byte[1024];
            InputStream fis = null;
            BufferedInputStream bis = null;
            try {
                fis = this.getClass().getResourceAsStream("/excel/SaleOrderTemplet.xlsx");
                bis = new BufferedInputStream(fis);
                OutputStream os = response.getOutputStream();
                int i = bis.read(buffer);
                while (i != -1) {
                    os.write(buffer, 0, i);
                    i = bis.read(buffer);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (bis != null) {
                    try {
                        bis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
