package com.futuremap.erp.module.orderprocess.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.futuremap.erp.common.exception.FuturemapBaseException;
import com.futuremap.erp.common.page.IPageWithExtra;
import com.futuremap.erp.common.page.PageWithExtra;
import com.futuremap.erp.module.orderprocess.entity.PurchOrder;
import com.futuremap.erp.module.orderprocess.entity.PurchOrderQuery;
import com.futuremap.erp.module.orderprocess.service.impl.PurchOrderServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.MyBatisSystemException;
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
@RequestMapping("/orderprocess/purchOrder")
@Slf4j
@Api(tags = {"获取采购订单数据（订单全过程）"})
public class PurchOrderController {
    @Autowired
    PurchOrderServiceImpl purchOrderService;

    @PostMapping("/listPage/{current}/{size}")
    @ApiOperation("获取采购订单数据(分页)")
    public IPageWithExtra<PurchOrder> getPurchOrderlistPage(PageWithExtra<PurchOrder> page, @RequestBody PurchOrderQuery purchOrderQuery) {
        try {
            if(purchOrderService.sum(purchOrderQuery).size() > 0) {
                page.setExtra((JSONObject) JSON.toJSON(purchOrderService.sum(purchOrderQuery).get(0)));
            }
            return purchOrderService.findList(page,purchOrderQuery);
        } catch (MyBatisSystemException e) {
            throw new FuturemapBaseException("您没有采购订单数据权限");
        } catch (Exception e) {
            log.error("获取采购订单数据失败",e);
            throw new FuturemapBaseException("获取采购订单数据失败");
        }
    }
    @PostMapping("/list")
    @ApiOperation("获取采购订单数据(不分页)")
    public List<PurchOrder> getPurchOrderlist(@RequestBody PurchOrderQuery purchOrderQuery) {
        try {
            return purchOrderService.findList(purchOrderQuery);
        } catch (Exception e) {
            log.error("获取采购订单数据失败",e);
            throw new FuturemapBaseException("获取采购订单数据失败");
        }
    }
}
