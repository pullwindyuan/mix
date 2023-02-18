<!DOCTYPE HTML>
<html>
<head>
    <meta charset="htf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>微信授权</title>
</head>
<body>
<#if (userInfoObj != null)>
<script>
document.cookie="token=${userInfoObj.get("token")};openid=${userInfoObj.get("openid")};domain=bibi321.com;path=/";
var referLink = document.createElement('a');
referLink.href = "http://m.bibi321.com/payTest.html";
document.body.appendChild(referLink);
referLink.click();
</script>
<#else>
<script>
var referLink = document.createElement('a');
referLink.href = "http://m.bibi321.com/payTest.html";
document.body.appendChild(referLink);
referLink.click();
</script>
</#if>
</body>
</html>