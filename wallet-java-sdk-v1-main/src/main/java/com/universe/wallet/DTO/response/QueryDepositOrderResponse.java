package com.universe.wallet.DTO.response;

import lombok.Data;

/**
 * 查询充值订单的返回数据
 * @author Pullwind
 */
@Data
public class QueryDepositOrderResponse {
    /**
     *	店家充值订单号
     */
    private String merchantOrderNo;
    /**
     *	店户会员编号
     */
    private String merchantMemberNo;
    /**
     *	服务费
     */
    private String serviceFee;
    /**
     * 原充值金额
     */
    private String amount;
    /**
     *
     */
    private String actualAmount;
    /**
     * 状态: 2.待匹配, 3.待付款, 4.付款延時, 5.待放行, 6.放行延時, 7.投訴 8.維權 9.交易成功 10.交易失败
     */
    private Integer state;
    /**
     *	创间时间
     */
    private Long createdAt;
    /**
     * h5跳转链接，仅当code为0或者2的时候返回
     */
    private String h5url;
}
