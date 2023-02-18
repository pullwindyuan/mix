package com.universe.wallet.apiLocalTest;

import com.alibaba.fastjson.JSON;
import com.universe.wallet.DTO.response.CreateDepositOrderResponse;
import com.universe.wallet.DTO.request.CreateDepositOrderRequest;
import com.universe.wallet.openApi.WalletService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 本地接口文档地址：http://localhost:8000/doc.html#/home
 * 测试对接创建充值订单详情的接口
 * @author Pullwind
 */
@Slf4j
@RestController
@RequestMapping("/api/openApi")
@Api(tags = "通过本地接口测试钱包开放接口调用")
public class CreateDepositOrder {

	@Autowired
	private WalletService walletService;

	/**
	 * 创建提充值单的业务参数结构，铜鼓接口测试的时候使用这样的json作为body
	 */
	public static final String createDepositOrder = "{\n" +
			"  \"merchantOrderNo\": \"order_123456\",\n" +
			"  \"amount\": \"100\",\n" +
			"  \"paymentMethod\": 1,\n" +
			"}";

	/**
	 * 测试的时候请求body使用如下格式
	 * {
	 *   "merchantOrderNo": "order_123456",
	 *   "amount": "100",
	 *   "paymentMethod": 1,
	 * }
	 * @param createDepositOrderRequest 创建充值订单数据结构
	 * @return
	 */
	@PostMapping("/deposit/create")
	@ApiOperation("充值订单：《创建》接口调用")
	@ResponseBody
	public String testDepositCreate(@RequestBody CreateDepositOrderRequest createDepositOrderRequest) {
		CreateDepositOrderResponse result = walletService.createDepositOrder(createDepositOrderRequest);
		return JSON.toJSONString(result);
	}
}
