package com.futuremap.custom.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Const {

	public static class Template {

		public final static String PROFIT_TYPE = "prifit";

		public final static String DEPT_TYPE = "dept";

		public final static String CASH_TYPE = "cash";

		public final static String OTHERS_TYPE = "others";

		public final static List<String> COLUMN_TYPES = Arrays.asList("class", "column");
	}
	
	public static class PayWay {

		public final static String ALIPAY = "alipay";
		
		public final static String WECHAT = "wechat";

	}
	
	
	  /**
     * 用户常量
     */
    public static class User {
    	public final static String AVATAR = "https://img.futuremap.com.cn/FhhjJ1vZ36uRMjQvoMwLC91FliV7";
        
    	public final static Integer DELETE = 1;
    	
    	public final static Integer NORMAL = 0;
    	
    }

	public static class ComCompany {

		public final static Integer CASE_TYPE = 3;

	}
	
	public static class FinanceImportType {
		//1全量覆盖 
		public final static Integer COVER = 1;
		//2增量刷新
		public final static Integer FIX = 2;

	}
	
	public static class CasePublic {
		public final static List<String> CASE_PULIC = Arrays.asList("深圳交易所", "上海交易所","香港交易所","纽约证券交易所","纳斯达克","东京证券交易所","泛欧交易所","东京证券交易所","多伦多证券交易所","多伦多证券交易所");


	}

	/**
	 * 
	 * 公共常量
	 *
	 */
	public static class Public {

		public final static String TRUE_STRING = "true";

		public final static String FALSE_STRING = "false";
		
		public final static String T_STR = "T";

		public final static String F_STR  = "F";

		public final static Integer TRUE = 1;

		public final static Integer FALSE = 0;

		public final static Integer FAIL = 0;

		public final static Integer SUCCESS = 1;

		public final static Integer DELETE = 1;
		

		public final static Integer NORMAL = 0;
		
		public final static String SUCCESS_STR = "SUCCESS";

		public final static String DELETE_STR = "FAIL";
	}

	// 微信
	public static class WeiXin {
		public static final String WXAVATAR_PREFIX = "wxavatar";

		// 小程序类型
		public final static Integer MINI_TYPE = 1;
		// 网页类型
		public final static Integer WEB_TYPE = 2;

		public final static String ERRMSG_SUCCESS = "OK";

		// ************************小程序******************************************************************

		public final static String MINI_USER = "https://api.weixin.qq.com/sns/jscode2session?appid=%s&secret=%s&js_code=%s&grant_type=authorization_code";

		// ************************网页应用******************************************************************

		public final static String WEB_QRCODE_URL = "https://open.weixin.qq.com/connect/qrconnect?appid=%s&redirect_uri=%s&response_type=code&scope=snsapi_login&state=login#wechat_redirect";

		/*
		 * 接口说明 通过code获取access_token的接口。
		 */
		public final static String WEB_ACCESS_TOKEN = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code";

		/*
		 * 获取用户个人信息（UnionID机制） 接口说明
		 * 此接口用于获取用户个人信息。开发者可通过OpenID来获取用户基本信息。特别需要注意的是，如果开发者拥有多个移动应用、网站应用和公众帐号，
		 * 可通过获取用户基本信息中的unionid来区分用户的唯一性，
		 * 因为只要是同一个微信开放平台帐号下的移动应用、网站应用和公众帐号，用户的unionid是唯一的。换句话说，同一用户，对同一个微信开放平台下的不同应用，
		 * unionid是相同的。
		 * 请注意，在用户修改微信头像后，旧的微信头像URL将会失效，因此开发者应该自己在获取用户信息后，将头像图片保存下来，避免微信头像URL失效后的异常情况。
		 */
		public final static String WEB_USERINFO = "https://api.weixin.qq.com/sns/userinfo?access_token=%s&openid=%s";

		/*
		 * 接口说明 检验授权凭证（access_token）是否有效
		 */
		public final static String WEB_AUTH = "https://api.weixin.qq.com/sns/auth?access_token=%s&openid=%s";
		// ************************公众号******************************************************************

		public final static String PUBLIC_ACCESS_TOKEN = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";

		public final static String PUBLIC_USERS = "https://api.weixin.qq.com/cgi-bin/user/get?access_token=%s";

		public final static String PUBLIC_SEND_MESSAGE = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=%s";
	}

	public static class Question {
		public final static String TEMPLATE_TYPE = "template";
		public final static String REPORT_TYPE = "report";
		// 商业模式
		public final static String MODEL_TYPE = "model";
		// AI董事会
		public final static String MANAGER_TYPE = "manager";
		//产业
		public final static String INDUSTRY_TYPE = "industry";

		public final static List<String> TREE_TYPES = Arrays.asList(MODEL_TYPE, MANAGER_TYPE);
	}

	/**
	 * WAIT_BUYER_PAY TRADE_SUCCESS TRADE_CLOSED TRADE_FINISHED
	 */
	public static enum AlipayStatus {

		WAIT_BUYER_PAY("WAIT_BUYER_PAY", "等待买家付款"), TRADE_SUCCESS("TRADE_SUCCESS", "买家己付款"),
		TRADE_CLOSED("TRADE_CLOSED", "交易关闭"), TRADE_FINISHED("TRADE_FINISHED", "交易成功结束");

		private String key;
		private String value;

		public String getKey() {
			return key;
		}

		public String getValue() {
			return value;
		}

		AlipayStatus(String key, String value) {
			this.key = key;
			this.value = value;
		}

		public static String getStrByKey(String key) {
			if ("WAIT_BUYER_PAY".equals(key)) {
				return WAIT_BUYER_PAY.value;
			} else if ("TRADE_SUCCESS".equals(key)) {
				return TRADE_SUCCESS.value;
			} else if ("TRADE_CLOSED".equals(key)) {
				return TRADE_CLOSED.value;
			} else if ("TRADE_FINISHED".equals(key)) {
				return TRADE_FINISHED.value;
			}
			return "";
		}
	}

	public static class PayStatus {
		public final static Integer UNPAY = 1;

		public final static Integer PAYED = 2;

	}

	public static class OrderType {
		// 解决方案
		public final static String SOLUTION = "solution";
		// 案例
		public final static String CASE = "case";

	}
	
	public static class Qcc {
		public static Map<String, String> newsTag = new HashMap<String, String>() {
			{
				put("10001","承诺失信");
				put("10002","兑付/偿付不确定");
				put("10003","债券/债务违约");
				put("10009","责令改正");
				put("10010","信披问题");
				put("11001","高管变动");
				put("11002","股权激励");
				put("12001","经营业绩");
				put("12002","战略合作");
				put("12003","兼并收购");
				put("12004","股权质押");
				put("12005","增资募资");
				put("12006","投融资");
				put("12008","资产重组");
				put("12010","利润分配");
				put("12011","接管托管");
				put("12012","生产产能");
				put("12013","关联交易");
				put("12014","产品信息");
				put("12015","项目签约");
				put("13001","增持减持");
				put("13002","股份回购");
				put("13003","股权转让");
				put("13004","新股发行");
				put("13005","股价下跌");
				put("13006","大宗交易");
				put("13007","上市退市");
				put("13009","停复牌");
				put("13010","限售股解禁");
				put("14001","信贷业务");
				put("14002","股东大会");
				put("14003","评级信息");
				put("14004","荣誉奖项");
				put("14005","政策影响");
				put("14006","考察调研");
				put("14007","牌照");
				put("14008","专利");
				put("14009","公示公告");
				put("14010","会议相关");
				put("14011","比赛竞赛");
				put("14012","区块链");
				put("14014","组织成立");
				put("14015","5G");
				put("20003","担保预警");
				put("20004","资金风险");
				put("20005","计提坏账准备");
				put("30001","高层被查");
				put("30002","高管违法");
				put("30003","高管失联/无法履职");
				put("30004","贪污受贿");
				put("30005","裁员相关");
				put("40005","资产出售");
				put("40006","诉讼纠纷");
				put("40007","股权冻结");
				put("40008","破产清算");
				put("40010","业绩下降");
				put("40012","侵权抄袭");
				put("40014","资金挪用/占用");
				put("40016","减资/分立/合并");
				put("40017","资产查封/扣押/冻结");
				put("40018","合同纠纷");
				put("40019","客户投诉");
				put("40020","维权");
				put("50001","监管关注");
				put("50002","监管谈话");
				put("50003","警示");
				put("50004","公开谴责");
				put("60002","产品问题");
				put("60003","虚假宣传");
				put("80001","违法违规");
				put("80002","立案调查");
				put("80003","市/估值下降");
				put("80004","推迟/取消发行");
				put("80006","暴雷事件");
				put("0000","其他");
			}
		};

	}

}
