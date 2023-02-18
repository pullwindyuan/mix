/**
 * 
 */
package com.futuremap.custom.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
* @author 作者 E-mail:
* @version 创建时间：2021年2月2日 上午9:40:36
* 类说明
*/
/**
 * @author futuremap
 *
 */
public class RegexUtil {

	public static String getFirstNumber(String str) {
		String regex = "\\d+";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(str);
		if(m.find()) {
			return m.group(0);
		}
		return str;
	}
	
	public static void main(String [] args) {
		System.out.println(getFirstNumber("1000以上"));
	}

}
