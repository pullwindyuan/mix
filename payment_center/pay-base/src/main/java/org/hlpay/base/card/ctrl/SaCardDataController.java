 package org.hlpay.base.card.ctrl;

 import com.alibaba.fastjson.JSONObject;
 import java.util.List;
 import org.hlpay.base.card.service.impl.SaCardDataServiceImpl;
 import org.hlpay.base.model.SaCardData;
 import org.hlpay.base.plugin.PageModel;
 import org.hlpay.common.util.MyLog;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.stereotype.Component;





 @Component
 public class SaCardDataController
 {
   private static final MyLog _log = MyLog.getLog(SaCardDataController.class);

   @Autowired
   private SaCardDataServiceImpl saCardDataService;

   public String save(SaCardData saCardData) throws Exception {
     PageModel pageModel = new PageModel();
     this.saCardDataService.baseInsert(saCardData);
     pageModel.setCode(Integer.valueOf(0));
     pageModel.setMsg("success");
     return JSONObject.toJSONString(pageModel);
   }

   public String select(Integer start, Integer end, SaCardData saCardData) throws Exception {
     PageModel pageModel = new PageModel();
     List<SaCardData> list = this.saCardDataService.baseLimitSelect(start, end, saCardData);
     pageModel.setCode(Integer.valueOf(0));
     pageModel.setMsg("success");
     pageModel.setList(list);
     return JSONObject.toJSONString(pageModel);
   }
 }

