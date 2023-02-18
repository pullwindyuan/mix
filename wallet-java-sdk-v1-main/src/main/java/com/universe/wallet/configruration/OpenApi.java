package com.universe.wallet.configruration;

import lombok.Data;

/**
 * 钱包API配置信息
 * @author Pullwind
 */
@Data
public class OpenApi {
    private Deposit deposit;
    private Withdraw withdraw;
}
