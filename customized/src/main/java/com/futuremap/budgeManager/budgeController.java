package com.futuremap.budgeManager;

import com.alibaba.fastjson.JSONObject;
import com.futuremap.annotation.CustomResponse;
import com.futuremap.resourceManager.service.MenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Api(value = "预算管理", tags = {"预算管理"})
@RestController
@RequestMapping("/budge")
public class budgeController {
	@Autowired
	private BudgeService budgeService;

	@PostMapping("/list")
	@ApiOperation(value = "获取年度预算信息列表", notes = "获取年度预算信息列表")
	@CustomResponse
	public List<JSONObject> getList(@RequestBody BudgeQueryDTO budgeQueryDTO) {
		return budgeService.getList(budgeQueryDTO);
	}

	@PostMapping("/update")
	@ApiOperation(value = "更新预算", notes = "更新预算")
	@CustomResponse
	public void update(@RequestBody List<BudgeUpdateDTO> budgeUpdateDTOList) {
		budgeService.update(budgeUpdateDTOList);
	}

	@PostMapping("/config")
	@ApiOperation(value = "获取预算查询相关的配置信息", notes = "获取预算查询相关的配置信息")
	@CustomResponse
	public JSONObject getConfig() {
		return budgeService.getConfig();
	}
}
