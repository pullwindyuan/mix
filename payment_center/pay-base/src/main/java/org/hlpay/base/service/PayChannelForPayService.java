 package org.hlpay.base.service;
 import com.alibaba.fastjson.JSON;
 import com.alibaba.fastjson.JSONObject;
 import com.github.pagehelper.StringUtil;
 import java.util.HashMap;
 import java.util.List;
 import java.util.Map;
 import java.util.function.BiConsumer;
 import javax.annotation.Resource;
 import org.hlpay.base.bo.MchInfoForConfigBo;
 import org.hlpay.base.bo.PayChannelForConfigBo;
 import org.hlpay.base.model.MchInfo;
 import org.hlpay.base.model.PayChannel;
 import org.hlpay.base.model.PayChannelExample;
 import org.hlpay.base.model.PayChannelForRuntime;
 import org.hlpay.common.entity.CommonResult;
 import org.hlpay.common.enumm.RunModeEnum;
 import org.hlpay.common.util.BeanUtil;
 import org.hlpay.common.util.HttpClient;
 import org.hlpay.common.util.JsonUtil;
 import org.hlpay.common.util.MyLog;
 import org.hlpay.common.util.ResultUtil;
 import org.hlpay.common.util.UnionIdUtil;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.core.env.Environment;
 import org.springframework.stereotype.Component;

 @Component
 public class PayChannelForPayService {
   private static final MyLog _log = MyLog.getLog(PayChannelForPayService.class);

   @Autowired
   private RunModeService runModeService;

   @Autowired
   private MchInfoService mchInfoService;
   @Autowired
   private PayChannelService payChannelService;
   @Resource
   private Environment env;
   private static MchInfo agencyInfo;
   private static Map<String, String> channelIdTypeMap;
   private static Map<String, Map<String, PayChannelForRuntime>> payChannelIdMap;
   private static Map<String, PayChannelForRuntime> payChannelMap;
   private static Map<String, PayChannelForRuntime> payChannelGlobalMap;

   public Map<String, PayChannelForRuntime> getPayChannelIdMapByMchId(String mchId) {
     Map<String, PayChannelForRuntime> payChannelMap = payChannelIdMap.get(mchId);
     if (payChannelMap == null) {
       try {
         init(mchId);
       } catch (Exception e) {
         e.printStackTrace();
         return null;
       }
     }
     return payChannelMap;
   }

   private final Object $lock = new Object[0]; public void init(List<PayChannel> records) { synchronized (this.$lock) {

       agencyInfo = this.mchInfoService.getAgencyInfo();

       if (payChannelGlobalMap == null) {
         payChannelGlobalMap = new HashMap<>();
       }
       if (payChannelIdMap == null) {
         payChannelIdMap = new HashMap<>();
       }
       if (payChannelMap == null) {
         payChannelMap = new HashMap<>();
       }
       if (channelIdTypeMap == null) {
         channelIdTypeMap = new HashMap<>();
       }





       List<MchInfo> mchInfoList = this.mchInfoService.selectMchInfoList();

       for (PayChannel payChannel : records) {
         payChannel.setCurrency(JSONObject.parseObject(payChannel.getParam()).getString("currency").toUpperCase());
         payChannelGlobalMap.put(payChannel.getMchId() + payChannel.getChannelId(), (PayChannelForRuntime)BeanUtil.copyProperties(payChannel, PayChannelForRuntime.class));
         channelIdTypeMap.put(payChannel.getChannelId(), "");
       }


       for (MchInfo mchInfo : mchInfoList) {

         final String[][] ids = UnionIdUtil.getIdInfoFromUnionId(mchInfo.getMchId());
         for (int i = 0; i < ids.length; i++) {
           final String id = ids[i][0];
           channelIdTypeMap.forEach(new BiConsumer<String, String>() {
                 PayChannelForRuntime payChannel;
                 Map<String, PayChannelForRuntime> tempMap;
                 PayChannelForRuntime payChannelForRuntime;

                 public void accept(String s, String s2) {
                   this.payChannel = (PayChannelForRuntime)PayChannelForPayService.payChannelGlobalMap.get(id + s);
                   if (this.payChannel == null) {
                     for (int n = ids.length - 1; n >= 0; n--) {
                       this.payChannel = (PayChannelForRuntime)PayChannelForPayService.payChannelGlobalMap.get(ids[n][0] + s);
                       if (this.payChannel != null) {
                         break;
                       }
                     }
                     if (this.payChannel == null) {
                       this.payChannel = (PayChannelForRuntime)PayChannelForPayService.payChannelGlobalMap.get("1" + s);
                     }
                   }

                   if (this.payChannel != null) {
                     this.payChannelForRuntime = (PayChannelForRuntime)BeanUtil.copyProperties(this.payChannel, PayChannelForRuntime.class);
                     try {
                       if (this.payChannel.getMchId().equals("1")) {
                         this.payChannelForRuntime.setMchInfo(PayChannelForPayService.agencyInfo);
                       } else {
                         this.payChannelForRuntime.setMchInfo(PayChannelForPayService.this.mchInfoService.selectMchInfo(this.payChannel.getMchId()));
                       }
                     } catch (NoSuchMethodException e) {
                       e.printStackTrace();
                     }
                     PayChannelForPayService.payChannelMap.put(mchInfo.getMchId() + this.payChannel.getChannelId(), this.payChannelForRuntime);
                     this.tempMap = (Map<String, PayChannelForRuntime>)PayChannelForPayService.payChannelIdMap.get(id);
                     if (this.tempMap == null) {
                       this.tempMap = new HashMap<>();
                       PayChannelForPayService.payChannelIdMap.put(id, this.tempMap);
                     }
                     this.tempMap.put(this.payChannel.getChannelId(), this.payChannelForRuntime);
                   }
                 }
               });
         }
       }
     }  }

   public Integer configPlatformPayChannelFromRemote(MchInfoForConfigBo mchInfoForConfigBo) {
     List<PayChannelForConfigBo> list = mchInfoForConfigBo.getPayChannels();
     if (list == null) {
       return Integer.valueOf(0);
     }


     init(BeanUtil.copyProperties(list, PayChannel.class));
     return Integer.valueOf(1);
   }

   public void configPlatformPayChannelFromDB() {
     PayChannelExample example = new PayChannelExample();
     PayChannelExample.Criteria criteria = example.createCriteria();

     criteria.andStateEqualTo(Byte.valueOf((byte)1));
     List<PayChannel> payChannelList = this.payChannelService.selectByExample(example);

     init(payChannelList);
   }

   public void init(String startMchId) throws Exception {
     int runMode = this.runModeService.getRunModeCode();
     if (runMode == RunModeEnum.PRIVATE.getCode().intValue()) {
       MchInfo agency, mchInfo = this.mchInfoService.selectByPrimaryKey(startMchId);

       String[][] ids = UnionIdUtil.getIdInfoFromUnionId(startMchId);
       if (mchInfo == null) {
         this.mchInfoService.tryToAddAllExternalMchInfo(ids[ids.length - 1][1]);
         agency = this.mchInfoService.getAgencyInfo();
       } else {
         agency = this.mchInfoService.selectByPrimaryKey(ids[0][1]);
       }
       forceGetPayChannelListByMchIdFromRemote(agency.getExternalId());
     } else {
       configPlatformPayChannelFromDB();
     }
   }

   public PayChannelForRuntime getMchPayChannel(String mchId, String channelId) {
     if (payChannelMap == null) {
       try {
         init(mchId);
       } catch (Exception e) {
         e.printStackTrace();
         return null;
       }
     }
     return payChannelMap.get(mchId + channelId);
   }

   public void forceGetPayChannelListByMchIdFromRemote(String mchId) {
     String configApiUrl = this.env.getProperty("deploy.publicPlatformPayMgrDeployUrl") + "/platform/getPlatform";

     int runMode = this.runModeService.getRunModeCode();
     if (runMode == RunModeEnum.PRIVATE.getCode().intValue())
     {
       if (!StringUtil.isEmpty(configApiUrl)) {
         try {
           System.out.println("请求更新配置");
           String result = HttpClient.post(configApiUrl, UnionIdUtil.getIdInfoFromUnionId(mchId)[0][0]);

           CommonResult<JSONObject> commonResult = (CommonResult<JSONObject>)JSONObject.parseObject(result, CommonResult.class);
           _log.info("获取的平台信息：" + ((JSONObject)commonResult.getData()).toJSONString(), new Object[0]);
           try {
             if (commonResult.getCode().intValue() != 200) {
               System.out.println("配置支付参数失败！");
             } else {
               JSONObject jsonObject = (JSONObject)commonResult.getData();
               MchInfoForConfigBo mchInfoForConfigBo = (MchInfoForConfigBo)JSONObject.toJavaObject((JSON)jsonObject, MchInfoForConfigBo.class);
               configPlatformPayChannelFromRemote(mchInfoForConfigBo);
             }
           } catch (Exception e) {
             e.printStackTrace();
             System.out.println("配置支付参数失败！");
           }
         } catch (Exception e) {
           e.printStackTrace();
         }
       }
     }
   }

   public Map selectPayChannel(JSONObject jsonParam) {
     String mchId = jsonParam.get("mchId").toString();
     String channelId = jsonParam.get("channelId").toString();
     PayChannelForRuntime payChannelForRuntime = getMchPayChannel(mchId, channelId);
     if (payChannelForRuntime == null) {
       return ResultUtil.createFailMap("渠道查询失败", null);
     }
     JSONObject channelObj = JsonUtil.getJSONObjectFromObj(payChannelForRuntime);
     return ResultUtil.createSuccessMap(channelObj);
   }

   public JSONObject getByMchIdAndChannelId(String mchId, String channelId) throws NoSuchMethodException {
     PayChannelForRuntime payChannelForRuntime = getMchPayChannel(mchId, channelId);
     if (payChannelForRuntime == null) return null;
     return (JSONObject)JSONObject.toJSON(payChannelForRuntime);
   }

   public PayChannelForRuntime selectPayChannel(String mchId, String channelId) throws NoSuchMethodException {
     return getMchPayChannel(mchId, channelId);
   }
 }
