package com.universe.wallet.controller.notify;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.universe.wallet.DTO.notify.DepositNotifyBody;
import com.universe.wallet.configruration.WalletApiProperties;
import com.universe.wallet.util.WalletRequestUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 接口文档地址：http://localhost:8000/doc.html#/home
 * @author Pullwind
 */
@Slf4j
@RestController
@RequestMapping("/api/notify")
@Api(tags = "回调接口样例")
public class DepositNotifyUrl {

	@Autowired
	private WalletApiProperties walletApiProperties;

	@PostMapping("/deposit")
	@ResponseBody
	@ApiOperation("创建充值订单回到接口")
	public String depositNotify(@RequestBody DepositNotifyBody depositNotifyBody) {
		log.info("/api/notify/deposit result: {}", JSONObject.toJSONString(depositNotifyBody));

		boolean match = WalletRequestUtil.checkSign((JSONObject) JSON.toJSON(depositNotifyBody),
				walletApiProperties.getOpenApi().getDeposit().getAppKey());
		//状态: 2.待匹配, 3.待付款, 4.付款延時, 5.待放行, 6.放行延時, 7.投訴 8.維權 9.交易成功 10.交易失败
		if(match) {
			// TODO 签名验证成功，回调收到的数据可信赖， 在这里执行客户自己的处理逻辑，比如对depositNotifyBody的数据进行存储等
			//商户接收到通知后，返回“SUCCESS”字符串，平台将不再通知(SUCCESS 使用大写字母)；如果没有反馈，平台将在10分钟内，通知3次，之后将不再主动发起通知
			return "SUCCESS";
		}else {
			//TODO 签名验证失败，回调收到的数据不安全或者不争取，在这里执行客户自己的处理逻辑
			//商户接收到通知后，返回“FAIL”字符串，平台将在10分钟内，通知3次，之后将不再主动发起通知
			return "FAIL";
		}
	}
}
