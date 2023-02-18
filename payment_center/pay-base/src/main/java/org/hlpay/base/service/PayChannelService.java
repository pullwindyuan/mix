package org.hlpay.base.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.StringUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.hlpay.base.bo.PayChannelBo;
import org.hlpay.base.cache.CacheService;
import org.hlpay.base.mapper.PayChannelMapper;
import org.hlpay.base.model.MchInfo;
import org.hlpay.base.model.PayChannel;
import org.hlpay.base.model.PayChannelExample;
import org.hlpay.base.model.SaCard;
import org.hlpay.common.entity.CommonResult;
import org.hlpay.common.enumm.CardDataTypeEnum;
import org.hlpay.common.enumm.CurrencyTypeEnum;
import org.hlpay.common.enumm.RunModeEnum;
import org.hlpay.common.enumm.SortTypeEnum;
import org.hlpay.common.util.BeanUtil;
import org.hlpay.common.util.JsonUtil;
import org.hlpay.common.util.MyLog;
import org.hlpay.common.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Component
public class PayChannelService
  extends CacheService<PayChannel>
{
  private static final MyLog _log = MyLog.getLog(PayChannelService.class);

  @Resource
  private PayChannelMapper payChannelMapper;

  @Resource
  private MchInfoService mchInfoService;
  @Autowired
  private CardService cardService;

  public PayChannel baseSelectPayChannel(String mchId, String channelId) throws NoSuchMethodException {
    PayChannel payChannel = new PayChannel();
    payChannel.setMchId(mchId);
    payChannel.setChannelId(channelId);
    payChannel = (PayChannel)getSimpleDataByUnionKey(payChannel);
    if (payChannel != null) {
      return payChannel;
    }
    List<PayChannel> list = getPayChannelListByMchId(mchId, channelId);
    if (list.size() == 0) {
      return null;
    }
    putToSimpleCache(list.get(0));
    return list.get(0);
  }

  public List<PayChannel> getPayChannelListByMchId(String mchId, String channelId) throws NoSuchMethodException {
    PayChannelExample example = new PayChannelExample();
    PayChannelExample.Criteria criteria = example.createCriteria();
    criteria.andMchIdEqualTo(mchId);
    criteria.andChannelIdEqualTo(channelId);
    List<PayChannel> payChannelList = selectByExample(example);


    if (payChannelList.size() == 0) {
      MchInfo mchInfo = this.mchInfoService.selectByPrimaryKey(mchId);
      if (mchInfo != null &&
        !StringUtil.isEmpty(mchInfo.getParentMchId()))
      {
        return getPayChannelListByMchId(mchInfo.getParentMchId(), channelId);
      }
    }

    return payChannelList;
  }

  public Map selectPayChannel(JSONObject jsonParam) throws NoSuchMethodException {
    String mchId = jsonParam.get("mchId").toString();
    String channelId = jsonParam.get("channelId").toString();
    PayChannel payChannel = baseSelectPayChannel(mchId, channelId);
    if (payChannel == null) {
      return ResultUtil.createFailMap("渠道查询失败", null);
    }
    JSONObject channelObj = JsonUtil.getJSONObjectFromObj(payChannel);
    return ResultUtil.createSuccessMap(channelObj);
  }

  public JSONObject getByMchIdAndChannelId(String mchId, String channelId) throws NoSuchMethodException {
    JSONObject param = new JSONObject();
    param.put("mchId", mchId);
    param.put("channelId", channelId);

    Map<String, Object> result = selectPayChannel(param);
    JSONObject bizResult = (JSONObject)result.get("bizResult");
    if (bizResult == null) return null;
    return bizResult;
  }

  public PayChannel selectPayChannel(String mchId, String channelId) throws NoSuchMethodException {
    return baseSelectPayChannel(mchId, channelId);
  }

  public int addPayChannel(PayChannel payChannel) {
    return insertSelective(payChannel);
  }

  public int updatePayChannel(PayChannel payChannel) {
    return updateByPrimaryKeySelective(payChannel);
  }

  public int updatePayChannelForAudit(PayChannel payChannel) {
    return updateByPrimaryKeySelectiveForAudit(payChannel);
  }

  public PayChannel selectPayChannelForTobeAudit(String channelId, String mchId) {
    PayChannelExample example = new PayChannelExample();
    PayChannelExample.Criteria criteria = example.createCriteria();
    criteria.andChannelIdEqualTo(channelId);
    criteria.andMchIdEqualTo(mchId);
    List<PayChannel> payChannelList = selectByExample(example);
    if (CollectionUtils.isEmpty(payChannelList)) return null;
    return payChannelList.get(0);
  }

  public PayChannel selectPayChannel(int id) throws NoSuchMethodException {
    return selectByPrimaryKey(Integer.valueOf(id));
  }

  public PayChannel selectByPrimaryKeyForAudit(int id) {
    return selectByPrimaryKeyForAudit(id);
  }

  public List<PayChannel> getPayChannelList(int offset, int limit, PayChannel payChannel) {
    PayChannelExample example = new PayChannelExample();
    example.setOrderByClause("mchId ASC, channelId ASC, createTime DESC");
    example.setOffset(Integer.valueOf(offset));
    example.setLimit(Integer.valueOf(limit));
    PayChannelExample.Criteria criteria = example.createCriteria();
    setCriteria(criteria, payChannel);
    return selectByExample(example);
  }

  public List<PayChannel> getPayChannelListWithoutInner(int offset, int limit, PayChannel payChannel) {
    PayChannelExample example = new PayChannelExample();
    example.setOrderByClause("mchId ASC, channelId ASC, createTime DESC");
    example.setOffset(Integer.valueOf(offset));
    example.setLimit(Integer.valueOf(limit));
    PayChannelExample.Criteria criteria = example.createCriteria();
    List<String> channelNames = new ArrayList<>();
    channelNames.add("ALIPAY");
    channelNames.add("WX");
    channelNames.add("UNION");
    criteria.andChannelNameIn(channelNames);
    setCriteria(criteria, payChannel);
    return selectByExample(example);
  }

  public Integer countWithoutInner(PayChannel payChannel) {
    PayChannelExample example = new PayChannelExample();
    PayChannelExample.Criteria criteria = example.createCriteria();
    List<String> channelNames = new ArrayList<>();
    channelNames.add("ALIPAY");
    channelNames.add("WX");
    channelNames.add("UNION");
    criteria.andChannelNameIn(channelNames);
    setCriteria(criteria, payChannel);
    return Integer.valueOf(countByExample(example));
  }



  public int listInsert(@Param("list") List<PayChannel> list) {
    int res = this.payChannelMapper.listInsert(list);
    if (res > 0) {
      for (PayChannel pc : list) {
        putToSimpleCache(pc);
      }
    }
    return res;
  }



  public int listInsertForAudit(@Param("list") List<PayChannel> list) {
    return this.payChannelMapper.listInsertForAudit(list);
  }

  public List<PayChannel> getPayChannelList(PayChannel payChannel) {
    PayChannelExample example = new PayChannelExample();
    example.setOrderByClause("mchId ASC, channelId ASC, createTime DESC");
    PayChannelExample.Criteria criteria = example.createCriteria();
    setCriteria(criteria, payChannel);
    return selectByExample(example);
  }

  public List<PayChannel> getPayChannelListByMchId(String mchId) throws NoSuchMethodException {
    PayChannelExample example = new PayChannelExample();
    PayChannelExample.Criteria criteria = example.createCriteria();
    criteria.andMchIdEqualTo(mchId);
    List<PayChannel> payChannelList = selectByExample(example);


    if (payChannelList.size() == 0) {
      MchInfo mchInfo = this.mchInfoService.selectByPrimaryKey(mchId);
      if (mchInfo != null &&
        !StringUtil.isEmpty(mchInfo.getParentMchId()))
      {
        return getPayChannelListByMchId(mchInfo.getParentMchId());
      }
    }

    return payChannelList;
  }

  public Integer count(PayChannel payChannel) {
    PayChannelExample example = new PayChannelExample();
    PayChannelExample.Criteria criteria = example.createCriteria();
    setCriteria(criteria, payChannel);
    return Integer.valueOf(countByExample(example));
  }

  void setCriteria(PayChannelExample.Criteria criteria, PayChannel payChannel) {
    if (payChannel != null) {
      if (StringUtils.isNotBlank(payChannel.getMchId())) criteria.andMchIdEqualTo(payChannel.getMchId());
      if (StringUtils.isNotBlank(payChannel.getChannelId())) criteria.andChannelIdEqualTo(payChannel.getChannelId());
    }
  }

  public CommonResult<Integer> createChannel(PayChannelBo payChannelBo) {
    String channelId = payChannelBo.getChannelId();
    String param = payChannelBo.getParam();

    if ("ALIPAY_MOBILE".equals(channelId) || "ALIPAY_PC"
      .equals(channelId) || "ALIPAY_WAP"
      .equals(channelId) || "ALIPAY_QR"
      .equals(channelId)) {
      JSONObject paramObj = null;
      try {
        paramObj = JSON.parseObject(param);
      } catch (Exception e) {
        _log.info("param is not json", new Object[0]);
      }
      if (paramObj != null) {
        paramObj.put("private_key", paramObj.getString("private_key").replaceAll(" ", "+"));
        paramObj.put("alipay_public_key", paramObj.getString("alipay_public_key").replaceAll(" ", "+"));
        payChannelBo.setParam(paramObj.toJSONString());
      }
    }

    PayChannel payChannel = (PayChannel)BeanUtil.copyProperties(payChannelBo, PayChannel.class);

    int result = addPayChannel(payChannel);
    _log.info("创建平台支付渠道,返回:{}", new Object[] { Integer.valueOf(result) });
    if (result > 0) {
      return CommonResult.success(Integer.valueOf(result));
    }
    return CommonResult.error("创建失败");
  }


  @Transactional(rollbackFor = {Exception.class})
  public CommonResult<Integer> createPlatformChannel(PayChannelBo payChannelBo) throws Exception {
    int result = createInnerChannel(payChannelBo);

    _log.info("创建平台支付渠道,返回:{}", new Object[] { Integer.valueOf(result) });
    if (result > 0) {
      return CommonResult.success(Integer.valueOf(result));
    }
    return CommonResult.error("创建失败");
  }

  public int createInnerChannel(PayChannelBo payChannelBo) throws Exception {
    if (payChannelBo == null) {
      return 0;
    }
    String mchId = payChannelBo.getMchId();
    MchInfo mchInfo = this.mchInfoService.selectByPrimaryKey(mchId);

    JSONObject payParam = JSONObject.parseObject(payChannelBo.getParam());
    String currency = payParam.getString("currency");
    CurrencyTypeEnum currencyTypeEnum = CurrencyTypeEnum.valueOf(currency.toUpperCase());

    SaCard trusteeshipCard = this.cardService.addTrusteeshipCard(mchInfo, currencyTypeEnum);
    if (trusteeshipCard == null)
    {
      throw new Exception("创建内部托管卡失败");
    }
    SaCard regulationCard = this.cardService.addRegulationCard(mchInfo, currencyTypeEnum);
    if (regulationCard == null)
    {
      throw new Exception("创建内部资管卡失败");
    }
    SaCard dumbCard = this.cardService.addDumbCard(mchInfo, currencyTypeEnum);
    if (dumbCard == null)
    {
      throw new Exception("创建内部冲销卡失败");
    }
    SaCard paymentCard = this.cardService.addMchCard(mchInfo, CardDataTypeEnum.PAYMENT, currencyTypeEnum, null, RunModeEnum.PUBLIC.getCode().intValue(), "1");
    if (paymentCard == null)
    {
      throw new Exception("创建内部收支卡失败");
    }

    JSONObject param = new JSONObject();
    List<PayChannel> records = new ArrayList<>();
    param.put("appid", "");
    param.put("currency", currencyTypeEnum.name().toLowerCase());
    param.put("private_key", "");
    param.put("public_key", "");
    param.put("isSandbox", Integer.valueOf(0));

    param.put("dumbAccount", dumbCard.getUserId());
    PayChannel innerChannel = new PayChannel();
    innerChannel.setMchId(mchId);
    innerChannel.setChannelMchId(mchId);
    innerChannel.setChannelName("INNER");
    innerChannel.setState(Byte.valueOf((byte)1));

    param.put("manageAccount", trusteeshipCard.getUserId());
    innerChannel.setParam(param.toJSONString());

    innerChannel.setChannelAccount(trusteeshipCard.getCardNumber());

    innerChannel.setChannelId("HL_" + currencyTypeEnum.name() + "_RECHARGE");

    innerChannel.setRemark(currencyTypeEnum.getDesc() + "内部充值支付渠道");
    records.add((PayChannel)BeanUtil.copyProperties(innerChannel, PayChannel.class));
    param.put("manageAccount", trusteeshipCard.getUserId());
    innerChannel.setParam(param.toJSONString());

    innerChannel.setChannelAccount(trusteeshipCard.getCardNumber());

    innerChannel.setChannelId("HL_" + currencyTypeEnum.name() + "_WITHDRAW");

    innerChannel.setRemark(currencyTypeEnum.getDesc() + "内部提现支付渠道");
    records.add((PayChannel)BeanUtil.copyProperties(innerChannel, PayChannel.class));

    param.put("manageAccount", trusteeshipCard.getUserId());
    innerChannel.setParam(param.toJSONString());

    innerChannel.setChannelAccount(trusteeshipCard.getCardNumber());

    innerChannel.setChannelId("HL_" + currencyTypeEnum.name() + "_TRANS");

    innerChannel.setRemark(currencyTypeEnum.getDesc() + "内部转账支付渠道");
    records.add((PayChannel)BeanUtil.copyProperties(innerChannel, PayChannel.class));

    param.put("manageAccount", trusteeshipCard.getUserId());
    innerChannel.setParam(param.toJSONString());

    innerChannel.setChannelAccount(paymentCard.getCardNumber());

    innerChannel.setChannelId("HL_" + currencyTypeEnum.name() + "_PAY");

    innerChannel.setRemark(currencyTypeEnum.getDesc() + "内部即时到账支付渠道");
    records.add((PayChannel)BeanUtil.copyProperties(innerChannel, PayChannel.class));

    String channelId = payChannelBo.getChannelId();
    String paramStr = payChannelBo.getParam();

    if ("ALIPAY_MOBILE".equals(channelId) || "ALIPAY_PC"
      .equals(channelId) || "ALIPAY_WAP"
      .equals(channelId) || "ALIPAY_QR"
      .equals(channelId)) {
      JSONObject paramObj = null;
      try {
        paramObj = JSON.parseObject(paramStr);
      } catch (Exception e) {
        _log.info("param is not json", new Object[0]);
      }
      if (paramObj != null) {
        paramObj.put("private_key", paramObj.getString("private_key").replaceAll(" ", "+"));
        paramObj.put("alipay_public_key", paramObj.getString("alipay_public_key").replaceAll(" ", "+"));
        payChannelBo.setParam(paramObj.toJSONString());
      }
    }

    PayChannel payChannel = (PayChannel)BeanUtil.copyProperties(payChannelBo, PayChannel.class);
    records.add(payChannel);
    return listInsert(records);
  }


  @Transactional(rollbackFor = {Exception.class})
  public int createInnerChannel(String mchId, String currency) throws Exception {
    MchInfo mchInfo = this.mchInfoService.selectByPrimaryKey(mchId);

    CurrencyTypeEnum currencyTypeEnum = CurrencyTypeEnum.valueOf(currency.toUpperCase());

    SaCard trusteeshipCard = this.cardService.addTrusteeshipCard(mchInfo, currencyTypeEnum);
    if (trusteeshipCard == null)
    {
      throw new Exception("创建内部托管卡失败");
    }
    SaCard regulationCard = this.cardService.addRegulationCard(mchInfo, currencyTypeEnum);
    if (regulationCard == null)
    {
      throw new Exception("创建内部资管卡失败");
    }
    SaCard dumbCard = this.cardService.addDumbCard(mchInfo, currencyTypeEnum);
    if (dumbCard == null)
    {
      throw new Exception("创建内部冲销卡失败");
    }
    SaCard paymentCard = this.cardService.addMchCard(mchInfo, CardDataTypeEnum.PAYMENT, currencyTypeEnum, null, RunModeEnum.PUBLIC.getCode().intValue(), "1");
    if (paymentCard == null)
    {
      throw new Exception("创建内部收支卡失败");
    }
    JSONObject param = new JSONObject();
    List<PayChannel> records = new ArrayList<>();
    param.put("appid", "");
    param.put("currency", currencyTypeEnum.name().toLowerCase());
    param.put("private_key", "");
    param.put("public_key", "");
    param.put("isSandbox", Integer.valueOf(0));

    param.put("dumbAccount", dumbCard.getUserId());
    PayChannel innerChannel = new PayChannel();
    innerChannel.setMchId(mchId);
    innerChannel.setChannelMchId(mchId);
    innerChannel.setChannelName("INNER");
    innerChannel.setState(Byte.valueOf((byte)1));

    param.put("manageAccount", trusteeshipCard.getUserId());
    innerChannel.setParam(param.toJSONString());

    innerChannel.setChannelAccount(trusteeshipCard.getCardNumber());

    innerChannel.setChannelId("HL_" + currencyTypeEnum.name() + "_RECHARGE");

    innerChannel.setRemark(currencyTypeEnum.getDesc() + "内部充值支付渠道");
    innerChannel.setId(Integer.valueOf(100000001));
    records.add((PayChannel)BeanUtil.copyProperties(innerChannel, PayChannel.class));

    param.put("manageAccount", trusteeshipCard.getUserId());
    innerChannel.setParam(param.toJSONString());

    innerChannel.setChannelAccount(trusteeshipCard.getCardNumber());

    innerChannel.setChannelId("HL_" + currencyTypeEnum.name() + "_WITHDRAW");

    innerChannel.setRemark(currencyTypeEnum.getDesc() + "内部提现支付渠道");
    innerChannel.setId(Integer.valueOf(100000002));
    records.add((PayChannel)BeanUtil.copyProperties(innerChannel, PayChannel.class));

    param.put("manageAccount", trusteeshipCard.getUserId());
    innerChannel.setParam(param.toJSONString());

    innerChannel.setChannelAccount(trusteeshipCard.getCardNumber());

    innerChannel.setChannelId("HL_" + currencyTypeEnum.name() + "_TRANS");

    innerChannel.setRemark(currencyTypeEnum.getDesc() + "内部转账支付渠道");
    innerChannel.setId(Integer.valueOf(100000003));
    records.add((PayChannel)BeanUtil.copyProperties(innerChannel, PayChannel.class));
    param.put("manageAccount", trusteeshipCard.getUserId());
    innerChannel.setParam(param.toJSONString());

    innerChannel.setChannelAccount(paymentCard.getCardNumber());

    innerChannel.setChannelId("HL_" + currencyTypeEnum.name() + "_PAY");

    innerChannel.setRemark(currencyTypeEnum.getDesc() + "内部即时到账支付渠道");
    innerChannel.setId(Integer.valueOf(100000004));
    records.add((PayChannel)BeanUtil.copyProperties(innerChannel, PayChannel.class));

    records.add((PayChannel)BeanUtil.copyProperties(innerChannel, PayChannel.class));

    return listInsert(records);
  }

  public int countByExample(PayChannelExample example) {
    Integer count = Integer.valueOf(this.payChannelMapper.countByExample(example));

    return count.intValue();
  }

  public int deleteByPrimaryKey(Integer id) throws NoSuchMethodException {
    int r = this.payChannelMapper.deleteByPrimaryKey(id);
    deleteSimpleCache(id.toString());
    return r;
  }


  public int insert(PayChannel record) {
    Integer res = Integer.valueOf(this.payChannelMapper.insert(record));
    if (res.intValue() > 0) {
      putToSimpleCache(record);
    }
    return res.intValue();
  }


  public int insertSelective(PayChannel record) {
    Integer res = Integer.valueOf(this.payChannelMapper.insertSelective(record));
    if (res.intValue() > 0) {
      putToSimpleCache(record);
    }
    return res.intValue();
  }


  public List<PayChannel> selectByExample(PayChannelExample example) {
    List<PayChannel> list = this.payChannelMapper.selectByExample(example);
    for (PayChannel pc : list) {
      putToSimpleCache(pc);
    }

    return list;
  }

  public PayChannel selectByPrimaryKey(Integer id) throws NoSuchMethodException {
    PayChannel payChannel = (PayChannel)getSimpleDataByPrimaryKey(id.toString());
    if (payChannel != null) {
      return payChannel;
    }
    payChannel = this.payChannelMapper.selectByPrimaryKey(id);

    if (payChannel != null) {
      putToSimpleCache(payChannel);
    }
    return payChannel;
  }

  public PayChannel selectByPrimaryKeyForAudit(Integer id) {
    PayChannel payChannel = this.payChannelMapper.selectByPrimaryKeyForAudit(id);

    return payChannel;
  }

  public int updateByPrimaryKeySelective(PayChannel record) {
    Integer res = Integer.valueOf(this.payChannelMapper.updateByPrimaryKeySelective(record));
    if (res.intValue() > 0) {
      updateSimpleCache(record);
    }
    return res.intValue();
  }

  public int updateByPrimaryKeySelectiveForAudit(PayChannel record) {
    Integer res = Integer.valueOf(this.payChannelMapper.updateByPrimaryKeySelectiveForAudit(record));
    return res.intValue();
  }

  public int updateByPrimaryKey(PayChannel record) {
    Integer res = Integer.valueOf(this.payChannelMapper.updateByPrimaryKey(record));
    if (res.intValue() > 0) {
      updateSimpleCache(record);
    }
    return res.intValue();
  }

  public String getNameSpace() {
    return "PAY-CHANNEL";
  }

  public String getSimplePrimaryKey(PayChannel data) {
    return data.getId().toString();
  }

  public List<String> getPrimaryKeyList(PayChannel data) {
    return null;
  }

  public String getSimpleUnionKey(PayChannel data) {
    return data.getChannelMchId() + ":" + data
      .getChannelId();
  }

  public List<TreeMap<String, String>> getUnionKeyList(PayChannel data) {
    return null;
  }

  public Map<String, Double> getScoreMap(PayChannel data) {
    return null;
  }

  public Map<String, String> getGroupMap(PayChannel data) {
    return null;
  }

  public Map<String, SortTypeEnum> getDefaultSortMap() {
    return null;
  }


  public Class<PayChannel> getType() {
    return PayChannel.class;
  }

  public String getQueryExpFromExample(PayChannel data) {
    return null;
  }
}

