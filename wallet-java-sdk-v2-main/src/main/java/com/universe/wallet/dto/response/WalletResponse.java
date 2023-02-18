package com.universe.wallet.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 返回信息的基础数据
 * @author Pullwind
 */
@Data
public class WalletResponse<T> {
    /**
     * 返回码，o为成功，其他的请结合msg判断使用
     */
    @ApiModelProperty(value = "接口返回状态码，0表示成功，其他根据接口文档定义")
    private Integer code;
    /**
     * 返回结果描述，为code为0的时候这里为success     */
    @ApiModelProperty(value = "接口返回说明信息，配合code使用")
    private String msg;
    /**
     * 返回的业务数据，不同的接口返回的数据不相同
     */
    @ApiModelProperty(value = "接口返回数据")
    private T data;
}
