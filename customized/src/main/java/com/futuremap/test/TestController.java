package com.futuremap.test;

import com.alibaba.fastjson.JSONObject;
import com.futuremap.annotation.CustomResponse;
import com.futuremap.datamanager.remoteDate.*;
import com.futuremap.resourceManager.service.ApiService;
import com.futuremap.test.json.AppAuth;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Api(value = "演示接口", tags = {"演示接口"})
@Slf4j
@RestController
@RequestMapping("/test")
public class TestController {
	@Autowired
	private  OrderService orderService;

	@Autowired
	private ApiService apiService;

	@Autowired
	private Code code;
	@Autowired
	private Customer customer;
	@Autowired
	private Delivery delivery;
	@Autowired
	private Department department;
	@Autowired
	private InventoryCode inventoryCode;
	@Autowired
	private Personnel personnel;
	@Autowired
	private SalesOrder salesOrder;
	@Autowired
	private SaleType saleType;
	@Autowired
	private Stock stock;

	@PostMapping("/login")
	@ApiOperation(value = "登录", notes = "登录")
	@CustomResponse
	public JSONObject login(@RequestBody Login logIn) {
		log.info("用户登录信息：" + JSONObject.toJSONString(logIn));
		JSONObject result = new JSONObject();
		result.put("code", 200);
		result.put("msg", "用户登录成功");
		JSONObject token = new JSONObject();
		token.put("data", token);
		return result;
	}

	@PostMapping("/appAuth")
	@ApiOperation(value = "应用授权", notes = "应用授权")
	@CustomResponse
	public JSONObject appAuth(@RequestBody AppAuth appAuth) {
		log.info("用户登录信息：" + JSONObject.toJSONString(appAuth));
		JSONObject result = new JSONObject();
		result.put("code", 200);
		result.put("msg", "用户登录成功");
		JSONObject token = new JSONObject();
		token.put("data", token);
		return result;
	}

	@PostMapping("/register")
	@ApiOperation(value = "注册", notes = "注册")
	@CustomResponse
	public JSONObject register(@RequestBody Register register) {
		log.info("用户注册信息：" + JSONObject.toJSONString(register));
		JSONObject result = new JSONObject();
		result.put("msg", "用户注册成功");
		return result;
	}

	@PostMapping("/orderList/{page}/{size}")
	@ApiOperation(value = "获取订单数据", notes = "获取订单数据")
	@CustomResponse
	public JSONObject getOrderList(@PathVariable Integer page, @PathVariable Integer size, @RequestBody OrderQuery orderQuery) {
		log.info("用户注册信息：" + JSONObject.toJSONString(orderQuery));
		JSONObject result = new JSONObject();
		result.put("msg", "用户注册成功");
		return orderService.getOrderList(page, size, orderQuery);
	}

	@PostMapping("/api")
	@ApiOperation(value = "api测试", notes = "api测试")
	@CustomResponse
	public JSONObject api(@RequestBody ApiDTO apiDTO) {
		return apiService.requestApiAdapter(apiDTO.getPath(), apiDTO.getParams());
	}

	@PostMapping("/getAndSaveDemo")
	@ApiOperation(value = "获取演示数据", notes = "获取演示数据")
	@CustomResponse
	public JSONObject getAndSaveDemo() {
		//code.getAndStore();
		//customer.getAndStore();
		delivery.getAndStore();
		//department.getAndStore();
		//inventoryCode.getAndStore();
		//personnel.getAndStore();
		//salesOrder.getAndStore();
		//saleType.getAndStore();
		//stock.getAndStore();
		JSONObject result = new JSONObject();
		result.put("result", "获取成功");
		return result;
	}
}
