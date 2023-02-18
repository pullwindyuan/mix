package com.futuremap.custom.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;


public class BigDecimalUtil {

	public static Boolean isEmpry(BigDecimal value) {
		return value == null || value.compareTo(BigDecimal.ZERO) == 0;
	}
	
	public static Boolean isNull(Object value) {
		return value == null ;
	}
	
	
	//保留两位小数
	public static BigDecimal devide2(BigDecimal value1, BigDecimal value2) {
		if (BigDecimalUtil.isEmpry(value1) || BigDecimalUtil.isEmpry(value2)) {
			return BigDecimal.ZERO;
		}
		return value1.divide(value2, 2, RoundingMode.HALF_UP);
	}
	

	/**
	 * @param v
	 * @param salaryBigDecimal
	 * @return
	 */
	public static BigDecimal devide4(Long value1, BigDecimal value2) {
		if (value1 == 0 || BigDecimalUtil.isEmpry(value2)) {
			return BigDecimal.ZERO;
		}
		return new BigDecimal(value1).divide(value2, 4, RoundingMode.HALF_UP);
	}
	
	//保留两位小数
	public static BigDecimal devide2(Integer value1, Integer value2) {
		if (BigDecimalUtil.isEmpry(value1) || BigDecimalUtil.isEmpry(value2)) {
			return BigDecimal.ZERO;
		}
		return  new BigDecimal(value1).divide(new BigDecimal(value2), 2, RoundingMode.HALF_UP);
	}
	
	public static Boolean isEmpry(Integer value) {
		return value == null || value == 0;
	}
	
	
	//保留两位小数
	public static BigDecimal add(BigDecimal value1, BigDecimal value2) {
		if(BigDecimalUtil.isNull(value1)) {
			value1 = BigDecimal.ZERO;
		}
		if (BigDecimalUtil.isNull(value2)) {
			value2 = BigDecimal.ZERO;
		}
		return value1.add(value2);
	}
	
	//保留两位小数
	public static BigDecimal subtract(BigDecimal value1, BigDecimal value2) {
		if(BigDecimalUtil.isNull(value1)) {
			value1 = BigDecimal.ZERO;
		}
		if (BigDecimalUtil.isNull(value2)) {
			value2 = BigDecimal.ZERO;
		}
		return value1.subtract(value2);
	}
	
	
	public static BigDecimal multiply(BigDecimal value1, BigDecimal value2) {
		if (BigDecimalUtil.isEmpry(value1) || BigDecimalUtil.isEmpry(value2)) {
			return null;
		}
		return value1.multiply(value2).setScale(2, BigDecimal.ROUND_HALF_UP);
	}
	
	
	
	// 
	public static BigDecimal getYoy(BigDecimal value1, BigDecimal value2) {
		if (BigDecimalUtil.isEmpry(value1) || BigDecimalUtil.isEmpry(value2)) {
			return BigDecimal.ZERO;
		}
		
		BigDecimal sub = subtract(value1,value2);
		return devide2(sub, value2);
	}
	/** 

	    * 计算金额方法 

	    * @author : shijing 

	    * 2017年3月23日下午4:53:00 

	    * @param b1 

	    * @param bn 

	    * @return 

	    */  

	   public static BigDecimal safeSubtract(BigDecimal b1, BigDecimal... bn) {  

	       return safeSubtract(true, b1, bn);  

	   }
	 /** 

	    * BigDecimal的安全减法运算 

	    * @author : shijing 

	    * 2017年3月23日下午4:50:45 

	    * @param isZero  减法结果为负数时是否返回0，true是返回0（金额计算时使用），false是返回负数结果 

	    * @param b1        被减数 

	    * @param bn        需要减的减数数组 

	    * @return 

	    */  

	   public static BigDecimal safeSubtract(Boolean isZero, BigDecimal b1, BigDecimal... bn) {  

	       if (null == b1) {  

	           b1 = BigDecimal.ZERO;  

	       }  

	       BigDecimal r = b1;  

	       if (null != bn) {  

	           for (BigDecimal b : bn) {  

	               r = r.subtract((null == b ? BigDecimal.ZERO : b));  

	           }  

	       }  

	       return isZero ? (r.compareTo(BigDecimal.ZERO) == -1 ? BigDecimal.ZERO : r) : r;  

	   } 
	
	
	public static BigDecimal getRomdanInt(int min, int max) {
		Random random = new Random();
		int s = random.nextInt(max) % (max - min + 1) + min;
		return BigDecimal.valueOf(s);
	}
	
	public static BigDecimal getRomdanDeci(int min, int max) {
		Random random = new Random();
		int s = random.nextInt(max) % (max - min + 1) + min;
		return devide2(BigDecimal.valueOf(s), BigDecimal.valueOf(100));
	}

	
	
}
