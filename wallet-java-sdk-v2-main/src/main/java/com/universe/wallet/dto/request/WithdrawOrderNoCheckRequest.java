package com.universe.wallet.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 发起免审订单请求参数，由于SDK进行了高度封装，所以实际需要的 merchantMemberNo 参数已经封装在了内部方法中通过配置自动装配，因为
 * merchantMemberNo参数对于特定的店家而言是固定，通过配置读取是最好的选择，所以这里的定义只用于标识接口需要的实际参数
 * @author Pullwind
 */
@Data
public class WithdrawOrderNoCheckRequest {
    @ApiModelProperty(value = "店家会员编号，唯一值 最长200位，封装后的暴露给对接商家的方法不需要传递该参数，所以在演示的适合可以为空")
    private String merchantMemberNo;
}
