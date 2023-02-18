package com.universe.wallet.DTO.response;

import lombok.Data;

/**
 * 创建充值订单的返回数据
 * @author Pullwind
 */
@Data
public class CreateDepositOrderResponse {
    /**
     * 把店家会员转跳到这URL
     */
    private String url;
}
