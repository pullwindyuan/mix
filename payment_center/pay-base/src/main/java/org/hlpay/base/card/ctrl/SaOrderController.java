package org.hlpay.base.card.ctrl;

import com.alibaba.fastjson.JSONObject;
import java.util.List;
import javax.annotation.Resource;
import org.hlpay.base.card.common.CommonUtils;
import org.hlpay.base.card.service.impl.SaOrderServiceImpl;
import org.hlpay.base.model.SaOrder;
import org.hlpay.base.plugin.PageModel;
import org.hlpay.common.util.MyLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;




@Component
public class SaOrderController
  extends CommonUtils
{
  private static final MyLog _log = MyLog.getLog(SaOrderController.class);

  @Autowired
  private SaOrderServiceImpl saOrderService;

  @Resource
  private SaScoreFlowController saScoreFlowController;

  public String save(SaOrder dealOrder) throws Exception {
    PageModel pageModel = new PageModel();
    int result = this.saOrderService.baseInsert(dealOrder);
    pageModel.setCode(Integer.valueOf(0));
    pageModel.setMsg("success");
    return JSONObject.toJSONString(pageModel);
  }

  public String delete(String dealOrderId) throws Exception {
    PageModel pageModel = new PageModel();
    int result = this.saOrderService.baseDelete(dealOrderId);
    _log.info("订单删除成功,删除记录数：{}", new Object[] { Integer.valueOf(result) });
    pageModel.setCode(Integer.valueOf(0));
    pageModel.setMsg("success");
    return JSONObject.toJSONString(pageModel);
  }

  public String select(Integer start, Integer end, SaOrder saOrder) throws Exception {
    PageModel pageModel = new PageModel();
    List<SaOrder> list = this.saOrderService.baseLimitSelect(start, end, saOrder);
    pageModel.setCode(Integer.valueOf(0));
    pageModel.setMsg("success");
    pageModel.setList(list);
    return JSONObject.toJSONString(pageModel);
  }
}
