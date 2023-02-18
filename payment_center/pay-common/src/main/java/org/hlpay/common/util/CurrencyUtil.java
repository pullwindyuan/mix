 package org.hlpay.common.util;

 import java.math.BigDecimal;
 import org.web3j.utils.Convert;

 public class CurrencyUtil
 {
   public static String converDisString(Long amount, String currency) {
     if ("cny".equals(currency))
       return AmountUtil.convertCent2Dollar(amount + "") + " " + currency;
     if ("gusd".equals(currency))
       return AmountUtil.convertCent2Dollar(amount + "") + " " + currency;
     if ("eth".equals(currency)) {
       BigDecimal amountWEI = Convert.toWei(BigDecimal.valueOf(amount.longValue()), Convert.Unit.GWEI);
       return Convert.fromWei(amountWEI, Convert.Unit.ETHER).doubleValue() + " " + currency;
     }
     return amount + " " + currency;
   }


   public static BigDecimal converEthToGWEI(Double amount) {
     BigDecimal amountWEI = Convert.toWei(BigDecimal.valueOf(amount.doubleValue()), Convert.Unit.ETHER);
     return Convert.fromWei(amountWEI, Convert.Unit.GWEI);
   }

   public static BigDecimal converGWEIToWEI(Double amount) {
     return Convert.toWei(BigDecimal.valueOf(amount.doubleValue()), Convert.Unit.GWEI);
   }

   public static BigDecimal converGWEIToETH(Double amount) {
     BigDecimal amountWEI = Convert.toWei(BigDecimal.valueOf(amount.doubleValue()), Convert.Unit.GWEI);
     return Convert.fromWei(amountWEI, Convert.Unit.ETHER);
   }

   public static String converEthToGWEIToStr(Double amount) {
     BigDecimal amountWEI = Convert.toWei(BigDecimal.valueOf(amount.doubleValue()), Convert.Unit.ETHER);
     return Convert.fromWei(amountWEI, Convert.Unit.GWEI).stripTrailingZeros().toPlainString();
   }

   public static String converGWEIToWEIToStr(Double amount) {
     return Convert.toWei(BigDecimal.valueOf(amount.doubleValue()), Convert.Unit.GWEI).stripTrailingZeros().toPlainString();
   }

   public static String converGWEIToETHToStr(Double amount) {
     BigDecimal amountWEI = Convert.toWei(BigDecimal.valueOf(amount.doubleValue()), Convert.Unit.GWEI);
     return Convert.fromWei(amountWEI, Convert.Unit.ETHER).stripTrailingZeros().toPlainString();
   }


   public static String converEthToGWEIToStr(Double amount, int scale) {
     BigDecimal amountWEI = Convert.toWei(BigDecimal.valueOf(amount.doubleValue()), Convert.Unit.ETHER);
     return Convert.fromWei(amountWEI, Convert.Unit.GWEI).stripTrailingZeros().toPlainString();
   }

   public static String converGWEIToWEIToStr(Double amount, int scale) {
     return Convert.toWei(BigDecimal.valueOf(amount.doubleValue()), Convert.Unit.GWEI).stripTrailingZeros().toPlainString();
   }

   public static String converGWEIToETHToStr(Double amount, int scale) {
     BigDecimal amountWEI = Convert.toWei(BigDecimal.valueOf(amount.doubleValue()), Convert.Unit.GWEI).setScale(scale, 5);
     return Convert.fromWei(amountWEI, Convert.Unit.ETHER).toPlainString();
   }
 }
