<!DOCTYPE HTML>
<html>
<head>
    <meta charset="htf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>卡包支付测试页面</title>
</head>
<body>

<script>
var referLink = document.createElement('a');
referLink.href = "${redirect}";
document.body.appendChild(referLink);
referLink.click();
</script>
</body>
</html>