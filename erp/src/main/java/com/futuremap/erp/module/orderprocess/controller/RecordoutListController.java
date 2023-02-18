package com.futuremap.erp.module.orderprocess.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.futuremap.erp.common.page.IPageWithExtra;
import com.futuremap.erp.common.page.PageWithExtra;
import com.futuremap.erp.module.orderprocess.entity.RecordoutList;
import com.futuremap.erp.module.orderprocess.entity.RecordoutQuery;
import com.futuremap.erp.module.orderprocess.service.impl.RecordoutListServiceImpl;
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
@RequestMapping("/orderprocess/recordout")
@Api(tags = {"获取材料出库数据（订单全过程）"})
public class RecordoutListController {

    @Autowired
    RecordoutListServiceImpl recordoutListService;

    @PostMapping("/listPage/{current}/{size}")
    @ApiOperation("获取材料出库列表数据(分页)")
    public IPageWithExtra<RecordoutList> getList(PageWithExtra<RecordoutList> page, @RequestBody RecordoutQuery recordoutQuery) {
        if(recordoutListService.sum(recordoutQuery).size() > 0) {
            page.setExtra((JSONObject) JSON.toJSON(recordoutListService.sum(recordoutQuery).get(0)));
        }
        return recordoutListService.findList(page,recordoutQuery);
    }
    @PostMapping("/list")
    @ApiOperation("获取材料出库列表数据(不分页)")
    public List<RecordoutList> getList(@RequestBody RecordoutQuery recordoutQuery) {
        return recordoutListService.findList(recordoutQuery);
    }
}
