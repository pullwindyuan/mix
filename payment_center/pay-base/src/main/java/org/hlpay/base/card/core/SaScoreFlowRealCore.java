package org.hlpay.base.card.core;

import com.alibaba.fastjson.JSONObject;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.hlpay.base.card.common.CommonUtils;
import org.hlpay.base.card.ctrl.SaScoreFlowRealController;
import org.hlpay.base.model.SaScoreFlow;
import org.hlpay.base.plugin.PageModel;
import org.hlpay.common.util.MyLog;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;









@Controller
@RequestMapping({"/api/saScoreFlow"})
public class SaScoreFlowRealCore
  extends CommonUtils
{
  private static final MyLog _log = MyLog.getLog(SaScoreFlowRealCore.class);



  @Resource
  private SaScoreFlowRealController saScoreFlowRealController;




  @RequestMapping({"countExchange"})
  @ResponseBody
  public String countExchange(String codeStr) {
    PageModel pageModel = new PageModel();
    String retStr = "";
    try {
      List<String> list = new ArrayList<>();
      String[] codeArray = codeStr.split(",");
      for (int i = 0; i < codeArray.length; i++) {
        list.add(codeArray[i]);
      }



      retStr = this.saScoreFlowRealController.countExchange(list);
      return retStr;
    } catch (Exception e) {
      e.printStackTrace();
      _log.info("积分流水：{}", new Object[] { "删除失败" });
      pageModel.setCode(Integer.valueOf(1));
      pageModel.setMsg("fail");
      return JSONObject.toJSONString(pageModel);
    }
  }







  @RequestMapping({"delete"})
  @ResponseBody
  public String delete(String scoreFlowId) {
    PageModel pageModel = new PageModel();
    String retStr = "";
    try {
      retStr = this.saScoreFlowRealController.delete(scoreFlowId);
      return retStr;
    } catch (Exception e) {
      e.printStackTrace();
      _log.info("积分流水：{}", new Object[] { "删除失败" });
      pageModel.setCode(Integer.valueOf(1));
      pageModel.setMsg("fail");
      return JSONObject.toJSONString(pageModel);
    }
  }









  @RequestMapping(value = {"list"}, method = {RequestMethod.POST})
  @ResponseBody
  public String select(SaScoreFlow saScoreFlow, Integer start, Integer end) {
    PageModel pageModel = new PageModel();
    String retStr = "";
    try {
      retStr = this.saScoreFlowRealController.select(saScoreFlow, start, end);
      _log.info("积分流水：{}", new Object[] { "查询成功" });
      return retStr;
    } catch (Exception e) {
      e.printStackTrace();
      _log.info("积分流水：{}", new Object[] { "查询失败" });
      pageModel.setCode(Integer.valueOf(1));
      pageModel.setMsg("fail");
      return JSONObject.toJSONString(pageModel);
    }
  }








  @RequestMapping(value = {"webList"}, method = {RequestMethod.POST})
  @ResponseBody
  public String webSelect(SaScoreFlow saScoreFlow, Integer start, Integer end) {
    PageModel pageModel = new PageModel();
    String retStr = "";
    try {
      retStr = this.saScoreFlowRealController.webSelect(saScoreFlow, start, end);
      _log.info("积分流水：{}", new Object[] { "查询成功" });
      return retStr;
    } catch (Exception e) {
      e.printStackTrace();
      _log.info("积分流水：{}", new Object[] { "查询失败" });
      pageModel.setCode(Integer.valueOf(1));
      pageModel.setMsg("fail");
      return JSONObject.toJSONString(pageModel);
    }
  }








  @RequestMapping(value = {"partnerList"}, method = {RequestMethod.POST})
  @ResponseBody
  public String partnerSelect(SaScoreFlow saScoreFlow, Integer start, Integer end) {
    PageModel pageModel = new PageModel();
    String retStr = "";
    try {
      retStr = this.saScoreFlowRealController.partnerSelect(saScoreFlow, start, end);
      _log.info("合伙人收益日期明细查询成功", new Object[0]);
      return retStr;
    } catch (Exception e) {
      e.printStackTrace();
      _log.info("合伙人收益日期明细查询失败", new Object[0]);
      pageModel.setCode(Integer.valueOf(1));
      pageModel.setMsg("fail");
      return JSONObject.toJSONString(pageModel);
    }
  }








  @RequestMapping(value = {"partnerDayList"}, method = {RequestMethod.POST})
  @ResponseBody
  public String partnerDayList(SaScoreFlow saScoreFlow, Integer start, Integer end) {
    PageModel pageModel = new PageModel();
    String retStr = "";
    try {
      retStr = this.saScoreFlowRealController.partnerEveryDay(saScoreFlow, start, end);
      _log.info("合伙人每日收益明细查询成功", new Object[0]);
      return retStr;
    } catch (Exception e) {
      e.printStackTrace();
      _log.info("合伙人每日收益明细查询失败", new Object[0]);
      pageModel.setCode(Integer.valueOf(1));
      pageModel.setMsg("fail");
      return JSONObject.toJSONString(pageModel);
    }
  }






  @RequestMapping({"total"})
  @ResponseBody
  public String total(SaScoreFlow saScoreFlow) {
    PageModel pageModel = new PageModel();
    String retStr = "";
    try {
      retStr = this.saScoreFlowRealController.total(saScoreFlow);
      _log.info("累计/昨日收益：{}", new Object[] { "查询成功" });
      return retStr;
    } catch (Exception e) {
      e.printStackTrace();
      _log.info("累计/昨日收益：{}", new Object[] { "查询失败" });
      pageModel.setCode(Integer.valueOf(1));
      pageModel.setMsg("fail");
      return JSONObject.toJSONString(pageModel);
    }
  }






  @RequestMapping({"yestList"})
  @ResponseBody
  public String yestList(SaScoreFlow saScoreFlow) {
    PageModel pageModel = new PageModel();
    String retStr = "";
    try {
      retStr = this.saScoreFlowRealController.yestList(saScoreFlow);
      _log.info("会员昨日收益以及昨日支出：{}", new Object[] { "查询成功" });
      return retStr;
    } catch (Exception e) {
      e.printStackTrace();
      _log.info("会员昨日收益以及昨日支出：{}", new Object[] { "查询失败" });
      pageModel.setCode(Integer.valueOf(1));
      pageModel.setMsg("fail");
      return JSONObject.toJSONString(pageModel);
    }
  }






  @RequestMapping({"partnerFlowCount"})
  @ResponseBody
  public String partnerFlowCount(SaScoreFlow saScoreFlow) {
    PageModel pageModel = new PageModel();
    String retStr = "";
    try {
      retStr = this.saScoreFlowRealController.partnerFlowCount(saScoreFlow);
      _log.info("合伙人发展会员数量消费总数查询成功", new Object[0]);
      return retStr;
    } catch (Exception e) {
      e.printStackTrace();
      _log.info("合伙人发展会员数量消费总数查询失败", new Object[0]);
      pageModel.setCode(Integer.valueOf(1));
      pageModel.setMsg("fail");
      return JSONObject.toJSONString(pageModel);
    }
  }
}
