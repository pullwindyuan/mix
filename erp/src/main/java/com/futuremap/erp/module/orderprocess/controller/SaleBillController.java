package com.futuremap.erp.module.orderprocess.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.futuremap.erp.common.page.IPageWithExtra;
import com.futuremap.erp.common.page.PageWithExtra;
import com.futuremap.erp.module.orderprocess.entity.SaleBill;
import com.futuremap.erp.module.orderprocess.entity.SaleBillQuery;
import com.futuremap.erp.module.orderprocess.service.impl.SaleBillServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author futuremap
 * @since 2021-07-13
 */
@RestController
@RequestMapping("/orderprocess/saleBill")
@Api(tags = {"获取销售回款数据（订单全过程）"})
public class SaleBillController {
    @Autowired
    SaleBillServiceImpl saleBillService;

    @PostMapping("/listPage/{current}/{size}")
    @ApiOperation("获取销售回款数据(分页)")
    public IPageWithExtra<SaleBill> getCloseOrderlistPage(PageWithExtra<SaleBill> page, @RequestBody SaleBillQuery saleBillQuery) {
        if(saleBillService.sum(saleBillQuery).size() > 0) {
            page.setExtra((JSONObject) JSON.toJSON(saleBillService.sum(saleBillQuery).get(0)));
        }
        return saleBillService.findList(page,saleBillQuery);
    }
}
