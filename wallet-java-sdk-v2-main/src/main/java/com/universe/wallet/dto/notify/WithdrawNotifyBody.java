package com.universe.wallet.dto.notify;

import lombok.Data;

/**
 * 提现通知回调返回数据格式
 * @author Pullwind
 */
@Data
public class WithdrawNotifyBody {
    /**
     *  店家订单号
     */
    private String merchantOrderNo;
    /**
     *  店家会员编号
     */
    private String merchantMemberNo;
    /**
     *	实际金额
     */
    private String amount;
    /**
     *	服务费
     */
    private String serviceFee;

    /**
     * 状态: 2.待匹配, 3.待付款, 4.付款延時, 5.待放行, 6.放行延時, 7.投訴 8.維權 9.交易成功 10.交易失败
     */
    private Integer state;
    /**
     * 时间戳
     */
    private Long timestamp;
}
