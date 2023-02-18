package com.universe.wallet.apiLocalTest;

import com.alibaba.fastjson.JSON;
import com.universe.wallet.DTO.request.QueryDepositOrderRequest;
import com.universe.wallet.DTO.response.QueryDepositOrderResponse;
import com.universe.wallet.openApi.WalletService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 本地接口文档地址：http://localhost:8000/doc.html#/home
 * 测试对接查询充值订单详情的接口
 * @author Pullwind
 */
@Slf4j
@RestController
@RequestMapping("/api/openApi")
@Api(tags = "通过本地接口测试钱包开放接口调用")
public class QueryDepositOrder {

	@Autowired
	private WalletService walletService;

	/**
	 * 查询充值订单的业务参数结构，铜鼓接口测试的时候使用这样的json作为body
	 */
	public static final String queryDepositrOrder = "{\n" +
			"  \"merchantOrderNo\": \"order_123456\",\n" +
			"}";

	/**
	 * 测试的时候请求body使用如下格式
	 * {
	 *   "merchantOrderNo": "9287349k3adfsdjflsdj",
	 * }
	 * @param queryDepositOrderRequest 查询充值订单信息的请求参数封装，虽然只有一个参数，考虑今后接口升级不排除增加参数的可能性
	 * @return
	 */
	@PostMapping("/deposit/query")
	@ApiOperation("充值订单：《查询》接口调用")
	@ResponseBody
	public String testDepositQuery(@RequestBody QueryDepositOrderRequest queryDepositOrderRequest) {
		QueryDepositOrderResponse result = walletService.queryDepositOrder(queryDepositOrderRequest);
		return JSON.toJSONString(result);
	}
}
