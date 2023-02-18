package com.universe.wallet.DTO.notify;

import lombok.Data;

/**
 * 提现通知回调返回数据格式
 * @author Pullwind
 * {
 *   "appId": "xxxx",
 *   "merchantOrderNo": "9287349k3adfsdjflsdj",
 *   "merchantMemberNo": "tt_123456",
 *   "amount": "190.5",
 *   "serviceFee": "9.5",
 *   "bankAccountName": "唐七七",
 *   "bankAccount": "40008384509934995",
 *   "bankName": "中国建设银行",
 *   "bankBranch": "深圳分行",
 *   "state": 1,
 *   "timestamp": 1640146252,
 *   "sign": "xxxx"
 * }
 */
@Data
public class WithdrawNotifyBody {
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
     *	服务费
     */
    private String serviceFee;
    /**
     *  收款人
     */
    private String bankAccountName;
    /**
     *	银行卡号
     */
    private String bankAccount;
    /**
     *银行名称
     */
    private String bankName;
    /**
     * 银行支行
     */
    private String bankBranch;
    /**
     * 状态: 1.待审核, 2.待匹配, 3.待付款, 4.付款延時, 5.待放行, 6.放行延時, 7.投訴 8.維權 9.交易成功 10.交易失败
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
