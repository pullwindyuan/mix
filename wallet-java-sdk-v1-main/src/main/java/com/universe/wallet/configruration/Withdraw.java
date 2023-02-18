package com.universe.wallet.configruration;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * 提现API配置
 * @author Pullwind
 */
@Data
@Component
//@ConfigurationProperties(prefix = "wallet.openApi.withdraw")
public class Withdraw {
    /**
     * 提现专用appId, 千万不能配置成《充值》的的
     */
    private String appId;
    /**
     * 提现专用appId, 千万不能配置成《充值》的的
     */
    private String appKey;
    /**
     * 创建提现订单接口路径
     */
    private String create;
    /**
     * 审核提现订单接口路径
     */
    private String approval;
    /**
     * 查询提现订单接口路径
     */
    private String query;
    /**
     * 创建提现订单成功后钱包服务回调的接口url
     */
    private String notifyUrl;
}
