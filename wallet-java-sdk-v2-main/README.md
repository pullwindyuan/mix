# ***\*JAVA.SDK文档说明\****

首先您需要先获取到 APPID 与 APPKey，后续会用用这两组来串接，店家与我门服务器之间的传递将会透过 APPKey 进行加解密。充值与提现 的APPID 分开

## ***\*说明\****

傳輸方式： 採用 HTTP 傳輸

提交方式： 採用 POST 方式提交

请求格式： JSON

字符編碼： UTF-8

請求返回數據： JSON

金额单位: 元

## ***\*加密过程\****

AES GCM 加密

对所有传递参数进行json序列化

对序列化后的字符串进行AES GCM加密

前12字节为 nonce 后面是加密之后数据 ciphertext

对nonce + ciphertext 进行base64 URLEncoding 编码得到 encryptString

## ***\*解密过程\****

对encryptString进行base64 URLDecoding 解码得到密文

前12字节为 nonce 后面是加密之后数据 ciphertext

对密文进行AES GCM解密得到明文

对明文进行 json 反序列化得到传递参数

## ***\*加密请求\****

加密示例：

由于AES GCM加密算法的特性，我们无法对同一原始信息在每次加密后输出相同的密文，所以需要使用能否正确解密我们提供的示例密文来判断加解密算法正确性。

将以下提供的key带入到自己的解密方法中，如果能够将base64正确的解密出req所示的内容就说明加解密方法是正确的。需要注意的是nonce的长度一定要设置成12。

key:

TK*K0T1(%6UCT(tlpZVB0516E&LOx#!@



req: {"appId":"","merchantOrderNo":"iizbzVQOcLiYjUgUeIZhgCOEBfjRwo","merchantMemberNo":"ZjzKDvufenWYLCxQNkGL","paymentMethod":1,"accountName":"张三","account":"3234456452342346","bankAccountName":"","bankAccount":"","bankName":"建设银行","bankBranch":"上海支行","notifyUrl":"https://xxx.xxxx.com/order","timestamp":1648435896,"sign":"","amount":"1.1","notAudit":false,"qrCodeUrl":""}



hex：ciphertext:b80f09e9818a75615feecbe55bf8a1ae3943e34e385906f733c9fbf114e020c0e01c4d507f35140ff627e9c10c59643768cf9170d84ed3bb92d2b3199767ecee71abcfdb07e15b6664bbf4deca1d58eb5b070ac9269668b227771791e217321b7c28ad0ef398efe2831e8e2629dc37aa7ba42b1886369afb4b2e3910450779006ae2d4402ed7963f9d503b302321c7bebfe2d0c60d7da6df309315eff063bacb120536b3d1dbd711f4fac91634623a32242c39e44d6d330a5c9a4acff3830dc85897e234fb1544c513c35d1a50dfc8ae2d2c0777152ed2f7c15cfef7f85035486a30d64dcf536c84559edf28186407503ef8f19e49f58a6e31507110bf0286e688f3a4d7045cfdb9d5cc48f58f9a2310b109c38cc4200f81e9a284ddbee9f291643ba40495922e12df3a8761ff12d9546b54b545ec07a945127459a521a5f7190e55107e167f166bfd6a2fb7e97c6a3c0d1b94b2d3686258d087fb00d3e7ac1b066efea446ffa6afa26f5bb03fde9d73dc1dfaec1bba033782c211c1ef887cdf16bdad2416d13852881ca4d835cbd7f2a5d65e7b96170717dd839f4960d31c02



base64：ciphertext:uA8J6YGKdWFf7svlW_ihrjlD4044WQb3M8n78RTgIMDgHE1QfzUUD_Yn6cEMWWQ3aM-RcNhO07uS0rMZl2fs7nGrz9sH4VtmZLv03sodWOtbBwrJJpZosid3F5HiFzIbfCitDvOY7-KDHo4mKdw3qnukKxiGNpr7Sy45EEUHeQBq4tRALteWP51QOzAjIce-v-LQxg19pt8wkxXv8GO6yxIFNrPR29cR9PrJFjRiOjIkLDnkTW0zClyaSs_zgw3IWJfiNPsVRMUTw10aUN_Iri0sB3cVLtL3wVz-9_hQNUhqMNZNz1NshFWe3ygYZAdQPvjxnkn1im4xUHEQvwKG5ojzpNcEXP251cxI9Y-aIxCxCcOMxCAPgemihN2-6fKRZDukBJWSLhLfOodh_xLZVGtUtUXsB6lFEnRZpSGl9xkOVRB-Fn8Wa_1qL7fpfGo8DRuUstNoYljQh_sA0-esGwZu_qRG_6avom9bsD_enXPcHfrsG7oDN4LCEcHviHzfFr2tJBbROFKIHKTYNcvX8qXWXnuWFwcX3YOfSWDTHAI=



## ***\*游戏大厅需要增加查看订单按钮，免找用户不到订单\*******\*（此按钮尽量实现）\****

1、重复提交充值或提现：打开H5页面，直接提示您当前有订单尚未完成，请选择取消或继续，取消则直接取消，继续的自动跳到当前订单。

2、查看订单按钮：打开H5页面，自动跳到当前订单页

## ***\*公共参数配置说明\****

**# 钱包API配置**

wallet:

**#由钱包分配**

merchantMemberNo: bevan004

**#钱包接口主地址，由钱包发放该地址**

baseUrl: https://go-wallet-partner.cg5.co

openApi:

withdraw:

**#由钱包分配，千万不要配置错误，充值和提现是不同的appId-appKey对，充值的appId是以TX开头的**

appId: TX4ENSM2Q5JJ922B **#TXYOHOPUUQELKO31 #TX3GPJ7UIMOQ77QJ #TX4ENSM2Q5JJ922B #TX3GPJ7UIMOQ77QJ**

**#由钱包分配，千万不要配置错误，充值和提现是不同的appId-appKey对**

appKey: TKwBkQ0rtb$vy4L5xQQCbYUdU7SEZy7X **#TK^WykC9YimNLso#w#Ii(ZeZgnZz\*M29 #TK0qUQi@Vl(qdO#94BCqIjx9%SSvKBZL #TKwBkQ0rtb$vy4L5xQQCbYUdU7SEZy7X #TK0qUQi@Vl(qdO#94BCqIjx9%SSvKBZL**

**# 提现回调地址,请配置成自己的回调地址，这里配的是demo中的演示示例的回调地址，供参考**

notifyUrl: http://localhost:8000/notify/withdraw

deposit:

**#由钱包分配，千万不要配置错误，充值和提现是不同的appId-appKey对，充值的appId是以CZ开头的**

appId: CZGB5WHUMJXLTRFK

**#由钱包分配，千万不要配置错误，充值和提现是不同的appId-appKey对**

appKey: CKtOay%jvLaMTqBT0pmNcrDyavH19bui

**# 充值回调地址,请配置成自己的回调地址，这里配的是demo中的演示示例的回调地址，供参考**

notifyUrl: http://localhost:8000/notify/deposit



通过将上述配置添加到自己项目的配置文件中以后，com.universe.wallet.configruration.WalletApiProperties便可自动装配为全局配置实体对象，在需要使用的类中使用。注意：提现和充值的notifyUrl也是分开配置的的，示例配置中的地址是SDK包中com.universe.wallet.controller.notify的提现和充值对应的测试接口地址。

通过在需要配置的类中定义

@Autowired

private WalletApiProperties walletApiProperties;

便可读取配置的公共参数。目前在本SDK中这些参数绝大部分都进行了封装，在使用本SDK时只需要正确配置即可，在自己的业务代码中不需要直接使用。

# ***\*工具类介绍\****

位于com.universe.wallet.util包下有两个工具类：

### ***\*1、OkHttpClientUtil\****

用于发起http请求

### ***\*2、AesUtil\****

加解密工具类，本SDK中的加解密均使用的本工具类中的方法。

本工具类的main用于加解密算法正确性的测试以及回调接口测试的参数生成，具体参见源码注释。通过运行main后再控制台能够看到如下信息：

![img](file:///C:\Users\Pullwind\AppData\Local\Temp\ksohtml5848\wps1.jpg)

depositAppKey需要复制application.yml配置文件

wallet:

deposit:

appKey:

withdrawAppKey需要复制application.yml配置文件

wallet:

withdraw:

appKey:



### ***\*3、WalletRequestUtil\****

钱包SDK核心工具类，为了让商户侧专注于业务本身，公共的参数的封装和处理在本SDK中都是内部处理（version、appid、timestamp、merchantMemberNo），商户只需要关注于业务直接相关的参数。通过该工具方法可以直接发起接口请求并获取到返回数据（支持泛型解析）。



#### ***\*钱包接口调用的封装工具方法：\*******\*walletRequest\*******\*、\*******\*walletRequestReturnList\****

可通过输入：请求接口的url，业务参数集合， 公共参数配置和希望输出的实体对象类及其他控制参数就能够获取到请求接口的最终返回数据。

#### ***\*请求参数封装工具方法：\*******\*formatRequestParams\****

对请求的原始参数进行格式化封装，其加密操作也在这里完成（调用***\*：\*******\*encode工具方法\****）

#### ***\*加密工具方法：\*******\*encode\****

对请求数据进行AES GCM加密，输出的是base64 UrlEncode字符串

#### ***\*解密工具方法：\*******\*getRawData\****

将接收到的加密数据进行解密并得输出制定的数据类型



# ***\*SDK工作流程图\****

![img](file:///C:\Users\Pullwind\AppData\Local\Temp\ksohtml5848\wps2.jpg)



# ***\*接口对接说明\****

## ***\*提现类\****

位于package com.universe.wallet.openapi包中的WalletService类可以直接用于处理钱包接口的调用，里面包括了[建立提现订单](#/107/8324)、[审批提现订单](#/107/8325)、[建立充值订单](#/107/8326)、[充值订单查询](#/107/8328)、[提现订单查询](#/107/8329)、[发起免审订单](#/107/8809)、[充值金额梯度查询](#/107/8816)、[充值金额列表](#/107/8817)、[抢单充值](#/107/8829)这9个API的调用封装。

### ***\*创建提现订单示例\****

代码：位于com.universe.wallet.openapi.WalletService

**/****

*** 创建提现订单**

***** **@param** **createWithdrawOrderRequest** **创建订单的参数结构，这里只保留了商户需要关注的参数，其余的都是通过配置文件进行配置，WalletRequestUtil工具类会自动**

***** **@return** **@return 创建成功会得到成功的返回值，失败会返回 null**

***/**

**public** WalletResponse<CreateWithdrawOrderResponse> createWithdrawOrder(CreateWithdrawOrderRequest createWithdrawOrderRequest) **throws** UnsupportedEncodingException {

String url = walletApiProperties.getCreateWithdrawOrderUrl();

**return** sendRequest(url, createWithdrawOrderRequest, CreateWithdrawOrderResponse.**class**, TradeType.WITHDRAW, **true**);

}





说明：这里就是通过调用前面工具类介绍中说的**walletRequest**来发起创建提现订单。回调地址notifyUrl参数无需手动维护，内部已经封装为自动填充。

创建订单的参数结构，这里只保留了商户需要关注的参数，其余的都是通过配置文件对应的walletApiProperties进行配置收集的**walletRequest**工具方法会自动将公共的参数封装到请求数据当中。商户需要关注的业务参数如下：

{

"merchantOrderNo": "1645268345945",

"amount": "166",

"paymentMethod": 1,

"accountName": "members66",

"account": "4000838450999950",

"bankName": "中国建设银行",

"bankBranch": "北京分行",

"qrCodeUrl": "http://qucodeurl.com"

}

重点说明下paymentMethod参数，v1版本目前为1即可，表示通过银行卡提现，暂未提供更多可选参数

### ***\*审批提现订单示例\****

代码：位于com.universe.wallet.openapi.WalletService

**/****

*** 审核提现订单**

***** **@param** **approvalWithdrawOrderRequest** **审批提现订单数据结构，这里只保留了商户需要关注的参数，其余的都是通过配置文件进行配置，WalletRequestUtil工具类会自动**

***        封装到最后的请求参数中**

*** {**

***  "merchantOrderNo": "9287349k3adfsdjflsdj",**

***  "state": 1  //状态 1: 通过 2: 拒绝**

*** }**

***** **@return** **审核操作成功会返回true，失败会返回 false**

***/**

**public** WalletResponse<Object> approvalWithdrawOrder(ApprovalWithdrawOrderRequest approvalWithdrawOrderRequest) **throws** UnsupportedEncodingException {

String url = walletApiProperties.getApprovalWithdrawOrderUrl();

**return** sendRequest(url, approvalWithdrawOrderRequest, Object.**class**, TradeType.WITHDRAW, **false**);

}

说明：这里就是通过调用前面工具类介绍中说的**walletRequest**来发起审核提现订单。

商户需要关注的业务参数如下



{

​    "merchantOrderNo": "9287349k3adfsdjflsdj",

​    "state": 1  //状态 1: 通过 2: 拒绝

}



### ***\*查询提现订单示例\****

代码：位于com.universe.wallet.openapi.WalletService

**/****

*** 查询提现订单**

***** **@param** **queryWithdrawOrderRequest** **查询提现订单数据结构，这里只保留了商户需要关注的参数，其余的都是通过配置文件进行配置，WalletRequestUtil工具类会自动**

***        封装到最后的请求参数中**

*** {**

***  "merchantOrderNo": "9287349k3adfsdjflsdj",**

*** }**

***** **@return** **查询成功会得到成功的返回值，失败会返回 null**

***/**

**public** WalletResponse<QueryWithdrawOrderResponse> queryWithdrawOrder(QueryWithdrawOrderRequest queryWithdrawOrderRequest) **throws** UnsupportedEncodingException {

String url = walletApiProperties.getQueryWithdrawOrderUrl();

**return** sendRequest(url, queryWithdrawOrderRequest, QueryWithdrawOrderResponse.**class**, TradeType.WITHDRAW, **false**);

}





说明：这里就是通过调用前面工具类介绍中说的**walletRequest**来发起查询提现订单。

商户需要关注的业务参数如下

{

​    "merchantOrderNo": "9287349k3adfsdjflsdj",

}



### ***\*发起免审提现订单\****

代码：位于com.universe.wallet.openapi.WalletService

**/****

*** 发起免审提现订单，这里无需传入任何参数。接口实际需要的参数已经在内部封装中自动传入**

***** **@return**

***** **@throws** **UnsupportedEncodingException**

***/**

**public** WalletResponse<WithdrawOrderNoCheckResponse> withdrawOrderNoCheck() **throws** UnsupportedEncodingException {

String url = walletApiProperties.getWithdrawOrderNoCheck();

**return** sendRequest(url, **new** JSONObject(), WithdrawOrderNoCheckResponse.**class**, TradeType.WITHDRAW, **false**);

}





说明：这里就是通过调用前面工具类介绍中说的**walletRequest**来发起免审提现订单。

商户无需关注任何参数。

### ***\*提现订单回调成功示例\****

代码：

**/****

*** 接口文档地址：http://localhost:8000/doc.html#/home**

***** **@author** **Pullwind**

***/**

**@Slf4j**

**@RestController**

**@RequestMapping**("/notify")

**@Api**(tags = "回调接口样例")

**public class** WithdrawNotifyUrl {



**@Autowired**

**private** WalletApiProperties walletApiProperties;



**@PostMapping**("/withdraw")

**@ResponseBody**

**@ApiOperation**("创建提现订单回到接口")

**public** String withdrawNotify(**@RequestBody** EncryptBody encryptBody) {

WithdrawNotifyBody data = WalletRequestUtil.getRawData(encryptBody,

​      walletApiProperties.getOpenApi().getWithdraw().getAppKey(), WithdrawNotifyBody.**class**);

**//参考OrderStatus的枚举定义：状态: 2.待匹配, 3.待付款, 4.付款延時, 5.待放行, 6.放行延時, 7.投訴 8.維權 9.交易成功 10.交易失败**

**if**(data != **null**) {

​    **int** status = data.getState();

​    **//** **TODO 根据回调的订单状态信息， 执行客户自己的处理逻辑**

​    **//商户接收到可解密通知后，返回“SUCCESS”字符串，平台将不再通知(SUCCESS 使用大写字母)；如果没有反馈，平台将在10分钟内，通知3次，之后将不再主动发起通知**

​    **return** "SUCCESS";

}**else** {

​    **//****TODO 回调收到的数据不安全或者不正确，在这里执行客户自己的处理逻辑**

​    **//商户收到不正确的通知后，返回“FAIL”字符串，平台将在10分钟内，通知3次，之后将不再主动发起通知**

​    **return** "FAIL";

}

}

}







说明：这是一个可以直接使用的独立controller类。收到钱包的回调并成功解密后即可根据回调参数中的 status 处理客户业务逻辑，商户确认回调数据正确后需要回复大写的”SUCCESS”。钱包服务收到SUCCESS回复后视为成功回调，后续不再会发起回调，商户需要在确认自己已经成功处理了回调数据后再做回应。如果还需要得到某个订单的确切状态就需要调用***\*《查询提现订单示例》\****。

### ***\*提现订单回调失败示例\****

代码：见《提现订单回调成功》一节，也就是上一节。



说明：这是一个可以直接使用的独立controller类。解密失败即可视为回调失败，应该立即回复大写的”FAIL”。如果商户发现回调数据有误，做好相应的记录，然后回复大写的”FAIL”。钱包收到”FAIL”回复后视为回调失败，会启动补充通知的流程：平台将在10分钟内，通知3次，之后将不再主动发起通知。如果还需要得到某个订单的确切状态就需要调用***\*《查询提现订单示例》\****

## ***\*充值类\****

### ***\*创建充值订单示例\****

代码：位于com.universe.wallet.openapi.WalletService

**/****

*** 创建充值订单**

***** **@param** **createDepositOrderRequest** **创建充值订单数据结构，这里只保留了商户需要关注的参数，其余的都是通过配置文件进行配置，**

***                 WalletRequestUtil工具类会自动封装到最后的请求参数中**

***** **@return** **创建成功会得到成功的返回值，失败会返回 null**

***/**

**public** WalletResponse<CreateDepositOrderResponse> createDepositOrder(CreateDepositOrderRequest createDepositOrderRequest) **throws** UnsupportedEncodingException {

String url = walletApiProperties.getCreateDepositOrderUrl();

**return** sendRequest(url, createDepositOrderRequest, CreateDepositOrderResponse.**class**, TradeType.DEPOSIT, **true**);

}





说明：这里就是通过调用前面工具类介绍中说的**walletRequest**来发起创建充值订单。

回调地址notifyUrl参数无需手动维护，内部已经封装为自动填充。

商户需要关注的业务参数如下

{

"merchantOrderNo": "order_123456",

"amount": "100",

"paymentMethod": 1,

"orderNo": "CND2205311721388927"

}



### ***\*查询充值定单示例\****

代码：位于com.universe.wallet.openapi.WalletService

**/****

*** 查询充值订单**

***** **@param** **queryDepositOrderRequest** **查询充值订单数据结构，这里只保留了商户需要关注的参数，其余的都是通过配置文件进行配置，**

***                 WalletRequestUtil工具类会自动封装到最后的请求参数中**

***** **@return** **查询成功会得到成功的返回值，失败会返回 null**

***/**

**public** WalletResponse<QueryDepositOrderResponse> queryDepositOrder(QueryDepositOrderRequest queryDepositOrderRequest) **throws** UnsupportedEncodingException {

String url = walletApiProperties.getQueryDepositOrderUrl();

**return** sendRequest(url, queryDepositOrderRequest, QueryDepositOrderResponse.**class**, TradeType.DEPOSIT, **false**);

}





说明：这里就是通过调用前面工具类介绍中说的**walletRequest**来发起查询充值订单。

商户需要关注的业务参数如下

{

​    "merchantOrderNo": "9287349k3adfsdjflsdj",

}



### ***\*获取充值金额列表示例\****

代码：位于com.universe.wallet.openapi.WalletService

**/****

*** 获取充值金额列表**

***** **@param** **depositAmountListRequest**

***** **@return**

***/**

**public** WalletResponse<List<DepositAmountListResponse>> queryDepositAmountList(DepositAmountListRequest depositAmountListRequest) **throws** UnsupportedEncodingException {

String url = walletApiProperties.getDepositAmountList();

**return** sendRequestReturnList(url, depositAmountListRequest, DepositAmountListResponse.**class**, TradeType.DEPOSIT, **false**);

}







说明：这里就是通过调用前面工具类介绍中说的**walletRequest**来发起获取充值金额列表。

商户需要关注的业务参数如下

{

"amountType": 1,

"paymentMethod": 1

}



### ***\*抢单充值示例\****

代码：位于com.universe.wallet.openapi.WalletService

**/****

*** 抢单充值**

***** **@param** **depositOrderSnatchRequest**

***** **@return**

***** **@throws** **UnsupportedEncodingException**

***/**

**public** **WalletResponse****<****DepositOrderSnatchResponse****>** **depositOrderSnatch****(****DepositOrderSnatchRequest** **depositOrderSnatchRequest****)** **throws** **UnsupportedEncodingException** **{**

**String** **url =** **walletApiProperties****.****getDepositOrderSnatch****()****;**

**return** **sendRequest****(****url,** **depositOrderSnatchRequest****,** **DepositOrderSnatchResponse****.****class****,** **TradeType****.****DEPOSIT****,** **true****)****;**

**}**







说明：这里就是通过调用前面工具类介绍中说的**walletRequest**来发起抢单充值。

回调地址notifyUrl参数无需手动维护，内部已经封装为自动填充。

商户需要关注的业务参数如下

{

"amount": "358",

"merchantOrderNo": "CND2205311721388927",

"paymentMethod": 1

}



### ***\*充值金额梯度查询示例\****

代码：位于com.universe.wallet.openapi.WalletService

**/****

*** 充值金额梯度查询**

***** **@return**

***** **@throws** **UnsupportedEncodingException**

***/**

**public** **WalletResponse****<****List****<****DepositAmountGardResponse****>****>** **queryDepositAmountGard****()** **throws** **UnsupportedEncodingException** **{**

**String** **url =** **walletApiProperties****.****getDepositAmountGard****()****;**

**return** **sendRequestReturnList****(****url,** **new** **JSONObject****()****,** **DepositAmountGardResponse****.****class****,** **TradeType****.****DEPOSIT****,** **false****)****;**

**}**







说明：这里就是通过调用前面工具类介绍中说的**walletRequest**来发充值金额梯度查询。

商户无需关注任何参数

### ***\*充值订单回调成功示例\****

代码：

**/****

*** 接口文档地址：http://localhost:8000/doc.html#/home**

***** **@author** **Pullwind**

***/**

**@Slf4j**

**@RestController**

**@RequestMapping**("/notify")

**@Api**(tags = "回调接口样例")

**public class** DepositNotifyUrl {



**@Autowired**

**private** WalletApiProperties walletApiProperties;



**@PostMapping**("/deposit")

**@ResponseBody**

**@ApiOperation**("创建充值订单回到接口")

**public** String depositNotify(**@RequestBody** EncryptBody encryptBody) {

DepositNotifyBody data = WalletRequestUtil.getRawData(encryptBody,

​      walletApiProperties.getOpenApi().getWithdraw().getAppKey(), DepositNotifyBody.**class**);

**//参考OrderStatus的枚举定义：状态: 2.待匹配, 3.待付款, 4.付款延時, 5.待放行, 6.放行延時, 7.投訴 8.維權 9.交易成功 10.交易失败**

**if**(data != **null**) {

​    **int** status = data.getState();

​    **//** **TODO 根据回调的订单状态信息， 执行客户自己的处理逻辑**

​    **//商户接收到可解密通知后，返回“SUCCESS”字符串，平台将不再通知(SUCCESS 使用大写字母)；如果没有反馈，平台将在10分钟内，通知3次，之后将不再主动发起通知**

​    **return** "SUCCESS";

}**else** {

​    **//****TODO 回调收到的数据不安全或者不正确，在这里执行客户自己的处理逻辑**

​    **//商户收到不正确的通知后，返回“FAIL”字符串，平台将在10分钟内，通知3次，之后将不再主动发起通知**

​    **return** "FAIL";

}

}

}





说明：这是一个可以直接使用的独立controller类。解密成功即可对回调数据进行处理，商户确认回调数据正确后需要回复大写的”SUCCESS”。钱包服务收到SUCCESS回复后视为成功回调，后续不再会发起回调，商户需要在确认自己已经成功处理了回调数据后再做回应。如果还需要得到某个订单的确切状态就需要调用***\*《查询充值订单示例》\****。



### ***\*充值订单回调失败示例：\****

代码：见《充值订单回调成功示例》一节，也就是上一节。



说明：这是一个可以直接使用的独立controller类。验签失败即可视为回调失败，应该立即回复大写的”FAIL”。如果验签成功则可以进一步对回调数据进行处理，商户发现回调数据有误，比如订单号不存在等即可视为回调失败，并做好相应的记录，然后回复大写的”FAIL”。钱包服务收到”FAIL”回复后视为回调失败，会启动补充通知的流程：平台将在10分钟内，通知3次，之后将不再主动发起通知。如果还需要得到某个订单的确切状态就需要调用***\*《查询充值订单示例》\****。



# ***\*SDK集成使用建议\****

## ***\*JDK版本\****

推荐使用JDK1.8。实际可以在JDK17都能正确运行。



## ***\*特性介绍\****

为一方面满足商户的个性化需求，一方面也可以大大减少商户的对接成本，本SDK目前以源代码形式发布。我们做了如下处理：

1、公共参数配置式（通过配置文件和注解自动收集配置），这样做的好处至少有三点

第一点：商户在接入的时候可以集中管理接口配置，一目了然，如果分散到各个接口的实现当中就非常不便于维护。



第二点：在业务代码中用户可以把开发精力集中在业务参数上，而不是相对固定的公共参数，因为这些公共参数大部分都是用于选择和透传，不参与实际业务的处理。具体的配置内容参见《***\*公共参数配置说明\****》



第三点：部署灵活，由于配置和源代码是分离的，在外部参数发生变化，比如由于安全问题导致密码有泄露的可能性，双发在一定时间后可能会更换密码，以保持更高的安全性，这个时候不需要进行重新编译并发布，更新配置即可。如果商户服务由于客观原因导致服务迁移，域名发生变化，回调URL也随之发生了变化的情况下，也不需要重复编译并发布，更改配置即可。



2、合理化封装，我们将发起请求和返回数据的数据结构已经定义了实体类，具体放置在dto目录下。分为notify、request、response三个分类，分别对应平台回调传输的数据结构、接口请求参数封装、接口返回数据封装。这部分数据之所以封装好是因为这些数据当中的字段都是平台约定，不可更改，为了避免每个商户通过自定义的方式来重新实现而带来可能的疏漏而让接口对接出现一些人为的不可控因素。商户可以直接使用dto目录下的实体类定义。



3、对接测试本地接口化：我们针对目前开放的9个接口在SDK中都提供了对应的本地测试接口，具体放置在com.universe.wallet.apilocaltest包路径下。其测试接口的参数和实际接口参数是一致的，用户可以直接通过我们提供的API文档工具（SDK项目在本地启动后在浏览器访问[http://localhost:8000/doc.html#/home就能看到我们提供的测试接口），这样商户可以在SDK集成到自己项目前在本项目中直接测试商户的接口参数配置是否正确以及快速验证接口的业务参数的使用是否有什么什么问题。](#/home就能看到我们提供的测试接口），这样商户可以在SDK集成到自己项目前在本项目中直接测试商户的接口参数配置是否正确以及快速验证接口的业务参数的使用是否有什么什么问题。)



4、最小化的代码侵入性集成SDK。

商户在集成的时候可以通过以下步骤快速集成：



## ***\*集成步骤\****

***\*（2-7步实质上是一步：文件整体拷贝）\****

***\*1、拷贝SDK项目application.yml中的钱包配置到自己的项目配置文件中，配置的实际参数值需要根据商户自己的实际进行配置值修改；\****



***\*2、将com.universe.wallet.configuration包中的配置类全部放置到自己的项目中；\****



***\*3、将com.universe.wallet.encryptor拷贝到自己项目；\****



***\*4、将com.universe.wallet.dto包下的所有子包拷贝到自己项目\****



***\*5、将com.universe.wallet.util工具包拷贝到自己的项目中\****



***\*6、将com.universe.wallet.openapi.WalletService拷贝到项目中；\****



***\*7、 com.universe.wallet.controller.notify包是回调接口的编写示例，可直接拷贝到自己的项目中进行二次开发，加入自己的回调处理代码；\****



***\*8、调用接口：参考com.universe.wallet.apilocaltest下的测试接口就可轻松使用封装好的工具方法发起接口调用，只需关注业务本身。\****



## ***\*强烈建议\****

***\*如果不是特殊的原因，推荐将本项目的除项目配置文件外的源代码直接一起复制到自己项目中的一个独立路径下面，比如walletApi，这样整合最快，以上7步就简化成3步（2-7简化成一次全量拷贝行为），以后有版本更新也非常方便升级。今后发布了jar的SDK也可以最快的速度进行整合替换。\****



## ***\*本地测试注意事项\****

***\*com.universe.wallet.apilocaltest是通过接口方式可以测试9个接口，接口可以通过本地的API文档：http://localhost:8000/doc.html#/home 来方便的调试和参考，实际业务使用的时候不需要用这些接口，商户在对SDK进行单独对接测试完成后应该删除这些本地化测试接口。商户实际只需要使用com.universe.demo.openapi.WalletService的服务方法在自己的业务当发起接口调用中。\****

***\*所以com.universe.wallet.apilocaltest类在实际客户的生产项目中不需要集成。\****





 