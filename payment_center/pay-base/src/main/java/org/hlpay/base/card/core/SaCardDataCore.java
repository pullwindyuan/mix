package org.hlpay.base.card.core;

import com.alibaba.fastjson.JSONObject;
import javax.annotation.Resource;
import org.hlpay.base.card.ctrl.SaCardDataController;
import org.hlpay.base.model.SaCardData;
import org.hlpay.base.plugin.PageModel;
import org.hlpay.common.util.MyLog;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;








@Controller
@RequestMapping({"/api/saCardData"})
public class SaCardDataCore
{
  private static final MyLog _log = MyLog.getLog(SaCardDataCore.class);



  @Resource
  private SaCardDataController saCardDataController;




  @RequestMapping(value = {"save"}, method = {RequestMethod.POST})
  @ResponseBody
  public String save(SaCardData saCardData) {
    PageModel pageModel = new PageModel();
    String retStr = "";
    try {
      retStr = this.saCardDataController.save(saCardData);
    } catch (Exception e) {
      e.printStackTrace();
      _log.info("卡种数据：{}", new Object[] { "添加失败" });
      pageModel.setCode(Integer.valueOf(1));
      pageModel.setMsg("fail");
      return JSONObject.toJSONString(pageModel);
    }
    return retStr;
  }








  @RequestMapping(value = {"list"}, method = {RequestMethod.POST})
  @ResponseBody
  public String select(SaCardData saCardData, Integer start, Integer end) {
    PageModel pageModel = new PageModel();
    String retStr = "";
    try {
      retStr = this.saCardDataController.select(start, end, saCardData);
      _log.info("卡种数据：{}", new Object[] { "查询成功" });
      return retStr;
    } catch (Exception e) {
      e.printStackTrace();
      _log.info("卡种数据：{}", new Object[] { "查询失败" });
      pageModel.setCode(Integer.valueOf(1));
      pageModel.setMsg("fail");
      return JSONObject.toJSONString(pageModel);
    }
  }
}
