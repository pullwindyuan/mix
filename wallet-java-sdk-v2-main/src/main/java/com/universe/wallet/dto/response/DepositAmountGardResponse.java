package com.universe.wallet.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 *
 * @author Pullwind
 */
@Data
public class DepositAmountGardResponse {
    @ApiModelProperty(value = "1：小额，2：中额，3：大额")
    private int amountType;
    @ApiModelProperty(value = "金额区间说明")
    private String amountName;
    @ApiModelProperty(value = "金额区间")
    private String between;
}
