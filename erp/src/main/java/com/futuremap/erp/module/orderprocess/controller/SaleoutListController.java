package com.futuremap.erp.module.orderprocess.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.futuremap.erp.common.page.IPageWithExtra;
import com.futuremap.erp.common.page.PageWithExtra;
import com.futuremap.erp.module.orderprocess.entity.SaleoutList;
import com.futuremap.erp.module.orderprocess.entity.SaleoutQuery;
import com.futuremap.erp.module.orderprocess.service.impl.SaleoutListServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping("/orderprocess/saleout")
@Api(tags = {"获取销售出库数据（订单全过程）"})
public class SaleoutListController {
    @Autowired
    SaleoutListServiceImpl saleoutListService;

    @PostMapping("/listPage/{current}/{size}")
    @ApiOperation("获取销售出库数据(分页)")
    public IPageWithExtra<SaleoutList> getList(PageWithExtra<SaleoutList> page, @RequestBody SaleoutQuery saleoutQuery) {
        if(saleoutListService.sum(saleoutQuery).size() > 0) {
            page.setExtra((JSONObject) JSON.toJSON(saleoutListService.sum(saleoutQuery).get(0)));
        }
        return saleoutListService.findList(page,saleoutQuery);
    }
    @PostMapping("/list")
    @ApiOperation("获取销售出库数据(不分页)")
    public List<SaleoutList> getList(@RequestBody SaleoutQuery saleoutQuery) {
        return saleoutListService.findList(saleoutQuery);
    }
}
