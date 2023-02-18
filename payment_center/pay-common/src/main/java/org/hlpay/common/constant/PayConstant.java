 package org.hlpay.common.constant;

 import java.io.File;

 public class PayConstant
 {
   public static final String PAY_CHANNEL_WX_JSAPI = "WX_JSAPI";
   public static final String PAY_CHANNEL_WX_NATIVE = "WX_NATIVE";
   public static final String PAY_CHANNEL_WX_APP = "WX_APP";
   public static final String PAY_CHANNEL_WX_MWEB = "WX_MWEB";
   public static final String PAY_CHANNEL_IAP = "IAP";
   public static final String PAY_CHANNEL_ALIPAY_MOBILE = "ALIPAY_MOBILE";
   public static final String PAY_CHANNEL_ALIPAY_PC = "ALIPAY_PC";
   public static final String PAY_CHANNEL_ALIPAY_WAP = "ALIPAY_WAP";
   public static final String PAY_CHANNEL_ALIPAY_QR = "ALIPAY_QR";
   public static final String PAY_CHANNEL_UNION_PC = "UNION_PC";
   public static final String PAY_CHANNEL_UNION_WAP = "UNION_WAP";
   public static final String PAY_CHANNEL_RECHARGE_BY_WX_WEB = "RECHARGE_BY_WX_MWEB";
   public static final String PAY_CHANNEL_RECHARGE_BY_ALIPAY_WEB = "RECHARGE_BY_ALIPAY_WAP";
   public static final String PAY_CHANNEL_RECHARGE_BY_UNION_WEB = "RECHARGE_BY_UNION_WAP";
   public static final String PAY_CHANNEL_FREEZE = "HL_FREEZE";
   public static final String PAY_CHANNEL_UNFREEZE = "HL_UNFREEZE";
   public static final String PAY_CHANNEL_AWARD_COIN = "HL_AWARD_COIN";
   public static final String PAY_CHANNEL_COIN_RECHARGE = "HL_COIN_RECHARGE";
   public static final String PAY_CHANNEL_COIN_WITHDRAW = "HL_COIN_WITHDRAW";
   public static final String PAY_CHANNEL_COIN_PAY = "HL_COIN_PAY";
   public static final String PAY_CHANNEL_COIN_ASYN_PAY = "HL_COIN_ASYN_PAY";
   public static final String CHANNEL_NAME_WX = "WX";
   public static final String CHANNEL_NAME_ALIPAY = "ALIPAY";
   public static final String CHANNEL_NAME_UNION = "UNION";
   public static final String CHANNEL_NAME_INNER = "INNER";
   public static final byte PAY_STATUS_CLOSED = -3;
   public static final byte PAY_STATUS_EXPIRED = -2;
   public static final byte PAY_STATUS_FAILED = -1;
   public static final byte PAY_STATUS_INIT = 0;
   public static final byte PAY_STATUS_PAYING = 1;
   public static final byte PAY_STATUS_SUCCESS = 2;
   public static final byte PAY_STATUS_COMPLETE = 3;
   public static final byte PAY_STATUS_SETTLE_COMPLETE = 4;
   public static final byte PAY_STATUS_SETTLE_SYNC_COMPLETE = 5;
   public static final byte PAY_STATUS_SUCCESS_AWARD = 6;
   public static final byte PAY_STATUS_COMPLETE_AWARD = 7;
   public static final byte PAY_STATUS_TRUSTEESHIP_SETTLE_COMPLETE = 8;
   public static final byte PAY_STATUS_TRUSTEESHIP_SETTLE_COMPLETE_AWARD = 9;
   public static final byte PAY_STATUS_SUCCESS_AND_SETTLE_REPEAT = -4;
   public static final byte PAY_STATUS_FAILED_AND_SETTLE_FAILED = -3;
   public static final byte PAY_STATUS_SUCCESS_AND_SETTLE_FAILED = -2;
   public static final byte PAY_STATUS_SUCCESS_BUT_SETTLE_AMOUNT_NOT_FIXED = -1;
   public static final byte PAY_STATUS_SUCCESS_AND_SETTLED = 2;
   public static final byte PAY_STATUS_SUCCESS_AND_SYNC_SETTLED = 2;
   public static final byte PAY_STATUS_SUCCESS_AND_SETTLE_REPEAT_FIXED = 4;
   public static final byte TRANS_STATUS_INIT = 0;
   public static final byte TRANS_STATUS_TRANING = 1;
   public static final byte TRANS_STATUS_SUCCESS = 2;
   public static final byte TRANS_STATUS_FAIL = 3;
   public static final byte TRANS_STATUS_COMPLETE = 4;
   public static final byte TRANS_RESULT_INIT = 0;
   public static final byte TRANS_RESULT_REFUNDING = 1;
   public static final byte TRANS_RESULT_SUCCESS = 2;
   public static final byte TRANS_RESULT_FAIL = 3;
   public static final byte REFUND_STATUS_INIT = 0;
   public static final byte REFUND_STATUS_REFUNDING = 1;
   public static final byte REFUND_STATUS_SUCCESS = 2;
   public static final byte REFUND_STATUS_FAIL = 3;
   public static final byte REFUND_STATUS_COMPLETE = 4;
   public static final byte REFUND_STATUS_CANCEL = 5;
   public static final byte REFUND_RESULT_INIT = 0;
   public static final byte REFUND_RESULT_REFUNDING = 1;
   public static final byte REFUND_RESULT_SUCCESS = 2;
   public static final byte REFUND_RESULT_FAIL = 3;
   public static final String MCH_NOTIFY_TYPE_PAY = "1";
   public static final String MCH_NOTIFY_TYPE_TRANS = "2";
   public static final String MCH_NOTIFY_TYPE_REFUND = "3";
   public static final String MCH_NOTIFY_TYPE_INNER_SETTLE = "4";
   public static final byte MCH_NOTIFY_STATUS_NOTIFYING = 1;
   public static final byte MCH_NOTIFY_STATUS_SUCCESS = 2;
   public static final byte MCH_NOTIFY_STATUS_FAIL = 3;
   public static final String RESP_UTF8 = "UTF-8";
   public static final String RETURN_PARAM_RETCODE = "retCode";
   public static final String RETURN_PARAM_RETMSG = "retMsg";
   public static final String RESULT_PARAM_RESCODE = "resCode";
   public static final String RESULT_PARAM_ERRCODE = "errCode";
   public static final String RESULT_PARAM_ERRCODEDES = "errCodeDes";
   public static final String RESULT_PARAM_SIGN = "sign";
   public static final String RETURN_VALUE_SUCCESS = "SUCCESS";
   public static final String RETURN_VALUE_FAIL = "FAIL";
   public static final String RETURN_ALIPAY_VALUE_SUCCESS = "success";
   public static final String RETURN_ALIPAY_VALUE_FAIL = "fail";
   public static final String RETURN_ALIPAY_TRADE_SUCCESS = "TRADE_SUCCESS";
   public static final String RETURN_WXPAY_TRADE_SUCCESS = "SUCCESS";
   public static final String RETURN_WXPAY_TRADE_TO_REFUND = "REFUND";
   public static final String RETURN_WXPAY_TRADE_NOTPAY = "NOTPAY";
   public static final String RETURN_WXPAY_TRADE_CLOSED = "CLOSED";
   public static final String RETURN_WXPAY_TRADE_REVOKED = "REVOKED";
   public static final String RETURN_WXPAY_TRADE_USERPAYING = "USERPAYING";
   public static final String RETURN_WXPAY_TRADE_PAYERROR = "PAYERROR";
   public static final String RETURN_UNION_VALUE_SUCCESS = "Success!";
   public static final String RETURN_UNION_VALUE_FAIL = "fail";
   public static final String RETURN_UNION_VALUE_SUCCESS_CODE = "00";
   public static final String RETURN_UNION_VALUE_FAIL_CODE = "01";
   public static final String DEFAULT_PLATFORM_ID = "1";
   public static final Integer DEFAULT_SETTLE_POUNDAGE_RATE = Integer.valueOf(6); public static final String NOTIFY_BUSI_PAY = "NOTIFY_VV_PAY_RES";
   public static final Integer DEFAULT_SETTLE_TN = Integer.valueOf(10);
   public static final String NOTIFY_BUSI_TRANS = "NOTIFY_VV_TRANS_RES";

   public static class JdConstant { public static final String CONFIG_PATH = "jd" + File.separator + "jd"; }


   public static class WxConstant
   {
     public static final String TRADE_TYPE_APP = "APP";
     public static final String TRADE_TYPE_JSPAI = "JSAPI";
     public static final String TRADE_TYPE_NATIVE = "NATIVE";
     public static final String TRADE_TYPE_MWEB = "MWEB";
   }

   public static class AliConstant
   {
     public static final String TRADE_TYPE_APP = "MOBILE";
     public static final String TRADE_TYPE_PC = "PC";
     public static final String TRADE_TYPE_QR = "QR";
     public static final String TRADE_TYPE_WAP = "WAP";
   }

   public static class BidouConstant {
     public static final String TRADE_TYPE_APP = "MOBILE";
     public static final String TRADE_TYPE_PC = "PC";
     public static final String TRADE_TYPE_QR = "QR";
     public static final String TRADE_TYPE_WAP = "WAP";
   }

   public static class HLConstant {
     public static final String TRADE_TYPE_APP = "MOBILE";
     public static final String TRADE_TYPE_PC = "PC";
     public static final String TRADE_TYPE_QR = "QR";
     public static final String TRADE_TYPE_WAP = "WAP";
   }

   public static class IapConstant {
     public static final String CONFIG_PATH = "iap" + File.separator + "iap";
   }

   public static class AlipayConstant {
     public static final String CONFIG_PATH = "alipay" + File.separator + "alipay";
     public static final String TRADE_STATUS_WAIT = "WAIT_BUYER_PAY";
     public static final String TRADE_STATUS_CLOSED = "TRADE_CLOSED";
     public static final String TRADE_STATUS_SUCCESS = "TRADE_SUCCESS";
     public static final String TRADE_STATUS_FINISHED = "TRADE_FINISHED";
   }
 }

