package com.universe.wallet.apilocaltest;

import com.universe.wallet.dto.request.CreateDepositOrderRequest;
import com.universe.wallet.dto.request.DepositAmountListRequest;
import com.universe.wallet.dto.request.DepositOrderSnatchRequest;
import com.universe.wallet.dto.request.QueryDepositOrderRequest;
import com.universe.wallet.dto.response.*;
import com.universe.wallet.openapi.WalletService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 本地接口文档地址：http://localhost:8000/doc.html#/home
 * 测试对接审批提现订单详情的接口
 * @author Pullwind
 */
@Slf4j
@RestController
@Api(tags = "通过本地接口测试钱包开放接口调用")
public class TestDepositApi {
    @Resource
    private WalletService walletService;

    /**
     * 测试的时候请求body使用如下格式
     * {
     *   "merchantOrderNo": "order_123456",
     *   "amount": "100",
     *   "paymentMethod": 1,
     * }
     * @param createDepositOrderRequest 业务参数，不包括公共参数（timestamp, merchantMemberNo，notifyUrl）
     * @return 返回结构化数据，包含了状态码code, 说明信息msg, 实际泛型业务数据data
     */
    @PostMapping("/deposit/create")
    @ApiOperation("充值：《创建充值订单》")
    @ResponseBody
    public WalletResponse<CreateDepositOrderResponse> testDepositCreate(@RequestBody CreateDepositOrderRequest createDepositOrderRequest) {
        return walletService.createDepositOrder(createDepositOrderRequest);
    }

    /**
     * 测试的时候请求body使用如下格式
     * {
     *   "merchantOrderNo": "9287349k3adfsdjflsdj",
     * }
     * @param queryDepositOrderRequest 查询充值订单信息的请求参数封装，虽然只有一个参数，考虑今后接口升级不排除增加参数的可能性。
     *                                 业务参数，不包括公共参数（timestamp, merchantMemberNo）
     * @return 返回结构化数据，包含了状态码code, 说明信息msg, 实际泛型业务数据data
     */
    @PostMapping("/deposit/query")
    @ApiOperation("充值：《查询充值订单》")
    @ResponseBody
    public WalletResponse<QueryDepositOrderResponse> testDepositQuery(@RequestBody QueryDepositOrderRequest queryDepositOrderRequest) {
        return walletService.queryDepositOrder(queryDepositOrderRequest);
    }

    /**
     * @param depositAmountListRequest 业务参数，不包括公共参数（timestamp, merchantMemberNo等）
     * @return 返回结构化数据，包含了状态码code, 说明信息msg, 实际泛型业务数据data
     */
    @PostMapping("/deposit/queryDepositAmountList")
    @ApiOperation("充值：《查询充值金额列表》")
    @ResponseBody
    public WalletResponse<List<DepositAmountListResponse>> testQueryDepositAmountList(@RequestBody DepositAmountListRequest depositAmountListRequest) {
        return walletService.queryDepositAmountList(depositAmountListRequest);
    }

    /**
     * 抢单充值接口测试
     * @param depositOrderSnatchRequest  业务参数，不包括公共参数（timestamp, merchantMemberNo，notifyUrl）
     * @return 返回结构化数据，包含了状态码code, 说明信息msg, 实际泛型业务数据data
     */
    @PostMapping("/deposit/orderSnatch")
    @ApiOperation("充值：《抢单充值》")
    @ResponseBody
    public WalletResponse<DepositOrderSnatchResponse> testDepositOrderSnatch(@RequestBody DepositOrderSnatchRequest depositOrderSnatchRequest) {
        return walletService.depositOrderSnatch(depositOrderSnatchRequest);
    }

    /**
     * 充值金额梯度查询，无参
     * @return 返回结构化数据，包含了状态码code, 说明信息msg, 实际泛型业务数据data
     */
    @PostMapping("/deposit/amountGard")
    @ApiOperation("充值：《查询充值金额梯度》")
    @ResponseBody
    public WalletResponse<List<DepositAmountGardResponse>> queryDepositAmountGard() {
        return walletService.queryDepositAmountGard();
    }
}
