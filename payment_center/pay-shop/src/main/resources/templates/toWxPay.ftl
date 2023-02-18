<!DOCTYPE HTML>
<html>
<head>
    <meta charset="htf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>航旅支付中心</title>
    <style>
        body{font-family: 'Microsoft YaHei';}
        #amount,#error{height: 80px; line-height: 80px; text-align: center; color: #f00; font-size: 30px; font-weight: bold;}
        #error{font-size: 20px;}
        #info{padding: 0 10px; font-size: 12px;}
        table{width: 100%; border-collapse: collapse;}
        tr{border: 1px solid #ddd;}
        td{padding: 10px;}
        .fr{text-align: right; font-weight: bold;}
    </style>
    <script src="//cdn.jsdelivr.net/jquery/1.12.1/jquery.min.js"></script>
</head>
<body>
<script>
<#if (client == 'wx')>
    function onBridgeReady(){
        WeixinJSBridge.invoke(
                'getBrandWCPayRequest', {
                    "appId" : "${orderMap.payParams.appId}",     //公众号名称，由商户传入
                    "timeStamp" : "${orderMap.payParams.timeStamp}",         //时间戳，自1970年以来的秒数
                    "nonceStr" : "${orderMap.payParams.nonceStr}", //随机串
                    "package" : "${orderMap.payParams.package}",
                    "signType" : "${orderMap.payParams.signType}",         //微信签名方式：
                    "paySign" : "${orderMap.payParams.paySign}" //微信签名,paySign 采用统一的微信支付 Sign 签名生成方法，注意这里 appId 也要参与签名，appId 与 config 中传入的 appId 一致，即最后参与签名的参数有appId, timeStamp, nonceStr, package, signType。
                },
                function(res) {
                    if(res.err_msg == "get_brand_wcpay_request:ok" ) {     // 使用以上方式判断前端返回,微信团队郑重提示：res.err_msg将在用户支付成功后返回    ok，但并不保证它绝对可靠。
                        alert('支付成功！');
                    } else {
                        alert('支付失败：' + res.err_msg);
                    }
                    WeixinJSBridge.call('closeWindow');
                }
        );
    }
    if (typeof WeixinJSBridge == "undefined") {
        if ( document.addEventListener ) {
            document.addEventListener('WeixinJSBridgeReady', onBridgeReady, false);
        } else if (document.attachEvent) {
            document.attachEvent('WeixinJSBridgeReady', onBridgeReady);
            document.attachEvent('onWeixinJSBridgeReady', onBridgeReady);
        }
    } else {
        onBridgeReady();
    }
//微信公众号支付返回数据格式
//
//    {
//        "payOrderId":"TBBTO20180609094546001",
//            "sign":"3A3E150A1238E3D35C7F885C6E8D620A",
//            "resCode":"SUCCESS",
//            "payParams":{
//        "timeStamp":"1528508749",
//                "package":"prepay_id=wx090945493374747be83213fe1722922152",
//                "paySign":"F2C6BFCF318E413FB807250ABA08A267",
//                "appId":"wxf13d13b2ab2a3dd9",
//                "signType":"MD5",
//                "nonceStr":"1528508749386"
//    },
//        "prepayId":"wx090945493374747be83213fe1722922152",
//            "retCode":"SUCCESS",
//            "retMsg":""
//    }
<#else>
    var referLink = document.createElement('a');
    referLink.href = "${payUrl}";
    document.body.appendChild(referLink);
    referLink.click();
</#if>
</script>
</body>
</html>