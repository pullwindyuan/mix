package com.universe.wallet.openApi;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.universe.wallet.DTO.request.*;
import com.universe.wallet.DTO.response.*;
import com.universe.wallet.DTO.response.*;
import com.universe.wallet.DTO.request.*;
import com.universe.wallet.configruration.WalletApiProperties;
import com.universe.wallet.util.WalletRequestUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Pullwind
 */
@Slf4j
@Service
public class WalletService {
    @Autowired
    private WalletApiProperties walletApiProperties;

    /**
     * 创建提现订单
     * @param createWithdrawOrderRequest 创建订单的参数结构，这里只保留了商户需要关注的参数，其余的都是通过配置文件进行配置，WalletRequestUtil工具类会自动
     *               封装到最后的请求参数中
     * {
     *     "merchantOrderNo":"123456fdsag",
     *     "paymentMethod":1,
     *     "bankAccountName":"张三",
     *     "bankAccount":"3234456452342346",
     *     "bankName":"建设银行",
     *     "bankBranch":"北京分行",
     *     "amount":"123.45"
     * }
     * @return @return 创建成功会得到成功的返回值，失败会返回 null
     */
    public CreateWithdrawOrderResponse createWithdrawOrder(CreateWithdrawOrderRequest createWithdrawOrderRequest) {
        JSONObject params = (JSONObject) JSON.toJSON(createWithdrawOrderRequest);
        String url = walletApiProperties.getCreateWithdrawOrderUrl();
        WalletResponse<CreateWithdrawOrderResponse> result = WalletRequestUtil.walletRequest(url,
                                                                                    params,
                                                                                    walletApiProperties,
                                                                                    CreateWithdrawOrderResponse.class);
        log.warn("result：{}", result);
        return result.getData();
    }

    /**
     * 审核提现订单
     * @param approvalWithdrawOrderRequest 审批提现订单数据结构，这里只保留了商户需要关注的参数，其余的都是通过配置文件进行配置，WalletRequestUtil工具类会自动
     *               封装到最后的请求参数中
     * {
     *   "merchantOrderNo": "9287349k3adfsdjflsdj",
     *   "state": 1   //状态 1: 通过 2: 拒绝
     * }
     * @return 审核操作成功会返回true，失败会返回 false
     */
    public boolean approvalWithdrawOrder(ApprovalWithdrawOrderRequest approvalWithdrawOrderRequest) {
        JSONObject params = (JSONObject) JSON.toJSON(approvalWithdrawOrderRequest);
        String url = walletApiProperties.getApprovalWithdrawOrderUrl();
        WalletResponse<ApprovalWithdrawOrderResponse> result = WalletRequestUtil.walletRequest(url,
                                                                                params,
                                                                                walletApiProperties,
                                                                                ApprovalWithdrawOrderResponse.class);

        log.warn("result：{}", result);
        return result.getCode() == 0 ? true : false;
    }

    /**
     * 查询提现订单
     * @param queryWithdrawOrderRequest 查询提现订单数据结构，这里只保留了商户需要关注的参数，其余的都是通过配置文件进行配置，WalletRequestUtil工具类会自动
     *               封装到最后的请求参数中
     * {
     *   "merchantOrderNo": "9287349k3adfsdjflsdj",
     * }
     * @return 查询成功会得到成功的返回值，失败会返回 null
     */
    public QueryWithdrawOrderResponse queryWithdrawOrder(QueryWithdrawOrderRequest queryWithdrawOrderRequest) {
        JSONObject params = (JSONObject) JSON.toJSON(queryWithdrawOrderRequest);
        String url = walletApiProperties.getQueryWithdrawOrderUrl();
        WalletResponse<QueryWithdrawOrderResponse> result = WalletRequestUtil.walletRequest(url,
                                                                                    params,
                                                                                    walletApiProperties,
                                                                                    QueryWithdrawOrderResponse.class);
        log.warn("result：{}", result);
        return result.getData();
    }

    /**
     * 创建充值订单
     * @param createDepositOrderRequest 创建充值订单数据结构，这里只保留了商户需要关注的参数，其余的都是通过配置文件进行配置，WalletRequestUtil工具类会自动
     *               封装到最后的请求参数中
     * {
     *   "merchantOrderNo": "order_123456",
     *   "amount": "100",
     *   "paymentMethod": 1,
     *   "notifyUrl":"http://127.0.0.1/order/state",
     * }
     * @return 创建成功会得到成功的返回值，失败会返回 null
     */
    public CreateDepositOrderResponse createDepositOrder(CreateDepositOrderRequest createDepositOrderRequest) {
        JSONObject params = (JSONObject) JSON.toJSON(createDepositOrderRequest);
        String url = walletApiProperties.getCreateDepositOrderUrl();
        WalletResponse<CreateDepositOrderResponse> result = WalletRequestUtil.walletRequest(url,
                                                                                    params,
                                                                                    walletApiProperties,
                                                                                    CreateDepositOrderResponse.class);
        log.warn("result：{}", result);
        return result.getData();
    }

    /**
     * 查询充值订单
     * @param queryDepositOrderRequest 查询充值订单数据结构，这里只保留了商户需要关注的参数，其余的都是通过配置文件进行配置，WalletRequestUtil工具类会自动
     *               封装到最后的请求参数中
     * {
     *   "merchantOrderNo": "9287349k3adfsdjflsdj",
     * }
     * @return 查询成功会得到成功的返回值，失败会返回 null
     */
    public QueryDepositOrderResponse queryDepositOrder(QueryDepositOrderRequest queryDepositOrderRequest) {
        JSONObject params = (JSONObject) JSON.toJSON(queryDepositOrderRequest);
        String url = walletApiProperties.getQueryDepositOrderUrl();
        WalletResponse<QueryDepositOrderResponse> result = WalletRequestUtil.walletRequest(url,
                                                                                    params,
                                                                                    walletApiProperties,
                                                                                    QueryDepositOrderResponse.class);
        log.warn("result：{}", result);
        return result.getData();
    }
}
