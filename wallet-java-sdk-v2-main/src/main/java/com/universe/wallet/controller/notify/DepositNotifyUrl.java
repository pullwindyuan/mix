package com.universe.wallet.controller.notify;

import com.universe.wallet.dto.notify.DepositNotifyBody;
import com.universe.wallet.dto.request.EncryptBody;
import com.universe.wallet.configuration.WalletApiProperties;
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
@RequestMapping("/notify")
@Api(tags = "回调接口样例")
public class DepositNotifyUrl {

	@Autowired
	private WalletApiProperties walletApiProperties;

	/**
	 * 充值订单状态通知 目前只通知 (3. 代付款 9. 交易成功, 10. 交易失败)
	 * @param encryptBody 加密后的信息数据结构
	 * @return 通知成功处理返回 SUCCESS， 通知处理失败返回 FAIL
	 */
	@PostMapping("/deposit")
	@ResponseBody
	@ApiOperation("创建充值订单回调接口")
	public String depositNotify(@RequestBody EncryptBody encryptBody) {
		DepositNotifyBody data = WalletRequestUtil.getRawData(encryptBody,
				walletApiProperties.getDeposit().getAppKey(), DepositNotifyBody.class);
		//参考OrderStatus的枚举定义：状态: 2.待匹配, 3.待付款, 4.付款延時, 5.待放行, 6.放行延時, 7.投訴 8.維權 9.交易成功 10.交易失败
		if(data != null) {
			int status = data.getState();
			// TODO 根据回调的订单状态信息， 执行客户自己的处理逻辑
			//商户接收到可解密通知后，返回“SUCCESS”字符串，平台将不再通知(SUCCESS 使用大写字母)；如果没有反馈，平台将在10分钟内，通知3次，之后将不再主动发起通知
			return "SUCCESS";
		}else {
			//TODO 回调收到的数据不安全或者不正确，在这里执行客户自己的处理逻辑
			//商户收到不正确的通知后，返回“FAIL”字符串，平台将在10分钟内，通知3次，之后将不再主动发起通知
			return "FAIL";
		}
	}
}
