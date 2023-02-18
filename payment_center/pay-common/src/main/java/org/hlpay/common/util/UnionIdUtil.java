 package org.hlpay.common.util;

 import java.math.BigDecimal;
 import java.math.RoundingMode;
 import org.apache.commons.lang3.StringUtils;

 public class UnionIdUtil
 {
   public static void main(String[] args) {
     BigDecimal ID = new BigDecimal("9999999999999999999999999999999999999999999999999999999999999999");
     String IDStr = "58798756437287908735-9875643216758-764342698756432123453678965";
     int ALIGN_TO_LEFT = 0;
     int ALIGN_TO_RIGHT = 1;
     int ALIGN = 1;
     int TOTAL_BITS = 128;
     int LEVEL_BITS = 16;
     int MAX_LEVEL = TOTAL_BITS / LEVEL_BITS;

     int[][] BITS = { { 60, 12 }, { 48, 12 }, { 36, 12 }, { 24, 12 }, { 12, 12 }, { 0, 12 } };


     int FIRST_LEVEL_BITS = LEVEL_BITS + TOTAL_BITS % MAX_LEVEL;

     getAbsoluteRangeFromIdByLevel(ID, TOTAL_BITS, LEVEL_BITS, 5);
     getRelativeRangeByLevel(TOTAL_BITS, LEVEL_BITS, 1);
     getLevelAndIndexInfoFromId(ID, TOTAL_BITS, LEVEL_BITS);
     getLevelAndIndexInfoFromId(new BigDecimal("568945297525463215847412368547856321475369514789632145858562478965348594"), BITS);


     getIdBaseOnParentIdByIndex(new BigDecimal("63958453625895870000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000"), 3,


         Long.valueOf(7598686L), TOTAL_BITS, LEVEL_BITS);


     getIdBaseOnParentIdByIndex(new BigDecimal("63958453625895870000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000"), 3,


         Long.valueOf(75L), TOTAL_BITS, LEVEL_BITS);


     getIdBaseOnParentIdByIndex(new BigDecimal("63958453625895870000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000"), 2,


         Long.valueOf(675878989L), BITS);

     getParentId(Long.valueOf(287183010000000000L), 1, TOTAL_BITS, LEVEL_BITS);
     getParentId(Long.valueOf(287183010000000000L), 1, BITS);

     getIdInfoFromUnionId(IDStr);
     getUnionIdBaseOnParentUnionIdByIndex(IDStr, "7598686");
     getUnionIdBaseOnParentUnionIdByIndex(IDStr, "75");
     getUnionIdBaseOnParentUnionIdByIndex(IDStr, "675878989");
     getParentId(IDStr, 1);
     getParentUnionId(IDStr, 2);
   }


   public static BigDecimal[][] getAbsoluteRangeFromIdByLevel(BigDecimal id, int maxBits, int levelBits, int currLevel) {
     int TOTAL_BITS = maxBits;
     int LEVEL_BITS = levelBits;
     int[][] bits = getBits(TOTAL_BITS, LEVEL_BITS);
     return getAbsoluteRangeFromIdByLevel(id, bits, currLevel);
   }


   public static BigDecimal[][] getAbsoluteRangeFromIdByLevel(BigDecimal input, int[][] bits, int currLevel) {
     BigDecimal[][] outPut = new BigDecimal[2][2];

     int CURR_LEVEL = currLevel;
     if (currLevel >= bits.length) {
       return null;
     }

     int POW = bits[CURR_LEVEL][0];

     BigDecimal scale = (new BigDecimal(10)).pow(POW);
     BigDecimal[] result = input.divideAndRemainder(scale);
     BigDecimal min = result[0].multiply(scale);
     outPut[0][0] = min;


     BigDecimal max = min.add((new BigDecimal(10)).pow(POW));
     outPut[1][0] = max;


     outPut[0][1] = new BigDecimal("1");

     outPut[1][1] = (new BigDecimal(10)).pow(bits[CURR_LEVEL][1]);

     return outPut;
   }


   public static BigDecimal[][] getRelativeRangeByLevel(int maxBits, int levelBits, int currLevel) {
     int TOTAL_BITS = maxBits;
     int LEVEL_BITS = levelBits;
     int[][] bits = getBits(TOTAL_BITS, LEVEL_BITS);
     return getRelativeRangeByLevel(bits, currLevel);
   }


   public static BigDecimal[][] getRelativeRangeByLevel(int[][] bits, int currLevel) {
     BigDecimal[][] outPut = new BigDecimal[2][2];









     int CURR_LEVEL = currLevel;
     if (currLevel >= bits.length) {
       return null;
     }

     int POW = bits[CURR_LEVEL][0];

     BigDecimal bigDecimal = (new BigDecimal(10)).pow(POW).stripTrailingZeros();
     BigDecimal min = bigDecimal;

     outPut[0][0] = min;
     BigDecimal max = min.multiply((new BigDecimal(10)).pow(bits[CURR_LEVEL][1]));


     outPut[1][0] = max;
     outPut[0][1] = new BigDecimal("1");

     outPut[1][1] = (new BigDecimal(10)).pow(bits[CURR_LEVEL][1]);

     return outPut;
   }


   public static BigDecimal[][] getLevelAndIndexInfoFromId(BigDecimal inputId, int maxBits, int levelBits) {
     int TOTAL_BITS = maxBits;
     int LEVEL_BITS = levelBits;
     int[][] bits = getBits(TOTAL_BITS, LEVEL_BITS);
     return getLevelAndIndexInfoFromId(inputId, bits);
   }


   public static BigDecimal[][] getLevelAndIndexInfoFromId(BigDecimal input, int[][] bits) {
     int MAX_LEVEL = bits.length;




     BigDecimal[][] outPut = new BigDecimal[MAX_LEVEL][2];

     for (int i = 0; i < MAX_LEVEL; i++) {
       int POW = bits[i][0];
       int LEVEL_BITS = bits[i][1];
       BigDecimal min = (new BigDecimal(10)).pow(POW).stripTrailingZeros();
       BigDecimal[] result = input.divideAndRemainder(min);
       outPut[i][0] = result[0].multiply(min);
       outPut[i][1] = result[0].divideAndRemainder((new BigDecimal(10)).pow(LEVEL_BITS))[1].setScale(0, RoundingMode.DOWN);
     }


     return outPut;
   }




   public static BigDecimal getIdBaseOnParentIdByIndex(BigDecimal parentId, int currLevel, Long index, int maxBits, int levelBits) {
     if (currLevel == 0) {
       parentId = new BigDecimal("0");
     }

     int TOTAL_BITS = maxBits;
     int LEVEL_BITS = levelBits;
     int[][] bits = getBits(TOTAL_BITS, LEVEL_BITS);
     return getIdBaseOnParentIdByIndex(parentId, currLevel, index, bits);
   }




   public static BigDecimal getIdBaseOnParentIdByIndex(BigDecimal parentId, int currLevel, Long index, int[][] bits) {
     if (currLevel == 0) {
       parentId = new BigDecimal("0");
     }

     if (currLevel >= bits.length) {
       return new BigDecimal("-1");
     }



     BigDecimal input = parentId;
     int POW = bits[currLevel][0];
     BigDecimal min = (new BigDecimal(10)).pow(POW).stripTrailingZeros();
     POW = bits[currLevel][1];
     BigDecimal max = (new BigDecimal(10)).pow(POW).stripTrailingZeros();
     if (index.longValue() >= max.longValue())
     {
       return new BigDecimal("-1");
     }
     BigDecimal currId = min.multiply(new BigDecimal(index.longValue()));
     currId = currId.add(input);

     return currId;
   }




   public static BigDecimal getParentId(Long id, int currLevel, int maxBits, int levelBits) {
     int TOTAL_BITS = maxBits;
     int LEVEL_BITS = levelBits;
     int[][] bits = getBits(TOTAL_BITS, LEVEL_BITS);
     return getParentId(id, currLevel, bits);
   }




   public static BigDecimal getParentId(Long id, int currLevel, int[][] bits) {
     if (currLevel == 0) {
       return new BigDecimal("-1");
     }

     if (currLevel >= bits.length) {
       return new BigDecimal("-1");
     }




     BigDecimal input = new BigDecimal(id.longValue());
     int POW = bits[currLevel - 1][0];
     BigDecimal min = (new BigDecimal(10)).pow(POW).stripTrailingZeros();
     BigDecimal[] result = input.divideAndRemainder(min);
     BigDecimal parentId = result[0].multiply(min);

     return parentId;
   }


   public static String[][] getIdInfoFromUnionId(String inputId) {
     if (StringUtils.isBlank(inputId)) {
       return null;
     }
     String[] ids = inputId.split("-");
     int MAX_LEVEL = ids.length;

     String[][] outPut = new String[MAX_LEVEL][2];

     for (int i = 0; i < MAX_LEVEL; i++) {
       if (i == 0) {
         outPut[i][0] = ids[0];
       } else if (i == MAX_LEVEL - 1) {
         outPut[i][0] = inputId;
       } else {
         outPut[i][0] = getParentUnionId(inputId, i + 1);
       }
       outPut[i][1] = ids[i];
     }


     return outPut;
   }






   public static String getUnionIdBaseOnParentUnionIdByIndex(String parentId, String index) {
     String id = parentId + "-" + index;

     return id;
   }



   public static String getParentId(String id, int currLevel) {
     if (StringUtils.isBlank(id)) {
       return null;
     }
     String[] ids = id.split("-");
     int MAX_LEVEL = ids.length;
     if (currLevel == 0) {
       return null;
     }

     if (currLevel >= MAX_LEVEL) {
       return null;
     }


     String parentId = ids[currLevel - 1];

     return parentId;
   }



   public static String getParentUnionId(String id, int currLevel) {
     if (StringUtils.isBlank(id)) {
       return null;
     }
     String[] ids = id.split("-");
     int MAX_LEVEL = ids.length;
     if (currLevel == 0) {
       return null;
     }

     if (currLevel >= MAX_LEVEL) {
       return null;
     }

     return getUnionId(currLevel, ids);
   }

   public static String getUnionId(String... ids) {
     return getUnionIdWithSeparator("-", ids);
   }

   public static String getUnionIdWithoutSeparator(String... ids) {
     return getUnionIdWithSeparator("", ids);
   }

   public static String getUnionIdWithSeparator(String separator, String... ids) {
     if (ids == null) {
       return null;
     }

     if (ids.length == 0) {
       return null;
     }
     String unionId = "";
     for (int i = 0; i < ids.length; i++) {
       unionId = unionId + ids[i];
       if (i < ids.length - 1) {
         unionId = unionId + separator;
       }
     }

     return unionId;
   }

   public static String getUnionId(int limit, String... ids) {
     if (ids == null) {
       return null;
     }

     if (ids.length == 0) {
       return null;
     }

     if (limit > ids.length)
     {
       return null;
     }

     String unionId = "";
     for (int i = 0; i < limit; i++) {
       unionId = unionId + ids[i];
       if (i < limit - 1) {
         unionId = unionId + "-";
       }
     }

     return unionId;
   }

   static int[][] getBits(int maxBits, int levelBits) {
     int TOTAL_BITS = maxBits;
     int LEVEL_BITS = levelBits;
     int MAX_LEVEL = TOTAL_BITS / LEVEL_BITS;

     int[][] bits = new int[MAX_LEVEL][2];

     for (int i = 0; i < MAX_LEVEL; i++) {
       bits[i][0] = (MAX_LEVEL - i - 1) * LEVEL_BITS;
       bits[i][1] = LEVEL_BITS;
     }
     return bits;
   }

   public enum AlignType { LEFT(0, "左对齐：从最高位开始排布"),
     RIGHT(1, "右对齐：从最低位开始排布");

     private int code;
     private String message;

     AlignType(int code, String message) {
       this.code = code;
       this.message = message;
     }


     public int getType() {
       return this.code;
     }

     public String getMessage() {
       return this.message;
     } }

 }

