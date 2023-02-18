package com.universe.wallet.openapi;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.universe.wallet.dto.request.*;
import com.universe.wallet.dto.response.*;
import com.universe.wallet.configuration.WalletApiProperties;
import com.universe.wallet.enums.TradeType;
import com.universe.wallet.util.WalletRequestUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Pullwind
 */
@Slf4j
@Service
public class WalletService {
    @Resource
    private WalletApiProperties walletApiProperties;

    /**
     * 创建提现订单
     * @param createWithdrawOrderRequest 创建订单的参数结构，这里只保留了商户需要关注的参数，其余的都是通过配置文件进行配置，WalletRequestUtil工具类会自动
     * @return @return 创建成功会得到成功的返回值，失败会返回 null
     */
    public WalletResponse<CreateWithdrawOrderResponse> createWithdrawOrder(CreateWithdrawOrderRequest createWithdrawOrderRequest) {
        String url = walletApiProperties.getCreateWithdrawOrderUrl();
        return sendRequest(url, createWithdrawOrderRequest, CreateWithdrawOrderResponse.class, TradeType.WITHDRAW, true);
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
    public WalletResponse<Object> approvalWithdrawOrder(ApprovalWithdrawOrderRequest approvalWithdrawOrderRequest) {
        String url = walletApiProperties.getApprovalWithdrawOrderUrl();
        return sendRequest(url, approvalWithdrawOrderRequest, Object.class, TradeType.WITHDRAW, false);
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
    public WalletResponse<QueryWithdrawOrderResponse> queryWithdrawOrder(QueryWithdrawOrderRequest queryWithdrawOrderRequest) {
        String url = walletApiProperties.getQueryWithdrawOrderUrl();
        return sendRequest(url, queryWithdrawOrderRequest, QueryWithdrawOrderResponse.class, TradeType.WITHDRAW, false);
    }

    /**
     * 发起免审提现订单，这里无需传入任何参数。接口实际需要的参数已经在内部封装中自动传入
     * @return 返回结构化数据，包含了状态码code, 说明信息msg, 实际泛型业务数据data
     */
    public WalletResponse<WithdrawOrderNoCheckResponse> withdrawOrderNoCheck() {
        String url = walletApiProperties.getWithdrawOrderNoCheck();
        return sendRequest(url, new JSONObject(), WithdrawOrderNoCheckResponse.class, TradeType.WITHDRAW, false);
    }

    /**
     * 创建充值订单
     * @param createDepositOrderRequest 创建充值订单数据结构，这里只保留了商户需要关注的参数，其余的都是通过配置文件进行配置，
     *                                  WalletRequestUtil工具类会自动封装到最后的请求参数中
     * @return 返回结构化数据，包含了状态码code, 说明信息msg, 实际泛型业务数据data
     */
    public WalletResponse<CreateDepositOrderResponse> createDepositOrder(CreateDepositOrderRequest createDepositOrderRequest) {
        String url = walletApiProperties.getCreateDepositOrderUrl();
        return sendRequest(url, createDepositOrderRequest, CreateDepositOrderResponse.class, TradeType.DEPOSIT, true);
    }

    /**
     * 查询充值订单
     * @param queryDepositOrderRequest 查询充值订单数据结构，这里只保留了商户需要关注的参数，其余的都是通过配置文件进行配置，
     *                                 WalletRequestUtil工具类会自动封装到最后的请求参数中
     * @return 返回结构化数据，包含了状态码code, 说明信息msg, 实际泛型业务数据data
     */
    public WalletResponse<QueryDepositOrderResponse> queryDepositOrder(QueryDepositOrderRequest queryDepositOrderRequest) {
        String url = walletApiProperties.getQueryDepositOrderUrl();
        return sendRequest(url, queryDepositOrderRequest, QueryDepositOrderResponse.class, TradeType.DEPOSIT, false);
    }

    /**
     * 获取充值金额列表
     * @param depositAmountListRequest
     * @return 返回结构化数据，包含了状态码code, 说明信息msg, 实际泛型业务数据data
     */
    public WalletResponse<List<DepositAmountListResponse>> queryDepositAmountList(DepositAmountListRequest depositAmountListRequest) {
        String url = walletApiProperties.getDepositAmountList();
        return sendRequestReturnList(url, depositAmountListRequest, DepositAmountListResponse.class, TradeType.DEPOSIT, false);
    }

    /**
     * 抢单充值
     * @param depositOrderSnatchRequest
     * @return 返回结构化数据，包含了状态码code, 说明信息msg, 实际泛型业务数据data
     */
    public WalletResponse<DepositOrderSnatchResponse> depositOrderSnatch(DepositOrderSnatchRequest depositOrderSnatchRequest) {
        String url = walletApiProperties.getDepositOrderSnatch();
        return sendRequest(url, depositOrderSnatchRequest, DepositOrderSnatchResponse.class, TradeType.DEPOSIT, true);
    }

    /**
     * 充值金额梯度查询
     * @return 返回结构化数据，包含了状态码code, 说明信息msg, 实际泛型业务数据data
     */
    public WalletResponse<List<DepositAmountGardResponse>> queryDepositAmountGard() {
        String url = walletApiProperties.getDepositAmountGard();
        return sendRequestReturnList(url, new JSONObject(), DepositAmountGardResponse.class, TradeType.DEPOSIT, false);
    }

    /**
     * 发送请求
     * @param url
     * @param request
     * @param responseClazz
     * @param <T>
     * @param <K>
     * @return
     */
    private <T, K> WalletResponse<T>  sendRequest(String url, K request,
                                                  Class<T> responseClazz,
                                                  TradeType tradeType,
                                                  boolean withNotifyUrl) {
        JSONObject params = (JSONObject) JSON.toJSON(request);
        WalletResponse<T> result = WalletRequestUtil.walletRequest(url,
                params,
                walletApiProperties,
                responseClazz,
                tradeType,
                withNotifyUrl);
        log.warn("result：{}", result);
        return result;
    }

    /**
     * 发送请求
     * @param url
     * @param request
     * @param responseClazz
     * @param <T>
     * @param <K>
     * @return
     */
    private <T, K> WalletResponse<List<T>> sendRequestReturnList(String url, K request,
                                                                 Class<T> responseClazz,
                                                                 TradeType tradeType,
                                                                 boolean withNotifyUrl) {
        JSONObject params = (JSONObject) JSON.toJSON(request);
        WalletResponse<List<T>> result = WalletRequestUtil.walletRequestReturnList(url,
                params,
                walletApiProperties,
                responseClazz,
                tradeType,
                withNotifyUrl);
        log.warn("result：{}", result);
        return result;
    }
}
