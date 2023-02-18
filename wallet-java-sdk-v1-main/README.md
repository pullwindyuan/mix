
## 项目介绍
1、本项目为一个钱包为第三方提供的提现和充值功能的开发接口对接示例项目，也可以直接集成到实际项目中。
2、本项目的特点式高度封装，尤其式签名和验签部分，包括网络请求都使用的OkHttp进行封装，通过WalletRequestUtil工具类中的walletRequest可以直接
3、发起接口请求，会自动进行签名和参数封装。通过matches可以对回调接口的请求数据进行验签。
4、项目的对接的关键非业务参数均通过application.yml中的如下的配置来定义，代码中不需要定义。
### 钱包API配置
wallet:
###由钱包分配
merchantMemberNo: account1
###如有升级请同步
version: v1
###钱包接口主地址，由钱包发放该地址
baseUrl: https://go-wallet-partner.cg6.co/
openApi:
withdraw:
###由钱包分配，千万不要配置错误，充值和提现是不同的appId-appKey对，充值的appId是以TX开头的
appId: TXTJ2FY9NRVMKJC3
###由钱包分配，千万不要配置错误，充值和提现是不同的appId-appKey对
appKey: TKNi3HZbX5l#Pt6#rN0mUAa9)aO#Zy4f
### 创建提现订单
create: /match/withdrawOrders/create
### 审核提现订单
approval: /match/withdrawOrders/approval
### 查询提现订单
query: /match/withdrawOrders/query
### 提现回调地址,请配置成自己的回调地址，这里配的是demo中的演示示例的回调地址，供参考
notifyUrl: http://localhost:8000/api/notify/withdraw
deposit:
###由钱包分配，千万不要配置错误，充值和提现是不同的appId-appKey对，充值的appId是以CZ开头的
appId: CZYVXIV30UF125UU
###由钱包分配，千万不要配置错误，充值和提现是不同的appId-appKey对
appKey: CK6Z*THfs)7Yhm#FmecMrWZTOtuEfs6d
### 创建充值订单
create: /match/depositOrders/create
### 查询充值订单
query: /match/depositOrders/query
### 充值回调地址,请配置成自己的回调地址，这里配的是demo中的演示示例的回调地址，供参考
notifyUrl: http://localhost:8000/api/notify/deposit

以上配置信息会自动装载到WalletApiProperties配置类中，在需要使用的地方自定引入即可：
@Autowired
private WalletApiProperties walletApiProperties;

## 集成步骤
1、在application.yml（或者自己项目中的实际配置文件内中加入配置，配置的具体值需要根据本商户的实际分配的配置值进行修改）；

2、将com.universe.demo.configruration包中的配置信息全部放置到自己的项目中（通过IDEA内部拷贝能够自动修改为目标项目的包名路径）

3、将com.universe.demo.configruration包下的文件拷贝到自己项目

4、将com.universe.demo.util工具包拷贝到自己的项目中

5、将com.universe.demo.openApi.WalletService拷贝到项目中（com.universe.demo.openApi.forDoc里面的代码是是为了方便我方人员书写文档，客户可以不使用）；

6、 com.universe.demo.controller.notify包路径下是回调接口的编写示例，里面有验签的操作；

## 强烈建议
如果不是特殊的原因，非常建议将本项目的除项目配置文件外的源代码直接全部放置到自己项目中的一个独立路径下面，这样整合最快，以后有版本更新也风场方便升级


## 本地测试注意
com.universe.demo.apiLocalTest是通过接口方式可以测试5个接口，接口可以通过本地的API文档：http://localhost:8000/doc.html#/home
    来方便的调试，输入的参数结构在每个接口方法的注释中都有，实际使用的时候不需要用接口，但可以利用里面的方法，其返回值都是封装后的，方便使用，客户也可
    以直接使用com.universe.demo.openApi.WalletService的服务方法应用到自己的业务当中。本类在实际客户的项目中不需要出现。


## 注意
目前com.universe.demo.enctyptor包里的类本次结成不需要关注，这是今后可能使用的兑成加密需要用的，本次还未启用