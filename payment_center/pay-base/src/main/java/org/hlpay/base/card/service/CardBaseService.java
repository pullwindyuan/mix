package org.hlpay.base.card.service;

import com.github.pagehelper.PageHelper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.hlpay.base.card.common.CommonUtils;
import org.hlpay.base.card.common.PageBean;
import org.hlpay.base.mapper.SaDealRecordMapper;
import org.hlpay.base.mapper.SaOrderMapper;
import org.hlpay.base.mapper.SaScoreFlowMapper;
import org.hlpay.base.mapper.SaScoreTypeMapper;
import org.hlpay.base.mapper.SaScoreTypeUserMapper;
import org.hlpay.base.mapper.SaSettleConfigMapper;
import org.hlpay.base.mapper.SysProxyUserMapper;
import org.hlpay.base.mapper.SysScoreSettleBillMapper;
import org.hlpay.base.mapper.SysUserMapper;
import org.hlpay.base.model.SaCard;
import org.hlpay.base.model.SaDealRecord;
import org.hlpay.base.model.SaOrder;
import org.hlpay.base.model.SaScoreFlow;
import org.hlpay.base.model.SaScoreType;
import org.hlpay.base.model.SaScoreTypeUser;
import org.hlpay.base.model.SaSettleConfig;
import org.hlpay.base.model.SysProxyUser;
import org.hlpay.base.model.SysScoreSettleBill;
import org.hlpay.base.model.SysUser;
import org.hlpay.base.service.CardService;
import org.hlpay.common.util.MyLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class CardBaseService<T>
  extends CommonUtils<T>
{
  private final MyLog _log = MyLog.getLog(CardBaseService.class);

  @Resource
  private SaDealRecordMapper saDealRecordMapper;

  @Resource
  private SaOrderMapper saOrderMapper;

  @Resource
  private SaScoreFlowMapper saScoreFlowMapper;

  @Resource
  private SysUserMapper sysUserMapper;

  @Autowired
  private CardService cardService;

  @Resource
  private SysScoreSettleBillMapper sysScoreSettleBillMapper;

  @Resource
  private SysProxyUserMapper sysProxyUserMapper;

  @Resource
  private SaSettleConfigMapper saSettleConfigMapper;

  @Resource
  private SaScoreTypeMapper saScoreTypeMapper;

  @Resource
  private SaScoreTypeUserMapper saScoreTypeUserMapper;


  public int addSaDealRecord(SaDealRecord saDealRecord) throws Exception {
    int result = this.saDealRecordMapper.insertSaDealRecord(saDealRecord);
    return result;
  }

  public int addErrorSaDealRecord(SaDealRecord saDealRecord) throws Exception {
    int result = this.saDealRecordMapper.insertErrorSaDealRecord(saDealRecord);
    return result;
  }

  public int deleteSaDealRecord(String dealRecordId) throws Exception {
    int result = this.saDealRecordMapper.delete(dealRecordId);
    return result;
  }

  public int updateSaDealRecord(SaDealRecord saDealRecord) throws Exception {
    int result = this.saDealRecordMapper.update(saDealRecord);
    return result;
  }

  public int updateDeal(Map<String, String> map) throws Exception {
    int result = this.saDealRecordMapper.updateSaDealRecord(map);
    return result;
  }

  public PageBean selectSaDealRecord(Integer start, Integer end, SaDealRecord saDealRecord) throws Exception {
    PageHelper.offsetPage(start.intValue(), end.intValue(), false);
    List<SaDealRecord> list = this.saDealRecordMapper.select(saDealRecord);
    int total = this.saDealRecordMapper.count(saDealRecord);
    return getInstance(start.intValue(), end.intValue(), total, list);
  }

  public List<SaDealRecord> selectSaDealRecords(SaDealRecord saDealRecord) throws Exception {
    return this.saDealRecordMapper.select(saDealRecord);
  }

  public List<SaDealRecord> infoSaDealRecord(String dealRecordId) throws Exception {
    List<SaDealRecord> list = this.saDealRecordMapper.info(dealRecordId);
    return list;
  }

  public List<Map<String, Integer>> totalDeal(SaDealRecord saDealRecord) throws Exception {
    saDealRecord.setDealType("0");
    int expend = this.saDealRecordMapper.totalDeal(saDealRecord);
    saDealRecord.setDealType("1");
    int income = this.saDealRecordMapper.totalDeal(saDealRecord);
    saDealRecord.setDealType(null);
    int count = this.saDealRecordMapper.count(saDealRecord);
    Map<String, Integer> map = new HashMap<>();
    map.put("count", Integer.valueOf(count));
    map.put("expend", Integer.valueOf(expend));
    map.put("income", Integer.valueOf(income));
    List<Map<String, Integer>> list = new ArrayList<>();
    list.add(map);
    return list;
  }



  public int addSaOrder(SaOrder dealOrder) throws Exception {
    int result = this.saOrderMapper.insertSaOrder(dealOrder);
    return result;
  }

  public int deleteSaOrder(String dealOrderId) throws Exception {
    int result = this.saOrderMapper.deleteSaOrder(dealOrderId);
    return result;
  }

  public int updateSaOrder(SaOrder dealOrder) throws Exception {
    int result = this.saOrderMapper.updateSaOrder(dealOrder);
    return result;
  }

  public PageBean selectSaOrder(int start, int end, String userId) throws Exception {
    PageHelper.offsetPage(start, end, false);
    List<SaOrder> list = this.saOrderMapper.selectSaOrder(userId);
    return getInstance(start, end, list.size(), list);
  }

  public List<SaOrder> infoSaOrder(String dealOrderId) throws Exception {
    return this.saOrderMapper.infoSaOrder(dealOrderId);
  }

  public int updateOrder(Map<String, String> map) throws Exception {
    return this.saOrderMapper.update(map);
  }



  public int addSaScoreFlow(SaScoreFlow saScoreFlow) throws Exception {
    int result = this.saScoreFlowMapper.insertSaScoreFlow(saScoreFlow);
    return result;
  }

  public int deleteSaScoreFlow(String scoreFlowId) throws Exception {
    return this.saScoreFlowMapper.deleteSaScoreFlow(scoreFlowId);
  }

  public int updateSaScoreFlow(SaScoreFlow saScoreFlow) throws Exception {
    return this.saScoreFlowMapper.updateSaScoreFlow(saScoreFlow);
  }

  public PageBean selectSaScoreFlow(SaScoreFlow saScoreFlow, Integer start, Integer end) throws Exception {
    PageHelper.offsetPage(start.intValue(), end.intValue(), false);
    List<SaScoreFlow> list = this.saScoreFlowMapper.selectSaScoreFlow(saScoreFlow);
    int total = this.saScoreFlowMapper.count(saScoreFlow);
    return getInstance(start.intValue(), end.intValue(), total, list);
  }

  public List<SaScoreFlow> infoSaScoreFlow(String scoreFlowId) throws Exception {
    return this.saScoreFlowMapper.infoSaScoreFlow(scoreFlowId);
  }

  public int noConfigScore(String userId) throws Exception {
    return this.saScoreFlowMapper.noConfigScore(userId);
  }

  public List<Map<String, Integer>> totalScore(SaScoreFlow saScoreFlow) throws Exception {
    saScoreFlow.setScoreFlowDirection("0");
    int expend = this.saScoreFlowMapper.totalScore(saScoreFlow);
    saScoreFlow.setScoreFlowDirection("1");
    int income = this.saScoreFlowMapper.totalScore(saScoreFlow);
    saScoreFlow.setScoreFlowDirection(null);
    int count = this.saScoreFlowMapper.count(saScoreFlow);
    Map<String, Integer> map = new HashMap<>();
    map.put("count", Integer.valueOf(count));
    map.put("expend", Integer.valueOf(expend));
    map.put("income", Integer.valueOf(income));
    List<Map<String, Integer>> list = new ArrayList<>();
    list.add(map);
    return list;
  }


  public List<String> countUserId() throws Exception {
    return this.saScoreFlowMapper.countUserId();
  }

  public long noConfigList(String userId) throws Exception {
    return this.saScoreFlowMapper.noConfigList(userId);
  }

  public int updateScoreFlow(Map<String, String> map) throws Exception {
    return this.saScoreFlowMapper.update(map);
  }

  public int updateRefund(SaScoreFlow saScoreFlow) {
    return this.saScoreFlowMapper.updateRefund(saScoreFlow);
  }



  public List<SysUser> infoSysUser(String loginAccount) throws Exception {
    return this.sysUserMapper.infoSysUser(loginAccount);
  }
  public SysUser infoSysUserByUserId(String userId) throws Exception {
    return this.sysUserMapper.info(userId);
  }



  public List<SysProxyUser> infoSysProxyUser(String proxyNumber) throws Exception {
    return this.sysProxyUserMapper.infoSysProxyUser(proxyNumber);
  }



  public List<SaCard> infoSaCard(String userId) throws Exception {
    return this.cardService.infoSaCard(userId);
  }

  public int updateSaCard(String cardNumber, String flag, String remainPart) throws Exception {
    return this.cardService.updateSaCard(cardNumber, flag, remainPart);
  }

  public int updateAll(SaCard saCard) throws Exception {
    return this.cardService.updateAll(saCard);
  }

  public int updateCardRemainPart(Map<String, Long> map) throws Exception {
    return this.cardService.update(map);
  }

  public int insertSaCard(SaCard saCard) throws Exception {
    return this.cardService.insertSaCard(saCard);
  }

  public PageBean listSaCard(int start, int end) throws Exception {
    PageHelper.offsetPage(start, end, false);
    List<SaCard> list = this.cardService.listSaCard();
    return getInstance(start, end, list.size(), list);
  }



  public int insertSettleBill(SysScoreSettleBill scoreSettleBill) throws Exception {
    return this.sysScoreSettleBillMapper.insertSettleBill(scoreSettleBill);
  }

  public int deleteSettleBill(String billId) throws Exception {
    return this.sysScoreSettleBillMapper.deleteSettleBill(billId);
  }


  public int updateSettleBill(SysScoreSettleBill scoreSettleBill) throws Exception {
    return this.sysScoreSettleBillMapper.updateSettleBill(scoreSettleBill);
  }


  public List<SysScoreSettleBill> infoSettleBill(String billId) throws Exception {
    return this.sysScoreSettleBillMapper.infoSettleBill(billId);
  }

  public PageBean selectSettleBill(int start, int end, SysScoreSettleBill scoreSettleBill) throws Exception {
    PageHelper.offsetPage(start, end, false);
    List<SysScoreSettleBill> list = this.sysScoreSettleBillMapper.selectSettleBill(scoreSettleBill);
    int total = this.sysScoreSettleBillMapper.count(scoreSettleBill);
    return getInstance(start, end, total, list);
  }

  public List<SysScoreSettleBill> limitSettleBill(String userId) throws Exception {
    return this.sysScoreSettleBillMapper.limitSettleBill(userId);
  }


  public List<Map<String, Integer>> totalBill(SysScoreSettleBill scoreSettleBill) throws Exception {
    scoreSettleBill.setBillStatus(Character.valueOf('0'));
    int expend = this.sysScoreSettleBillMapper.totalBill(scoreSettleBill);
    scoreSettleBill.setBillStatus(Character.valueOf('1'));
    int income = this.sysScoreSettleBillMapper.totalBill(scoreSettleBill);
    scoreSettleBill.setBillStatus(null);
    int count = this.sysScoreSettleBillMapper.count(scoreSettleBill);
    Map<String, Integer> map = new HashMap<>();
    map.put("count", Integer.valueOf(count));
    map.put("expend", Integer.valueOf(expend));
    map.put("income", Integer.valueOf(income));
    List<Map<String, Integer>> list = new ArrayList<>();
    list.add(map);
    return list;
  }

  public int insertSettleBill(List<String> list, SysScoreSettleBill scoreSettleBill) throws Exception {
    return this.sysScoreSettleBillMapper.insert(list, scoreSettleBill);
  }

  public List<Map<String, String>> getBill(String createTime) throws Exception {
    return this.sysScoreSettleBillMapper.getBill(createTime);
  }




  public List<SaSettleConfig> selectSaSettleConfig(String userId) throws Exception {
    return this.saSettleConfigMapper.selectSaSettleConfig(userId);
  }

  public int save(SaSettleConfig saSettleConfig) {
    return this.saSettleConfigMapper.save(saSettleConfig);
  }



  public int insertSaScoreType(SaScoreType saScoreType) throws Exception {
    return this.saScoreTypeMapper.save(saScoreType);
  }
  public List<SaScoreType> selectSaScoreType(SaScoreType saScoreType) throws Exception {
    return this.saScoreTypeMapper.info(saScoreType);
  }

  public PageBean selectSaScoreType(SaScoreType saScoreType, int start, int end) throws Exception {
    PageHelper.offsetPage(start, end, false);
    List<SaScoreType> list = this.saScoreTypeMapper.select(saScoreType);
    return getInstance(start, end, list.size(), list);
  }



  public int insertSaScoreTypeUser(SaScoreTypeUser saScoreTypeUser) throws Exception {
    return this.saScoreTypeUserMapper.save(saScoreTypeUser);
  }

  public List<SaScoreTypeUser> infoSaScoreTypeUser(SaScoreTypeUser saScoreTypeUser) throws Exception {
    return this.saScoreTypeUserMapper.info(saScoreTypeUser);
  }
  public int updateSaScoreTypeUser(SaScoreTypeUser saScoreTypeUser) throws Exception {
    return this.saScoreTypeUserMapper.update(saScoreTypeUser);
  }
}
