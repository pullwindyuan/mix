package com.universe.wallet.configruration;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * 充值API配置
 * @author Pullwind
 */
@Data
@Component
//@ConfigurationProperties(prefix = "wallet.openApi.deposit")
public class Deposit {
    /**
     * 充值专用appId, 千万不能配置成《提现》的
     */
    private String appId;
    /**
     * 充值专用appKey, 千万不能配置成《提现》的
     */
    private String appKey;
    /**
     * 创建充值订单接口路径
     */
    private String create;
    /**
     * 查询充值订单接口路径
     */
    private String query;
    /**
     * 创建充值订单成功后钱包服务回调的接口url
     */
    private String notifyUrl;
}
