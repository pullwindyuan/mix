package com.futuremap.erp.module.orderprocess.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.futuremap.erp.common.page.IPageWithExtra;
import com.futuremap.erp.common.page.PageWithExtra;
import com.futuremap.erp.module.orderprocess.entity.CloseOrder;
import com.futuremap.erp.module.orderprocess.entity.CloseOrderQuery;
import com.futuremap.erp.module.orderprocess.service.impl.CloseListServiceImpl;
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
@RequestMapping("/orderprocess/closeList")
@Api(tags = {"获取供应商与销售客户映射数据"})
public class CloseListController {
    @Autowired
    CloseListServiceImpl closeListService;

    @PostMapping("/listPage/{current}/{size}")
    @ApiOperation("获取供应商与销售客户映射数据(分页)")
    public IPageWithExtra<CloseOrder> getCloseOrderlistPage(PageWithExtra<CloseOrder> page, @RequestBody CloseOrderQuery closeOrderQuery) {
        if(closeListService.sum(closeOrderQuery).size() > 0) {
            page.setExtra((JSONObject) JSON.toJSON(closeListService.sum(closeOrderQuery).get(0)));
        }
        return closeListService.findList(page,closeOrderQuery);
    }
    @PostMapping("/dvouchlistPage/{current}/{size}")
    @ApiOperation("通过销售订单好获取供应商与销售客户映射数据(分页)")
    public IPageWithExtra<CloseOrder> getCloseOrderByCsocodelistPage(PageWithExtra<CloseOrder> page, @RequestBody CloseOrderQuery closeOrderQuery) {
        if(closeListService.sum(closeOrderQuery).size() > 0) {
            page.setExtra((JSONObject) JSON.toJSON(closeListService.sumByCsocode(closeOrderQuery).get(0)));
        }
        return closeListService.findByCsocode(page,closeOrderQuery);
    }
    @PostMapping("/list")
    @ApiOperation("获取供应商与销售客户映射数据(不分页)")
    public List<CloseOrder> getCloseOrderlist(@RequestBody CloseOrderQuery closeOrderQuery) {
        return closeListService.findList(closeOrderQuery);
    }

}
