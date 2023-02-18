 package org.hlpay.base.card.ctrl;

 import com.alibaba.fastjson.JSONObject;
 import java.text.SimpleDateFormat;
 import java.util.Calendar;
 import java.util.Date;
 import java.util.List;
 import org.hlpay.base.card.common.CommonUtils;
 import org.hlpay.base.card.service.impl.SaCardServiceImpl;
 import org.hlpay.base.card.service.impl.SaScoreFlowServiceRealImpl;
 import org.hlpay.base.model.SaCard;
 import org.hlpay.base.model.SaScoreFlow;
 import org.hlpay.base.plugin.PageModel;
 import org.hlpay.common.util.MyLog;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.stereotype.Component;




 @Component
 public class SaScoreFlowRealController
   extends CommonUtils
 {
   private static final MyLog _log = MyLog.getLog(SaScoreFlowRealController.class);

   @Autowired
   private SaScoreFlowServiceRealImpl saScoreFlowRealService;

   @Autowired
   private SaCardServiceImpl saCardService;

   public String countExchange(List<String> list) throws Exception {
     PageModel pageModel = new PageModel();
     List<SaScoreFlow> retList = this.saScoreFlowRealService.countExchange(list);
     pageModel.setCode(Integer.valueOf(0));
     pageModel.setMsg("success");
     pageModel.setList(retList);
     return JSONObject.toJSONString(pageModel);
   }

   public String delete(String scoreFlowId) throws Exception {
     PageModel pageModel = new PageModel();
     int result = this.saScoreFlowRealService.baseDelete(scoreFlowId);
     pageModel.setCode(Integer.valueOf(0));
     pageModel.setMsg("success");
     return JSONObject.toJSONString(pageModel);
   }

   public String select(SaScoreFlow saScoreFlow, Integer start, Integer end) throws Exception {
     PageModel pageModel = new PageModel();
     List<SaScoreFlow> list = this.saScoreFlowRealService.baseLimitSelect(start, end, saScoreFlow);
     Integer count = this.saScoreFlowRealService.count();
     pageModel.setCode(Integer.valueOf(0));
     pageModel.setMsg("success");
     pageModel.setList(list);
     pageModel.setCount(count);
     return JSONObject.toJSONString(pageModel);
   }

   public String webSelect(SaScoreFlow saScoreFlow, Integer start, Integer end) throws Exception {
     PageModel pageModel = new PageModel();
     List<SaScoreFlow> list = this.saScoreFlowRealService.limitList(start, end, saScoreFlow);
     Integer count = this.saScoreFlowRealService.count();
     pageModel.setCode(Integer.valueOf(0));
     pageModel.setMsg("success");
     pageModel.setList(list);
     pageModel.setCount(count);
     return JSONObject.toJSONString(pageModel);
   }


   public String partnerSelect(SaScoreFlow saScoreFlow, Integer start, Integer end) throws Exception {
     PageModel pageModel = new PageModel();
     JSONObject jo = new JSONObject();
     SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
     Calendar calendar = Calendar.getInstance();
     calendar.setTime(new Date());
     calendar.add(5, -1);
     saScoreFlow.setTripTime(sdf.format(calendar.getTime()));
     Long y = this.saScoreFlowRealService.partnerYesterTotal(saScoreFlow);
     saScoreFlow.setScoreFlowDirection("1");
     Long c = this.saScoreFlowRealService.partnerCount(saScoreFlow);
     jo.put("count", c);
     jo.put("yesterday", y);
     List<SaScoreFlow> list = this.saScoreFlowRealService.partnerEveryday(start, end, saScoreFlow);
     pageModel.setCode(Integer.valueOf(0));
     pageModel.setMsg("success");
     pageModel.setList(list);
     pageModel.setObj(jo);
     return JSONObject.toJSONString(pageModel);
   }


   public String partnerEveryDay(SaScoreFlow saScoreFlow, Integer start, Integer end) throws Exception {
     PageModel pageModel = new PageModel();
     List<SaScoreFlow> list = this.saScoreFlowRealService.baseLimitSelect(start, end, saScoreFlow);
     JSONObject jo = new JSONObject();
     saScoreFlow.setScoreFlowDirection("1");
     Long c = this.saScoreFlowRealService.partnerCount(saScoreFlow);


     SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
     Calendar calendar = Calendar.getInstance();
     calendar.setTime(new Date());
     calendar.add(5, -1);
     saScoreFlow.setTripTime(sdf.format(calendar.getTime()));
     Long y = this.saScoreFlowRealService.partnerYesterTotal(saScoreFlow);
     jo.put("count", c);
     jo.put("expend", y);
     pageModel.setCode(Integer.valueOf(0));
     pageModel.setMsg("success");
     pageModel.setList(list);
     pageModel.setObj(jo);
     return JSONObject.toJSONString(pageModel);
   }

   public String total(SaScoreFlow saScoreFlow) throws Exception {
     PageModel pageModel = new PageModel();
     JSONObject jo = new JSONObject();
     SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
     Calendar calendar = Calendar.getInstance();
     calendar.setTime(new Date());
     calendar.add(5, -1);
     saScoreFlow.setTripTime(sdf.format(calendar.getTime()));
     saScoreFlow.setScoreFlowDirection("1");
     Long y = this.saScoreFlowRealService.yesterTotal(saScoreFlow);
     SaCard saCard = new SaCard();
     saCard.setUserId(saScoreFlow.getUserId());
     saCard = this.saCardService.baseInfo(saCard);
     Long c = saCard.getRemainPart();
     jo.put("count", c);
     jo.put("yesterday", y);
     pageModel.setCode(Integer.valueOf(0));
     pageModel.setMsg("success");
     pageModel.setObj(jo);
     return JSONObject.toJSONString(pageModel);
   }

   public String yestList(SaScoreFlow saScoreFlow) throws Exception {
     PageModel pageModel = new PageModel();
     JSONObject jo = new JSONObject();
     SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
     Calendar calendar = Calendar.getInstance();
     calendar.setTime(new Date());
     calendar.add(5, -1);
     saScoreFlow.setTripTime(sdf.format(calendar.getTime()));
     saScoreFlow.setScoreFlowDirection("1");
     Long y = this.saScoreFlowRealService.yesterTotal(saScoreFlow);
     saScoreFlow.setScoreFlowDirection("0");
     Long c = this.saScoreFlowRealService.yesterTotal(saScoreFlow);
     jo.put("s", y);
     jo.put("c", c);
     pageModel.setCode(Integer.valueOf(0));
     pageModel.setMsg("success");
     pageModel.setObj(jo);
     return JSONObject.toJSONString(pageModel);
   }


   public String partnerFlowCount(SaScoreFlow saScoreFlow) throws Exception {
     PageModel pageModel = new PageModel();
     Integer c = this.saScoreFlowRealService.partnerFlowCount(saScoreFlow);
     pageModel.setCode(Integer.valueOf(0));
     pageModel.setMsg("success");
     pageModel.setCount(c);
     return JSONObject.toJSONString(pageModel);
   }
 }
