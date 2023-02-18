package com.universe.wallet.apiLocalTest;

import com.universe.wallet.DTO.request.ApprovalWithdrawOrderRequest;
import com.universe.wallet.openApi.WalletService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 本地接口文档地址：http://localhost:8000/doc.html#/home
 * 测试对接审批提现订单详情的接口
 * @author Pullwind
 */
@Slf4j
@RestController
@RequestMapping("/api/openApi")
@Api(tags = "通过本地接口测试钱包开放接口调用")
public class ApprovalWithdrawOrder {

	@Autowired
	private WalletService walletService;

	/**
	 * 审批提现订单的业务参数结构，铜鼓接口测试的时候使用这样的json作为body
	 */
	public static final String approvalWithdrawOrder = "{\n" +
			"  \"merchantOrderNo\": \"14235437865985898\",\n" +
			"  \"state\": 1,\n" +
			"}";

	/**
	 * 测试的时候请求body使用如下格式
	 *{
	 *     "merchantOrderNo":"9287349k3adfsdjflsdj",
	 *     "state":1
	 * }
	 * @param approvalWithdrawOrderRequest 审核提现订单的请求参数封装
	 * @return
	 */
	@PostMapping("/withdraw/approval")
	@ApiOperation("提现订单：《审核》接口调用")
	@ResponseBody
	public String testWithdrawApproval(@RequestBody ApprovalWithdrawOrderRequest approvalWithdrawOrderRequest) {
		boolean result = walletService.approvalWithdrawOrder(approvalWithdrawOrderRequest);
		return String.valueOf(result);
	}
}
