package com.universe.wallet.dto.response;

import lombok.Data;

/**
 * 创建提现订单的返回数据
 * @author Pullwind
 */
@Data
public class ApprovalWithdrawOrderResponse {
    /**
     * 目前该值为null, 判断是否执行成功是通过外层的code和msg，这个判断已经在服务方法中分装好了，这里是为了以后的扩展预留
     */
    private Object data;
}
