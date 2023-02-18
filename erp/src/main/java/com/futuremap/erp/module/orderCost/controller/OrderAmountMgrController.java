package com.futuremap.erp.module.orderCost.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.futuremap.erp.common.exception.FuturemapBaseException;
import com.futuremap.erp.module.auth.service.DataFilterByRolePermission;
import com.futuremap.erp.module.orderCost.entity.OrderAmountMgr;
import com.futuremap.erp.module.orderCost.entity.OrderAmountMgrQuery;
import com.futuremap.erp.module.orderCost.service.impl.OrderAmountMgrServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@RestController
@Slf4j
@RequestMapping("/orderAmountMgr")
@Api(tags = {"客户账期控制器"})
public class OrderAmountMgrController extends DataFilterByRolePermission {

    @Autowired
    OrderAmountMgrServiceImpl orderAmountMgrService;

    /**
     * 导入数据
     *
     * @param file
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/fileUpload", method = RequestMethod.POST)
    @ApiOperation("客户账期设置上传excel数据")
    public void importExcel(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws IOException {
        if(checkWritePermission(request) == false) {
            log.error("您没有操作权限");
            throw new FuturemapBaseException("您没有操作权限");
        }
        InputStream fileInputStream = file.getInputStream();
        orderAmountMgrService.buildOrderAmountMgrList(fileInputStream);

    }


    @PostMapping("/listPage/{current}/{size}")
    @ApiOperation("客户账期设置上传数据(分页)")
    public IPage<OrderAmountMgr> getOrderAmountMgrlistPage(Page<OrderAmountMgr> page, @RequestBody OrderAmountMgrQuery switchOrderQuery) {
        return orderAmountMgrService.findList(page, switchOrderQuery);
    }


    @GetMapping("/download")
    @ApiOperation("客户账期设置下载excel模版")
    public void downloadFile(HttpServletResponse response) throws FileNotFoundException {
        String fileName = "orderAmountMgr.xlsx";// 文件名
        if (fileName != null) {
            response.setContentType("application/force-download");// 设置强制下载不打开
            response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);// 设置文件名
            byte[] buffer = new byte[1024];
            InputStream fis = null;
            BufferedInputStream bis = null;
            try {
                fis = this.getClass().getResourceAsStream("/usr/local/proj/erp/downloadFile/orderAmountMgr.xlsx");
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
