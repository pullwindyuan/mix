package com.universe.wallet.apiLocalTest;

import com.alibaba.fastjson.JSON;
import com.universe.wallet.DTO.request.QueryWithdrawOrderRequest;
import com.universe.wallet.DTO.response.QueryWithdrawOrderResponse;
import com.universe.wallet.openApi.WalletService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 本地接口文档地址：http://localhost:8000/doc.html#/home
 * 测试对接查询提现订单详情的接口
 * @author Pullwind
 */
@Slf4j
@RestController
@RequestMapping("/api/openApi")
@Api(tags = "通过本地接口测试钱包开放接口调用")
public class QueryWithdrawOrder {

	@Autowired
	private WalletService walletService;

	/**
	 * 查询提现订单的业务参数结构，铜鼓接口测试的时候使用这样的json作为body
	 */
	public static final String queryWithdrawOrder = "{\n" +
			"  \"merchantOrderNo\": \"14235437865985898\",\n" +
			"}";

	/**
	 * 测试的时候请求body使用如下格式
	 * {
	 *   "merchantOrderNo": "9287349k3adfsdjflsdj",
	 * }
	 * @param queryWithdrawOrderRequest 查询提现订单信息请求参数封装，虽然只有一个参数，考虑今后接口升级不排除增加参数的可能性
	 * @return
	 */
	@PostMapping("/withdraw/query")
	@ApiOperation("提现订单：《查询》接口调用")
	@ResponseBody
	public String testWithdrawQuery(@RequestBody QueryWithdrawOrderRequest queryWithdrawOrderRequest) {
		QueryWithdrawOrderResponse result = walletService.queryWithdrawOrder(queryWithdrawOrderRequest);
		return JSON.toJSONString(result);
	}
}
