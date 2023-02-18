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
    <script type="text/javascript" src='js/jquery.base64.js'></script>
</head>
<body>
<div id="amount">¥ 0.01</div>
<div id="info">
    <table>
        <tr>
            <td>购买商品</td>
            <td class="fr">好东西</td>
        </tr>
        <tr>
            <td>收款方</td>
            <td class="fr">深圳航旅投资有限公司</td>
        </tr>
    </table>
</div>

<script>
    document.domain = "bibi321.com";

    $.ajax({
        type:"GET",
        url:"http://pv.sohu.com/cityjson?ie=utf-8",
        data:{},
        success:function(data){
            alert(data);
            var result = JSON.parse(data);
            var ip = result.cip;

            localStorage.setItem("clientIp",ip);

            var redirectUrl = "http://shop.bibi321.com/toWxPay.html?amount=10&redirect=http://m.bibi321.com&clientIp=" + ip
            var redirectUrlBase64 = $.base64('encode', redirectUrl);
            var referLink = document.createElement('a');
            //referLink.href = "http://m.bibi321.com/payTest.html";
            //alert(redirectUrlBase64);
            //alert($.base64().decode(redirectUrlBase64));
            if(isWeiXin()){
                referLink.href = "http://uc.bibi321.com/getOpenId?redirectUrl=" + redirectUrlBase64;
            }else{
                referLink.href = redirectUrl;
            }

            //alert(referLink.href);
            document.body.appendChild(referLink);
            referLink.click();
        },
        error:function(data){
            var result = JSON.parse(data);
            $('.notify').text("获取IP失败")
        }
    })
    //判断是否微信登陆
    function isWeiXin() {
        var ua = window.navigator.userAgent.toLowerCase();
        console.log(ua);//mozilla/5.0 (iphone; cpu iphone os 9_1 like mac os x) applewebkit/601.1.46 (khtml, like gecko)version/9.0 mobile/13b143 safari/601.1
        if (ua.match(/MicroMessenger/i) == 'micromessenger') {
            return true;
        } else {
            return false;
        }
    }

    function GetQueryString(name)
    {
        var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
        var r = window.location.search.substr(1).match(reg);
        if(r!=null)return  unescape(r[2]); return null;
    }
</script>
</body>
</html>