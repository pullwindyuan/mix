package com.universe.wallet.configruration;

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
    private String version;
    private String baseUrl;
    private OpenApi openApi;

    public String getCreateWithdrawOrderUrl() {
        return this.baseUrl + this.version + this.getOpenApi().getWithdraw().getCreate();
    }

    public String getQueryWithdrawOrderUrl() {
        return this.baseUrl + this.version + this.getOpenApi().getWithdraw().getQuery();
    }

    public String getApprovalWithdrawOrderUrl() {
        return this.baseUrl + this.version + this.getOpenApi().getWithdraw().getApproval();
    }

    public String getCreateDepositOrderUrl() {
        return this.baseUrl + this.version + this.getOpenApi().getDeposit().getCreate();
    }

    public String getQueryDepositOrderUrl() {
        return this.baseUrl + this.version + this.getOpenApi().getDeposit().getQuery();
    }
}
