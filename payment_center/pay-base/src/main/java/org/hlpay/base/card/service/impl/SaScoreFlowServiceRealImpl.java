package org.hlpay.base.card.service.impl;

import com.github.pagehelper.PageHelper;
import java.util.List;
import javax.annotation.Resource;
import org.apache.ibatis.annotations.Param;
import org.hlpay.base.card.service.SaScoreFlowRealService;
import org.hlpay.base.mapper.SaScoreFlowRealMapper;
import org.hlpay.base.model.SaScoreFlow;
import org.springframework.stereotype.Service;




@Service
public class SaScoreFlowServiceRealImpl
  implements SaScoreFlowRealService
{
  @Resource
  private SaScoreFlowRealMapper saScoreFlowRealMapper;

  public int baseInsert(SaScoreFlow saScoreFlow) throws Exception {
    return this.saScoreFlowRealMapper.baseInsert(saScoreFlow);
  }


  public int listInsert(@Param("list") List<SaScoreFlow> list) throws Exception {
    return this.saScoreFlowRealMapper.listInsert(list);
  }


  public int baseDelete(String s) throws Exception {
    return this.saScoreFlowRealMapper.baseDelete(s);
  }



  public int baseUpdate(SaScoreFlow saScoreFlow) throws Exception {
    return this.saScoreFlowRealMapper.baseUpdate(saScoreFlow);
  }


  public List<SaScoreFlow> selectList(SaScoreFlow saScoreFlow) throws Exception {
    return this.saScoreFlowRealMapper.baseLimitSelect(saScoreFlow);
  }


  public List<SaScoreFlow> baseLimitSelect(Integer start, Integer end, SaScoreFlow saScoreFlow) throws Exception {
    PageHelper.offsetPage(start.intValue(), end.intValue(), false);
    return this.saScoreFlowRealMapper.baseLimitSelect(saScoreFlow);
  }



  public SaScoreFlow baseInfo(SaScoreFlow saScoreFlow) throws Exception {
    return (SaScoreFlow)this.saScoreFlowRealMapper.baseInfo(saScoreFlow);
  }


  public Long yesterTotal(SaScoreFlow saScoreFlow) throws Exception {
    return this.saScoreFlowRealMapper.yesterTotal(saScoreFlow);
  }


  public String maxValue(String time) throws Exception {
    return this.saScoreFlowRealMapper.maxValue(time);
  }


  public Long partnerYesterTotal(SaScoreFlow saScoreFlow) throws Exception {
    return this.saScoreFlowRealMapper.partnerYesterTotal(saScoreFlow);
  }


  public Long partnerCount(SaScoreFlow saScoreFlow) throws Exception {
    return this.saScoreFlowRealMapper.partnerCount(saScoreFlow);
  }


  public List<SaScoreFlow> partnerEveryday(Integer start, Integer end, SaScoreFlow saScoreFlow) {
    PageHelper.offsetPage(start.intValue(), end.intValue(), false);
    return this.saScoreFlowRealMapper.partnerEveryday(saScoreFlow);
  }


  public Integer partnerFlowCount(SaScoreFlow saScoreFlow) throws Exception {
    return this.saScoreFlowRealMapper.partnerFlowCount(saScoreFlow);
  }


  public Integer count() throws Exception {
    return this.saScoreFlowRealMapper.count();
  }


  public List<SaScoreFlow> limitList(Integer start, Integer end, SaScoreFlow scoreFlow) throws Exception {
    PageHelper.offsetPage(start.intValue(), end.intValue(), false);
    return this.saScoreFlowRealMapper.limitList(scoreFlow);
  }


  public List<SaScoreFlow> countExchange(List<String> list) throws Exception {
    return this.saScoreFlowRealMapper.countExchange(list);
  }
}





