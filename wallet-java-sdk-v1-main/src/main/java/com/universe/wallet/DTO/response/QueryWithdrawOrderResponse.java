package com.universe.wallet.DTO.response;

import lombok.Data;

/**
 * 查询提现订单返回数据
 * @author Pullwind
 */
@Data
public class QueryWithdrawOrderResponse {
    /**
     *	店户会员编号
     */
    private String merchantMemberNo;
    /**
     *	店家订单号
     */
    private String merchantOrderNo;
    /**
     *	服务费
     */
    private String serviceFee;
    /**
     *	提现金额
     */
    private String amount;
    /**
     *	收款人
     */
    private String bankAccountName;
    /**
     * 状态: 1.待审核, 2.待匹配, 3.待付款, 4.付款延時, 5.待放行, 6.放行延時, 7.投訴 8.維權 9.交易成功 10.交易失败
     */
    private Integer state;
    /**
     *	银行卡号
     */
    private String bankAccount;
    /**
     *	银行名称
     */
    private String bankName;
    /**
     * 分行
     */
    private String bankBranch;
    /**
     *	创间时间
     */
    private Long createdAt;
    /**
     *	h5跳转链接，仅当code为0或者2的时候返回
     */
    private String h5url;
}
