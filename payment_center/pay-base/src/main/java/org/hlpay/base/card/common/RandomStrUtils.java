 package org.hlpay.base.card.common;

 import java.util.Iterator;
 import java.util.Map;
 import java.util.Random;
 import java.util.concurrent.ConcurrentHashMap;






 public class RandomStrUtils
 {
   private static Object lock = new Object();

   private static RandomStrUtils instance;

   private Map<String, Long> randomStrMap = new ConcurrentHashMap<>();

   private static final String[] BASE_STRING = new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" };








   private static final String[] BASE_NUM_STRING = new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" };






   private static final int RANDOM_STRING_LENGTH = 6;






   public static RandomStrUtils getInstance() {
     synchronized (lock) {
       if (instance == null) {
         instance = new RandomStrUtils();
       }
     }
     return instance;
   }

   public String getRandomString() {
     Long nowTime = Long.valueOf(System.currentTimeMillis());
     String randomStr = null;

     synchronized (lock) {

       randomStr = createRandomString(6, nowTime);


       Iterator<Map.Entry<String, Long>> it = this.randomStrMap.entrySet().iterator();
       while (it.hasNext()) {
         Map.Entry<String, Long> entry = it.next();
         Long value = entry.getValue();
         if (nowTime.longValue() - value.longValue() > 5000L) {
           it.remove();
         }
       }
     }

     return randomStr;
   }

   private String createRandomString(int len, Long nowTime) {
     Random random = new Random();
     int length = BASE_STRING.length;
     String randomString = "";
     for (int i = 0; i < length; i++) {
       randomString = randomString + BASE_STRING[random.nextInt(length)];
     }
     random = new Random(System.currentTimeMillis());
     String resultStr = "";
     for (int j = 0; j < len; j++) {
       resultStr = resultStr + randomString.charAt(random.nextInt(randomString.length() - 1));
     }


     Long randomStrCreateTime = this.randomStrMap.get(resultStr);
     if (randomStrCreateTime != null && nowTime
       .longValue() - randomStrCreateTime.longValue() < 5000L) {
       resultStr = createRandomString(len, nowTime);
     }
     this.randomStrMap.put(resultStr, nowTime);
     return resultStr;
   }

   public String createRandomNumString(int len) {
     Long nowTime = Long.valueOf(System.currentTimeMillis());
     Random random = new Random();
     int length = BASE_NUM_STRING.length;
     String randomString = "";
     for (int i = 0; i < length; i++) {
       randomString = randomString + BASE_NUM_STRING[random.nextInt(length)];
     }
     random = new Random(System.currentTimeMillis());
     String resultStr = "";
     for (int j = 0; j < len; j++) {
       resultStr = resultStr + randomString.charAt(random.nextInt(randomString.length() - 1));
     }


     Long randomStrCreateTime = this.randomStrMap.get(resultStr);
     if (randomStrCreateTime != null && nowTime
       .longValue() - randomStrCreateTime.longValue() < 5000L) {
       resultStr = createRandomString(len, nowTime);
     }
     this.randomStrMap.put(resultStr, nowTime);
     return resultStr;
   }

   public static void main(String[] args) {
     System.out.println(getInstance().getRandomString());
     System.out.println(getInstance().createRandomNumString(16));
   }
 }

