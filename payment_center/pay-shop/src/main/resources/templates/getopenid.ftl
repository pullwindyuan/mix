<!DOCTYPE HTML>
<html>
<head>
    <meta charset="htf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>航旅微信支付测试</title>
    <script src="//cdn.jsdelivr.net/jquery/1.12.1/jquery.min.js"></script>
</head>
<body>
</div>
<script>
    var referLink = document.createElement('a');
    //referLink.href = "http://m.bibi321.com/paytest.html";
    referLink.href = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxf13d13b2ab2a3dd9%s&redirect_uri=http%3a%2f%2fm.bibi321.com%2fpaytest.html&response_type=code&scope=snsapi_base&state=1#wechat_redirect";
    document.body.appendChild(referLink);
    referLink.click();
</script>
</body>
</html>