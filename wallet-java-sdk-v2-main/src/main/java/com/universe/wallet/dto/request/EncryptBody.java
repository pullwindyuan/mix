package com.universe.wallet.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 回调请求参数
 * @author Pullwind
 */
@Data
public class EncryptBody {
    @ApiModelProperty(value = "加密后的请求参数，使用AES-GCM", required = true)
    private String encryptString;
}
