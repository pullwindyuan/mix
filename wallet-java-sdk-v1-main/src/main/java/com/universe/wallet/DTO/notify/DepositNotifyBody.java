package com.universe.wallet.DTO.notify;

import lombok.Data;

/**
 * 充值通知回调返回数据格式
 * @author Pullwind
 * {
 *   "appId": "xxxx",
 *   "merchantOrderNo", "xxx"
 *   "merchantMemberNo": "tt_123456",
 *   "amount": "190",
 *   "actualAmount": "250"
 *   "serviceFee": "9.5",
 *   "state": 1,
 *   "timestamp": 1640146252,
 *   "sign": "xxxx"
 * }
 */
@Data
public class DepositNotifyBody {
    /**
     * 店家 APPID
     */
    private String appId;
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
     *	最后实际撮合金额
     */
    private String actualAmount;
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
    /**
     * 签名
     */
    private String sign;

}
