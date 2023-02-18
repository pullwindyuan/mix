package org.hlpay.base.card.ctrl;

import com.alibaba.fastjson.JSONObject;
import java.util.List;
import org.hlpay.base.card.common.CommonUtils;
import org.hlpay.base.card.service.impl.SaScoreFlowServiceImpl;
import org.hlpay.base.model.SaScoreFlow;
import org.hlpay.base.plugin.PageModel;
import org.hlpay.common.util.MyLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;









@Component
public class SaScoreFlowController
  extends CommonUtils
{
  private static final MyLog _log = MyLog.getLog(SaScoreFlowController.class);

  @Autowired
  private SaScoreFlowServiceImpl saScoreFlowService;

  public String delete(String scoreFlowId) throws Exception {
    PageModel pageModel = new PageModel();
    int result = this.saScoreFlowService.baseDelete(scoreFlowId);
    pageModel.setCode(Integer.valueOf(0));
    pageModel.setMsg("success");
    return JSONObject.toJSONString(pageModel);
  }

  public String select(SaScoreFlow saScoreFlow, Integer start, Integer end) throws Exception {
    PageModel pageModel = new PageModel();
    List<SaScoreFlow> list = this.saScoreFlowService.baseLimitSelect(start, end, saScoreFlow);
    Long count = this.saScoreFlowService.noCount(saScoreFlow);
    pageModel.setCode(Integer.valueOf(0));
    pageModel.setMsg("success");
    pageModel.setList(list);
    pageModel.setCount(Integer.valueOf(count + ""));
    return JSONObject.toJSONString(pageModel);
  }

  public String select(SaScoreFlow saScoreFlow) {
    PageModel pageModel = new PageModel();

    try {
      List list = this.saScoreFlowService.totalScore(saScoreFlow);
      if (list.size() > 0) {
        _log.info("查询成功,查询记录数：{}", new Object[] { Integer.valueOf(list.size()) });
        pageModel.setCode(Integer.valueOf(0));
        pageModel.setMsg("success");
        pageModel.setList(list);
        return JSONObject.toJSONString(pageModel);
      }
    } catch (Exception e) {
      e.printStackTrace();
      _log.info("积分流水：{}", new Object[] { "查询失败" });
      pageModel.setCode(Integer.valueOf(1));
      pageModel.setMsg("fail");
    }
    return JSONObject.toJSONString(pageModel);
  }

  public String select(String userId) {
    PageModel pageModel = new PageModel();
    Integer score = Integer.valueOf(0);
    try {
      score = Integer.valueOf(this.saScoreFlowService.noConfigScore(userId));
      if (score.intValue() > 0) {
        _log.info("未出账积分查询成功,：{}", new Object[] { score });
        pageModel.setCode(Integer.valueOf(0));
        pageModel.setMsg("success");
        pageModel.setCount(score);
        return JSONObject.toJSONString(pageModel);
      }
    } catch (Exception e) {
      e.printStackTrace();
      _log.info("未出账积分：{}", new Object[] { "查询失败" });
      pageModel.setCode(Integer.valueOf(1));
      pageModel.setMsg("fail");
    }
    return JSONObject.toJSONString(pageModel);
  }
}





