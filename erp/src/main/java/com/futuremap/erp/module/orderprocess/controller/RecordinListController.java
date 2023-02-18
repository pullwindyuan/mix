package com.futuremap.erp.module.orderprocess.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.futuremap.erp.common.page.IPageWithExtra;
import com.futuremap.erp.common.page.PageWithExtra;
import com.futuremap.erp.module.orderprocess.entity.RecordinList;
import com.futuremap.erp.module.orderprocess.entity.RecordinQuery;
import com.futuremap.erp.module.orderprocess.service.impl.RecordinListServiceImpl;
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
@RequestMapping("/orderprocess/recordin")
@Api(tags = {"获取材料和产成品出库数据（订单全过程）"})
public class RecordinListController {
    @Autowired
    RecordinListServiceImpl recordinListService;

    @PostMapping("/listPage/{current}/{size}")
    @ApiOperation("获取产成品入库列表数据(分页)")
    public IPageWithExtra<RecordinList> getList(PageWithExtra<RecordinList> page, @RequestBody RecordinQuery recordinQuery) {
        if(recordinListService.sum(recordinQuery).size() > 0) {
            page.setExtra((JSONObject) JSON.toJSON(recordinListService.sum(recordinQuery).get(0)));
        }
        return recordinListService.findList(page,recordinQuery);
    }
    @PostMapping("/list")
    @ApiOperation("获取产成品入库列表数据(不分页)")
    public List<RecordinList> getList(@RequestBody RecordinQuery recordinQuery) {
        return recordinListService.findList(recordinQuery);
    }
}
