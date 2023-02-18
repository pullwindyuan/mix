 package com.futuremap.custom.util;

 import java.util.Iterator;
 import java.util.Map;
 import java.util.Random;
 import java.util.concurrent.ConcurrentHashMap;


 public class RandomStrUtils
 {
   private static Object lock = new Object();

   private static RandomStrUtils instance;

   private Map<String, Long> randomStrMap = new ConcurrentHashMap<>();

   private static final String[] BASE_STRING = new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z" };






   private static final String[] BASE_WITH_UPPERCASE_STRING = new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" };








   private static final String[] BASE_UPPERCASE_STRING = new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" };





   private static final String[] LOWERCASE_STRING = new String[] { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z" };





   private static final String[] UPPERCASE_STRING = new String[] { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" };




   private static final String[] BASE_NUM_STRING = new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };



   private static final int RANDOM_STRING_LENGTH = 20;




   public static RandomStrUtils getInstance() {
     synchronized (lock) {
       if (instance == null) {
         instance = new RandomStrUtils();
       }
     }
     return instance;
   }

   public String getRandomString() {
     return getRandomString(20);
   }

   public String getRandomString(int len) {
     Long nowTime = Long.valueOf(System.currentTimeMillis());
     String randomStr = null;

     synchronized (lock) {

       randomStr = createRandomString(len, nowTime);


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
     int length = BASE_WITH_UPPERCASE_STRING.length;
     String randomString = "";
     for (int i = 0; i < length; i++) {
       randomString = randomString + BASE_WITH_UPPERCASE_STRING[random.nextInt(length)];
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

   public String getLowercaseNumRandomString(int len) {
     Long nowTime = Long.valueOf(System.currentTimeMillis());
     String randomStr = null;

     synchronized (lock) {

       randomStr = createLowercaseRandomString(len, nowTime);


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

   private String createLowercaseRandomString(int len, Long nowTime) {
     Random random = new Random();
     int length = BASE_STRING.length;
     String randomString = "";
     for (int i = 0; i < length; i++) {
       randomString = randomString + BASE_STRING[random.nextInt(length)];
     }
     random = new Random(System.currentTimeMillis());
     int num = random.nextInt(len - 2) + 1;
     int low = len - num;


     String resultStr = "";

     for (int j = 0; j < len; ) {
       char temp = randomString.charAt(random.nextInt(randomString.length() - 1));
       if (temp >= '0' && temp <= '9' && num > 0) {
         resultStr = resultStr + temp;
         num--;
         j++; continue;
       }  if (temp >= 'a' && temp <= 'z' && low > 0) {
         resultStr = resultStr + temp;
         low--;
         j++;
       }
     }



     Long randomStrCreateTime = this.randomStrMap.get(resultStr);
     if (randomStrCreateTime != null && nowTime
       .longValue() - randomStrCreateTime.longValue() < 5000L) {
       resultStr = createLowercaseRandomString(len, nowTime);
     }
     this.randomStrMap.put(resultStr, nowTime);
     return resultStr;
   }

   public String getUppercaseNumRandomString(int len) {
     Long nowTime = Long.valueOf(System.currentTimeMillis());
     String randomStr = null;

     synchronized (lock) {

       randomStr = createUppercaseRandomString(len, nowTime);


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

   private String createUppercaseRandomString(int len, Long nowTime) {
     Random random = new Random();
     int length = BASE_UPPERCASE_STRING.length;
     String randomString = "";
     for (int i = 0; i < length; i++) {
       randomString = randomString + BASE_UPPERCASE_STRING[random.nextInt(length)];
     }
     random = new Random(System.currentTimeMillis());
     int num = random.nextInt(len - 2) + 1;
     int upper = len - num;

     String resultStr = "";

     for (int j = 0; j < len; ) {
       char temp = randomString.charAt(random.nextInt(randomString.length() - 1));
       if (temp >= '0' && temp <= '9' && num > 0) {
         resultStr = resultStr + temp;
         num--;
         j++; continue;
       }  if (temp >= 'A' && temp <= 'Z' && upper > 0) {
         resultStr = resultStr + temp;
         upper--;
         j++;
       }
     }



     Long randomStrCreateTime = this.randomStrMap.get(resultStr);
     if (randomStrCreateTime != null && nowTime
       .longValue() - randomStrCreateTime.longValue() < 5000L) {
       resultStr = createUppercaseRandomString(len, nowTime);
     }
     this.randomStrMap.put(resultStr, nowTime);
     return resultStr;
   }

   public String getMixRandomString(int len) {
     Long nowTime = Long.valueOf(System.currentTimeMillis());
     String randomStr = null;

     synchronized (lock) {

       randomStr = createMixUppercaseRandomString(len, nowTime);


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

   private String createMixUppercaseRandomString(int len, Long nowTime) {
     Random random = new Random();
     int length = BASE_WITH_UPPERCASE_STRING.length;
     String randomString = "";
     for (int i = 0; i < length; i++) {
       randomString = randomString + BASE_WITH_UPPERCASE_STRING[random.nextInt(length)];
     }
     random = new Random(System.currentTimeMillis());
     int num = random.nextInt(len - 3) + 1;
     int upper = random.nextInt(len - 2 - num) + 1;
     int low = len - num - upper;


     String resultStr = "";

     for (int j = 0; j < len; ) {
       char temp = randomString.charAt(random.nextInt(randomString.length() - 1));
       if (temp >= '0' && temp <= '9' && num > 0) {
         resultStr = resultStr + temp;
         num--;
         j++; continue;
       }  if (temp >= 'a' && temp <= 'z' && low > 0) {
         resultStr = resultStr + temp;
         low--;
         j++; continue;
       }  if (temp >= 'A' && temp <= 'Z' && upper > 0) {
         resultStr = resultStr + temp;
         upper--;
         j++;
       }
     }



     Long randomStrCreateTime = this.randomStrMap.get(resultStr);
     if (randomStrCreateTime != null && nowTime
       .longValue() - randomStrCreateTime.longValue() < 5000L) {
       resultStr = createMixUppercaseRandomString(len, nowTime);
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

   public static void main(String[] args) throws Exception {
     String result = getInstance().getMixRandomString(6);
     System.out.println(result);
     result = getInstance().getRandomString(6);
     System.out.println(result);
     result = getInstance().getUppercaseNumRandomString(6);
     System.out.println(result);
     result = getInstance().getLowercaseNumRandomString(6);
     System.out.println(result);
   }
 }

