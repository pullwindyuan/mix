package com.universe.wallet.DTO.response;

import lombok.Data;

/**
 * 外年报返回信息的基础数据
 * @author Pullwind
 */
@Data
public class WalletResponse<T> {
    /**
     * 返回码，o为成功，其他的请结合msg判断使用
     */
    private Integer code;
    /**
     * 返回结果描述，为code为0的时候这里为success     */
    private String msg;
    /**
     * 返回的业务数据，不同的接口返回的数据不相同
     */
    private T data;
}
