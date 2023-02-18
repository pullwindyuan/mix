package com.universe.wallet.configuration;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * 充值API配置
 * @author Pullwind
 */
@Data
@Component
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
    public static final String CREATE = "/depositOrderCreate";
    /**
     * 查询充值订单接口路径
     */
    public static final String QUERY = "/depositOrderQuery";
    /**
     * 抢单充值
     */
    public static final String ORDER_SNATCH = "/depositOrderSnatch";
    /**
     * 查询充值金额列表
     */
    public static final String AMOUNT_LIST = "/depositAmountList";
    /**
     * 查询充值金额梯度
     */
    public static final String AMOUNT_GARD = "/depositAmountGard";
    /**
     * 创建充值订单成功后钱包服务回调的接口url
     */
    private String notifyUrl;
}
