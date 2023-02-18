 package org.hlpay.base.card.common;

 import com.alibaba.fastjson.JSONObject;
 import java.io.BufferedReader;
 import java.io.InputStreamReader;
 import java.lang.management.ManagementFactory;
 import java.lang.management.RuntimeMXBean;
 import java.lang.reflect.Field;
 import java.net.HttpURLConnection;
 import java.net.URL;
 import java.text.SimpleDateFormat;
 import java.util.Calendar;
 import java.util.List;
 import java.util.Map;
 import java.util.Random;
 import javax.annotation.Resource;
 import javax.servlet.http.HttpServletResponse;
 import org.hlpay.base.model.SaSettleConfig;
 import org.hlpay.common.util.HLPayUtil;
 import org.hlpay.common.util.PayDigestUtil;
 import org.springframework.stereotype.Component;






 @Component
 public class CommonUtils<T>
 {
   @Resource
   private HttpServletResponse response;

   public SimpleDateFormat getSimpleDateFormat(String format) {
     return new SimpleDateFormat(format);
   }






   public String randomCard() {
     return "CD" + String.valueOf(System.currentTimeMillis()).substring(8);
   }





   public String randomDeal() {
     RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
     return "DL" + String.valueOf((System.currentTimeMillis() + Long.valueOf((new Random()).nextInt(999)).longValue()) + runtimeMXBean
         .getName().substring(0, runtimeMXBean.getName().indexOf("@")));
   }





   public String randomBill() {
     RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
     return "BL" + String.valueOf((System.currentTimeMillis() + Long.valueOf((new Random()).nextInt(999)).longValue()) + runtimeMXBean
         .getName().substring(0, runtimeMXBean.getName().indexOf("@")));
   }





   public String randomFlow(String scoreFlowDirection) {
     RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
     return scoreFlowDirection + String.valueOf((System.currentTimeMillis() + Long.valueOf((new Random()).nextInt(999)).longValue()) + runtimeMXBean
         .getName().substring(0, runtimeMXBean.getName().indexOf("@")));
   }






   public boolean getSignResult(Object obj) {
     JSONObject paramMap = new JSONObject();
     paramMap.put("reqData", obj.toString());
     String reqSign = PayDigestUtil.getSign((Map)paramMap, "qwertyuiopasdfghjklzxcvbnm");
     paramMap.put("sign", reqSign);
     String reqData = paramMap.toJSONString();
     JSONObject jo = JSONObject.parseObject(reqData);
     boolean sign = HLPayUtil.verifyPaySign((Map)jo, "qwertyuiopasdfghjklzxcvbnm");
     return sign;
   }





   public String checkIsNull(Object obj) {
     Class<?> userCla = obj.getClass();
     Field[] fs = userCla.getDeclaredFields();
     for (int i = 0; i < fs.length; i++) {
       Field f = fs[i];
       f.setAccessible(true);
       Object val = new Object();
       try {
         val = f.get(obj);
         if (val == null && !ignore(f.getName())) {
           return f.getName();
         }
       } catch (IllegalArgumentException e) {
         e.printStackTrace();
       } catch (IllegalAccessException e) {
         e.printStackTrace();
       }
     }

     return "notNull";
   }





   public boolean isNull(String id) {
     if (id == null || id.equals(" ")) {
       return true;
     }
     return false;
   }

   public String getResult(String getUrl) {
     StringBuilder sb = new StringBuilder();
     try {
       URL url = new URL(getUrl);
       HttpURLConnection con = (HttpURLConnection)url.openConnection();
       con.connect();
       BufferedReader bf = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));
       String line;
       while ((line = bf.readLine()) != null) {
         sb.append(line);
       }
       bf.close();
       con.disconnect();
       return sb.toString();
     } catch (Exception e) {
       e.printStackTrace();

       return sb.toString();
     }
   }
   public JSONObject parseJson(String jsonString, String key) {
     JSONObject json = JSONObject.parseObject(jsonString);
     if (key == null || " ".equals(key)) {
       return json;
     }
     json = JSONObject.parseObject(json.getString(key));
     return json;
   }










   public PageBean getInstance(int start, int end, int total, List<T> list) {
     PageBean pageBean = new PageBean();
     Page<T> page = new Page<>(start, end, total, list);
     pageBean.setTotal(page.getTotal());
     pageBean.setList(list);
     return pageBean;
   }























































   public void importExcel(List list, String excelName) {}























































   public String getDate(List<SaSettleConfig> list) {
     String str = "";
     Calendar calendar = Calendar.getInstance();
     int year = calendar.get(1);
     int month = calendar.get(2) + 1;
     int date = calendar.get(5);
     SaSettleConfig saSettleConfig = list.get(0);
     if (saSettleConfig.getLiquidateMode().charValue() == '1') {
       int day = saSettleConfig.getLiquidateDate().intValue();
       if (date < day) {
         if (month == 12) {
           str = (year + 1) + "/" + '\001' + "/" + day;
         } else {
           str = year + "/" + month + "/" + day;
         }

       } else if (month == 12) {
         str = (year + 1) + "/" + '\001' + "/" + day;
       } else {
         str = year + "/" + (month + 1) + "/" + day;
       }
     }

     return str;
   }






   public boolean ignore(String name) {
     if ("isDelete".equalsIgnoreCase(name)) {
       return true;
     }

     if ("flag".equalsIgnoreCase(name)) {
       return true;
     }

     if ("status".equalsIgnoreCase(name)) {
       return true;
     }

     if ("userId".equalsIgnoreCase(name)) {
       return true;
     }

     if ("cardNumber".equalsIgnoreCase(name)) {
       return true;
     }

     if ("dealRecordId".equalsIgnoreCase(name)) {
       return true;
     }

     if ("calculateTime".equalsIgnoreCase(name)) {
       return true;
     }

     if ("dealCreateTime".equalsIgnoreCase(name)) {
       return true;
     }

     if ("dealEndTime".equalsIgnoreCase(name)) {
       return true;
     }

     if ("dealOrderId".equalsIgnoreCase(name)) {
       return true;
     }

     if ("scoreFlowId".equalsIgnoreCase(name)) {
       return true;
     }

     if ("scoreFlowNumber".equalsIgnoreCase(name)) {
       return true;
     }

     return false;
   }
 }





