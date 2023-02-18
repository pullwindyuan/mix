package com.universe.wallet.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 配置类
 * @author Pullwind
 */
@Data
@Component
@ConfigurationProperties(prefix = "wallet")
public class WalletApiProperties {
    private String merchantMemberNo;
    private String version = "v2";
    private String baseUrl;
    private Deposit deposit;
    private Withdraw withdraw;

    public String getCreateWithdrawOrderUrl() {
        return this.baseUrl + Withdraw.CREATE;
    }

    public String getQueryWithdrawOrderUrl() {
        return this.baseUrl + Withdraw.QUERY;
    }

    public String getApprovalWithdrawOrderUrl() {
        return this.baseUrl + Withdraw.APPROVAL;
    }

    public String getWithdrawOrderNoCheck() {
        return this.baseUrl + Withdraw.ORDER_NO_CHECK;
    }

    public String getCreateDepositOrderUrl() {
        return this.baseUrl + Deposit.ORDER_SNATCH;
    }

    public String getQueryDepositOrderUrl() {
        return this.baseUrl + Deposit.QUERY;
    }

    public String getDepositOrderSnatch() {
        return this.baseUrl + Deposit.ORDER_SNATCH;
    }

    public String getDepositAmountList() {
        return this.baseUrl + Deposit.AMOUNT_LIST;
    }

    public String getDepositAmountGard() {
        return this.baseUrl + Deposit.AMOUNT_GARD;
    }
}
