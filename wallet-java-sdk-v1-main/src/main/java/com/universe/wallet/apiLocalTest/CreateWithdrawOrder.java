package com.universe.wallet.apiLocalTest;

import com.alibaba.fastjson.JSON;
import com.universe.wallet.DTO.response.CreateWithdrawOrderResponse;
import com.universe.wallet.DTO.request.CreateWithdrawOrderRequest;
import com.universe.wallet.openApi.WalletService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 本地接口文档地址：http://localhost:8000/doc.html#/home
 * 测试对接创建提现订单详情的接口
 * @author Pullwind
 */
@Slf4j
@RestController
@RequestMapping("/api/openApi")
@Api(tags = "通过本地接口测试钱包开放接口调用")
public class CreateWithdrawOrder {

	@Autowired
	private WalletService walletService;

	/**
     * 创建提现订单的业务参数结构，铜鼓接口测试的时候使用这样的json作为body
	 */
	public static final String createWithdrawOrder = "{\n" +
			"\"merchantOrderNo\":\"14235437865985898\"," +
			"\"paymentMethod\":1," +
			"\"bankAccountName\":\"张三\"," +
			"\"bankAccount\":\"3234456452342346\"," +
			"\"bankName\":\"建设银行\"," +
			"\"bankBranch\":\"北京分行\"," +
			"\"amount\":\"123.45\"}";


	/**
	 * 测试的时候请求body使用如下格式
	 * {
	 *   "merchantOrderNo": "1645268345945",
	 *   "amount": "166",
	 *   "paymentMethod": 1,
	 *   "bankAccountName": "members66",
	 *   "bankAccount": "4000838450999950",
	 *   "bankName": "中国建设银行",
	 *   "bankBranch": "北京分行",
	 * }
	 * @param createWithdrawOrderRequest 创建提现订单需要的业务参数数据结构封装
	 * @return
	 */
	@PostMapping("/withdraw/create")
	@ApiOperation("提现订单：《创建》接口调用")
	@ResponseBody
	public String testWithdrawCreate(@RequestBody CreateWithdrawOrderRequest createWithdrawOrderRequest) {
		log.info("start to tset testWithdrawCreate");
		CreateWithdrawOrderResponse result = walletService.createWithdrawOrder(createWithdrawOrderRequest);
		return JSON.toJSONString(result);
	}
}
