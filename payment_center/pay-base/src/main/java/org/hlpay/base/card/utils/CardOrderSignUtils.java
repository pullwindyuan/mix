 package org.hlpay.base.card.utils;

 import com.alibaba.fastjson.JSONObject;
 import java.util.Map;
 import org.hlpay.common.util.HLPayUtil;
 import org.hlpay.common.util.MyLog;
 import org.hlpay.common.util.PayDigestUtil;
 import org.springframework.stereotype.Component;






 @Component
 public class CardOrderSignUtils
 {
   private static final MyLog _log = MyLog.getLog(CardOrderSignUtils.class);

   private String reqKey;


   public String setSign(JSONObject jo) {
     String reqSign = PayDigestUtil.getSign((Map)jo, this.reqKey);
     _log.info("reqSign生成签名数据：" + reqSign, new Object[0]);
     jo.put("sign", reqSign);
     _log.info("jo数据：" + jo.toJSONString(), new Object[0]);
     return jo.toJSONString();
   }

   public boolean getSign(JSONObject jo) {
     boolean flag = HLPayUtil.verifyPaySign((Map)jo, this.reqKey);
     _log.info("flag数据：" + flag, new Object[0]);
     if (flag) {
       return true;
     }
     return false;
   }

   public static void main(String[] args) {
     JSONObject jo = new JSONObject();
     jo.put("mchId", "1001");
     jo.put("mchOrderNo", "5645121");
     jo.put("payOrderId", "G12055465247");
     jo.put("status", "2");
     jo.put("reqKey", "asdfasdfasdfasd");

     CardOrderSignUtils cosu = new CardOrderSignUtils();
     cosu.setSign(jo);
     cosu.getSign(jo);
   }
 }
