package com.futuremap.salseReport.salesDaily;

import com.alibaba.fastjson.JSONObject;
import com.futuremap.annotation.CustomResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Api(value = "销售管理", tags = {"销售管理"})
@RestController
@RequestMapping("/salesDaily")
public class salesDailyController {
	@Autowired
	private SalesDailyService salesDailyService;

	@PostMapping("/info")
	@ApiOperation(value = "获取销售日报数据", notes = "获取销售日报数据")
	@CustomResponse
	public JSONObject getInfo(@RequestBody SalesDailyDTO salesDailyDTO) {
		return salesDailyService.getInfo(salesDailyDTO);
	}
}
