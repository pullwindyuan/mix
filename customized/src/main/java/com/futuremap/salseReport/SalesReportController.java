package com.futuremap.salseReport;


import com.alibaba.fastjson.JSONObject;
import com.futuremap.annotation.CustomResponse;
import com.futuremap.base.bean.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Api(value = "销售管理", tags = {"销售管理"})
@RestController
@RequestMapping("/salesReport")
public class SalesReportController {
    @Autowired
    private SalesReportService salesReportService;

    @PostMapping("/revenueStatistics")
    @ApiOperation(value = "获取销售收入统计", notes = "获取销售收入统计:2021-12-09 00:00:00有数据")
    @CustomResponse
    public List<JSONObject> getInfo(@RequestBody SalesReportDTO salesReportDTO) {
        return salesReportService.getRevenueStatisticsInfoList(salesReportDTO);
    }

    @PostMapping("/delivery")
    @ApiOperation(value = "获取发货信息列表", notes = "获取发货信息列表:2021-12-09 00:00:00有数据")
    @CustomResponse
    public Page getDeliveryInfoList(@RequestBody SalesReportPageDTO salesReportPageDTO) {
        return salesReportService.getDeliveryInfoList(salesReportPageDTO);
    }

    @PostMapping("/overDueReceivable")
    @ApiOperation(value = "获取目前已经逾期的应收款信息列表", notes = "获取目前已经逾期的应收款信息列表:2021-12-09 00:00:00有数据")
    @CustomResponse
    public Page getOverdueReceivableList(@RequestBody SalesReportPageDTO salesReportPageDTO) {
        return salesReportService.getOverdueReceivableList(salesReportPageDTO);
    }

    @PostMapping("/dueReceivables")
    @ApiOperation(value = "获取当月到期的应收款信息列表", notes = "获取当月到期的应收款信息列表:2021-12-09 00:00:00有数据")
    @CustomResponse
    public Page getDueReceivablesList(@RequestBody SalesReportPageDTO salesReportPageDTO) {
        return salesReportService.getDueReceivablesList(salesReportPageDTO);
    }

    @PostMapping("/unexecutedContract")
    @ApiOperation(value = "获取截止当月未执行的合同信息列表", notes = "获取截止当月未执行的合同信息列表:2021-12-09 00:00:00有数据")
    @CustomResponse
    public Page getUnexecutedContractReportList(@RequestBody SalesReportPageDTO salesReportPageDTO) {
        return salesReportService.getUnexecutedContractReportList(salesReportPageDTO);
    }

    @PostMapping("/newContract")
    @ApiOperation(value = "获取截止当月新增合同信息列表", notes = "获取截止当月新增合同信息列表:2021-12-09 00:00:00有数据")
    @CustomResponse
    public Page getNewContractReportList(@RequestBody SalesReportPageDTO salesReportPageDTO) {
        return salesReportService.getNewContractReportList(salesReportPageDTO);
    }

    @PostMapping("/budgeTrack")
    @ApiOperation(value = "获取截止当月预算完成情况跟踪信息", notes = "获取截止当月预算完成情况跟踪信息:2021-12-09 00:00:00有数据")
    @CustomResponse
    public JSONObject getBudgeTrackReport(@RequestBody SalesReportDTO salesReportDTO) {
        return salesReportService.getBudgeTrackReport(salesReportDTO);
    }
}
