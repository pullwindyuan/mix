银联全渠道系统加密与验签测试证书将于2017年12月4日过期，为了确保测试能够顺利开展，本周五（12月1日）18:00至24:00将对系统加密与验签证书维护。
更换证书名称：
加密证书：acp_test_enc.cer
签名证书：acp_test_verify_sign.cer

操作方法：替换开发包中对应证书，路径：xxxxxx开发包\Java Version SDK (通用版)\ACPSample_xxxxxx\src\assets\测试环境证书

请于12月1日晚正式启用，提前启用测试会报错。


##测试账号

卡号 ：
6216261000000000018

证件号 ：
341126197709218366

姓名：全渠道
控件短信验证码 ：123456

http://localhost:8088/unionPay/pay.html?amount=1&channelId=UNION_PC

测试浏览器360兼容模式，个别浏览器可能无法支付。