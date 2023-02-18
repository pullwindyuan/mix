package com.universe.wallet.apilocaltest;

import com.universe.wallet.dto.request.ApprovalWithdrawOrderRequest;
import com.universe.wallet.dto.request.CreateWithdrawOrderRequest;
import com.universe.wallet.dto.request.QueryWithdrawOrderRequest;
import com.universe.wallet.dto.response.*;
import com.universe.wallet.openapi.WalletService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 本地接口文档地址：http://localhost:8000/doc.html#/home
 * 测试对接审批提现订单详情的接口
 * @author Pullwind
 */
@Slf4j
@RestController
@Api(tags = "通过本地接口测试钱包开放接口调用")
public class TestWithdrawApi {
    @Resource
    private WalletService walletService;


    /**
     * 测试的时候请求body使用如下格式
     * {
     *   "merchantOrderNo": "1645268345945",
     *   "amount": "166",
     *   "paymentMethod": 1,
     *   "bankAccountName": "members66",
     *   "bankAccount": "4000838450999950",
     *   "bankName": "中国建设银行",
     *   "bankBranch": "北京分行",
     * }
     * @param createWithdrawOrderRequest 创建提现订单需要的业务参数数据结构封装
     *                                   业务参数，不包括公共参数（timestamp, merchantMemberNo，notifyUrl）
     * @return 返回结构化数据，包含了状态码code, 说明信息msg, 实际泛型业务数据data
     */
    @PostMapping("/withdraw/create")
    @ApiOperation("提现：《创建提现订单》")
    @ResponseBody
    public WalletResponse<CreateWithdrawOrderResponse> testWithdrawCreate(@RequestBody CreateWithdrawOrderRequest createWithdrawOrderRequest) {
        log.info("start to tset testWithdrawCreate");
        return walletService.createWithdrawOrder(createWithdrawOrderRequest);
    }

    /**
     * 测试的时候请求body使用如下格式
     *{
     *     "merchantOrderNo":"9287349k3adfsdjflsdj",
     *     "state":1
     * }
     * @param approvalWithdrawOrderRequest 审核提现订单的请求参数封装
     *                                     业务参数，不包括公共参数（timestamp, merchantMemberNo）
     * @return 返回结构化数据，包含了状态码code, 说明信息msg, 实际泛型业务数据data
     */
    @PostMapping("/withdraw/approval")
    @ApiOperation("提现：《审核提现订单》")
    @ResponseBody
    public WalletResponse<Object> testWithdrawApproval(@RequestBody ApprovalWithdrawOrderRequest approvalWithdrawOrderRequest) {
        return walletService.approvalWithdrawOrder(approvalWithdrawOrderRequest);
    }

    /**
     * 测试的时候请求body使用如下格式
     * {
     *   "merchantOrderNo": "9287349k3adfsdjflsdj",
     * }
     * @param queryWithdrawOrderRequest 查询提现订单信息请求参数封装，虽然只有一个参数，考虑今后接口升级不排除增加参数的可能性.
     *                                  业务参数，不包括公共参数（timestamp, merchantMemberNo）
     * @return 返回结构化数据，包含了状态码code, 说明信息msg, 实际泛型业务数据data
     */
    @PostMapping("/withdraw/query")
    @ApiOperation("提现：《查询提现订单》")
    @ResponseBody
    public WalletResponse<QueryWithdrawOrderResponse> testWithdrawQuery(@RequestBody QueryWithdrawOrderRequest queryWithdrawOrderRequest) {
        return walletService.queryWithdrawOrder(queryWithdrawOrderRequest);
    }

    /**
     * 发起免审提现订单，这里无需传入任何参数。接口实际需要的参数已经在内部封装中自动传入
     * @return 返回结构化数据，包含了状态码code, 说明信息msg, 实际泛型业务数据data
     */
    @PostMapping("/withdraw/orderNoCheck")
    @ApiOperation("提现：《发起免审提现订单》")
    @ResponseBody
    public WalletResponse<WithdrawOrderNoCheckResponse> testWithdrawQuery() {
        return walletService.withdrawOrderNoCheck();
    }
}
