package com.universe.wallet.configuration;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * 提现API配置
 * @author Pullwind
 */
@Data
@Component
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
    public static final String CREATE = "/withdrawOrderCreate";
    /**
     * 审核提现订单接口路径
     */
    public static final String APPROVAL = "/withdrawOrderApproval";
    /**
     * 查询提现订单接口路径
     */
    public static final String QUERY = "/withdrawOrderQuery";
    /**
     * 发起免审订单
     */
    public static final String ORDER_NO_CHECK = "/withdrawOrderNoCheck";
    /**
     * 创建提现订单成功后钱包服务回调的接口url
     */
    private String notifyUrl;

}
