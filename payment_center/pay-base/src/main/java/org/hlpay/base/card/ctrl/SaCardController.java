package org.hlpay.base.card.ctrl;

import com.alibaba.fastjson.JSONObject;
import java.util.ArrayList;
import java.util.List;
import org.hlpay.base.card.common.PageBean;
import org.hlpay.base.card.service.impl.SaCardDataServiceImpl;
import org.hlpay.base.card.service.impl.SaCardServiceImpl;
import org.hlpay.base.model.SaCard;
import org.hlpay.base.model.SaCardData;
import org.hlpay.base.plugin.PageModel;
import org.hlpay.common.enumm.CurrencyTypeEnum;
import org.hlpay.common.enumm.UserCardDataTypeEnum;
import org.hlpay.common.util.MyLog;
import org.hlpay.common.util.RandomStrUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;






@Component
public class SaCardController
{
  private static final MyLog _log = MyLog.getLog(SaCardController.class);

  @Autowired
  private SaCardServiceImpl saCardService;

  @Autowired
  private SaCardDataServiceImpl saCardDataService;


  public String addAllPaymentCard(SaCard saCard) throws Exception {
    PageModel pageModel = new PageModel();
    String cardType = saCard.getCardType();
    String currency = saCard.getCurrency();
    String userId = saCard.getUserId();
    String userName = saCard.getUserName();
    String loginAccount = saCard.getLoginAccount();
    saCard = this.saCardService.baseInfo(saCard);
    if (saCard == null) {
      List<SaCard> list = new ArrayList<>();
      saCard = new SaCard();
      saCard.setUserId(userId);
      List<SaCard> cardList = this.saCardService.baseLimitSelect(Integer.valueOf(0), Integer.valueOf(100), saCard);
      List<SaCardData> scdList = this.saCardDataService.baseLimitSelect(Integer.valueOf(0), Integer.valueOf(100), null);
      boolean flag = false;

      UserCardDataTypeEnum[] userCardDataTypeEnum = UserCardDataTypeEnum.values();
      CurrencyTypeEnum[] currencyTypeEnum = CurrencyTypeEnum.values();
      for (int i = 0; i < scdList.size(); i++) {
        for (int j = 0; j < cardList.size(); j++) {
          if (((SaCardData)scdList.get(i)).getDataType().equals(((SaCard)cardList.get(j)).getCardType()) && ((SaCardData)scdList
            .get(i)).getDataCode().equals(((SaCard)cardList.get(j)).getCurrency())) {
            flag = true;
            break;
          }
          flag = false;
        }

        if (!flag) {
          SaCard sc = new SaCard();
          sc.setCardType(((SaCardData)scdList.get(i)).getDataType());
          sc.setCurrency(((SaCardData)scdList.get(i)).getDataCode());
          sc.setCardName(((SaCardData)scdList.get(i)).getDataType());
          sc.setCardNumber("C4000" + RandomStrUtils.getInstance().createRandomNumString(7));
          sc.setLoginAccount(loginAccount);
          sc.setUserId(userId);
          sc.setUserName(userName);
          list.add(sc);
          if (sc.getCardType().equals(cardType) && sc.getCurrency().equals(currency)) {
            saCard = new SaCard();
            saCard.setCardNumber(sc.getCardNumber());
            saCard.setLoginAccount(sc.getLoginAccount());
            saCard.setUserId(sc.getUserId());
            saCard.setCardType(sc.getCardType());
            saCard.setCurrency(sc.getCurrency());
          }
        }
      }
      if (list.size() > 0) {
        this.saCardService.addSaCard(list);
      }
    }
    pageModel.setCode(Integer.valueOf(0));
    pageModel.setMsg("success");
    pageModel.setObj(saCard);
    return JSONObject.toJSONString(pageModel);
  }

  public String select(Integer start, Integer end, SaCard saCard) throws Exception {
    PageModel pageModel = new PageModel();
    List<SaCard> list = this.saCardService.baseLimitSelect(start, end, saCard);
    pageModel.setCode(Integer.valueOf(0));
    pageModel.setMsg("success");
    pageModel.setList(list);
    return JSONObject.toJSONString(pageModel);
  }















  public String info(SaCard saCard) throws Exception {
    PageModel pageModel = new PageModel();
    saCard = this.saCardService.baseInfo(saCard);
    if (saCard == null) {
      pageModel.setCode(Integer.valueOf(1));
      pageModel.setMsg("找不到卡号!");
    } else {
      pageModel.setCode(Integer.valueOf(0));
      pageModel.setMsg("success");
      pageModel.setObj(saCard);
    }
    return JSONObject.toJSONString(pageModel);
  }

  public String baseUpdate(SaCard saCard) throws Exception {
    PageModel pageModel = new PageModel();
    SaCard sc = this.saCardService.baseInfo(saCard);
    if (sc == null) {
      pageModel.setCode(Integer.valueOf(1));
      pageModel.setMsg("未能找到卡号！");
      return JSONObject.toJSONString(pageModel);
    }
    int result = this.saCardService.updateData(saCard);
    pageModel.setCode(Integer.valueOf(0));
    pageModel.setMsg("success");
    return JSONObject.toJSONString(pageModel);
  }

  public String infoAndSave(SaCard saCard) throws Exception {
    PageModel pageModel = new PageModel();
    String loginAccont = saCard.getLoginAccount();
    String username = saCard.getUserName();
    String userId = saCard.getUserId();
    saCard = this.saCardService.baseInfo(saCard);
    if (saCard == null) {
      saCard = new SaCard();
      saCard.setCardNumber("C4000" + RandomStrUtils.getInstance().createRandomNumString(7));
      saCard.setLoginAccount(loginAccont);
      saCard.setUserId(userId);
      saCard.setUserName(username);
      this.saCardService.baseInsert(saCard);
      _log.info("下单时创建新用户：" + userId + " 创建卡成功", new Object[0]);
    }
    pageModel.setCode(Integer.valueOf(0));
    pageModel.setMsg("success");
    pageModel.setObj(saCard);
    return JSONObject.toJSONString(pageModel);
  }

  public String descSelect() throws Exception {
    PageModel pageModel = new PageModel();
    List<SaCard> list = this.saCardService.get("descSelect");
    if (list == null || list.size() <= 0) {
      list = new ArrayList<>();
      list = this.saCardService.descSaCard();
      this.saCardService.set("descSelect", list);
    }
    pageModel.setCode(Integer.valueOf(0));
    pageModel.setMsg("success");
    pageModel.setList(list);
    return JSONObject.toJSONString(pageModel);
  }

  public String save(SaCard saCard) throws Exception {
    PageModel pageModel = new PageModel();
    saCard.setCardNumber("C4000" + RandomStrUtils.getInstance().createRandomNumString(7));
    int result = this.saCardService.baseInsert(saCard);
    if (result > 0) {
      pageModel.setCode(Integer.valueOf(0));
      pageModel.setMsg("success");
      return JSONObject.toJSONString(pageModel);
    }
    return JSONObject.toJSONString(pageModel);
  }

  public String update(SaCard saCard) {
    PageModel pageModel = new PageModel();
    try {
      int result = this.saCardService.updateAll(saCard);
      if (result > 0) {
        _log.info("积分卡修改成功,修改记录数：{}", new Object[] { Integer.valueOf(result) });
        pageModel.setCode(Integer.valueOf(0));
        pageModel.setMsg("success");
        return JSONObject.toJSONString(pageModel);
      }
    } catch (Exception e) {
      e.printStackTrace();
      _log.info("积分卡：{}", new Object[] { "修改失败" });
      pageModel.setCode(Integer.valueOf(1));
      pageModel.setMsg("fail");
    }
    return JSONObject.toJSONString(pageModel);
  }

  public String list(String userId, Integer start, Integer end) {
    PageModel pageModel = new PageModel();

    try {
      PageBean pageBean = new PageBean();
      if (userId != null && !userId.equals(" ")) {
        List<SaCard> list = this.saCardService.infoSaCard(userId);
        pageBean.setList(list);
        pageBean.setTotal(list.size());
      } else {
        pageBean = this.saCardService.listSaCard(start.intValue(), end.intValue());
      }
      if (pageBean.getTotal() > 0) {
        _log.info("积分卡查询成功,查询记录数：{}", new Object[] { Integer.valueOf(pageBean.getTotal()) });
        pageModel.setCode(Integer.valueOf(0));
        pageModel.setMsg("success");
        pageModel.setList(pageBean.getList());
        pageModel.setCount(Integer.valueOf(pageBean.getTotal()));
        return JSONObject.toJSONString(pageModel);
      }
    } catch (Exception e) {
      e.printStackTrace();
      _log.info("积分卡：{}", new Object[] { "查询失败" });
      pageModel.setCode(Integer.valueOf(1));
      pageModel.setMsg("fail");
    }
    return JSONObject.toJSONString(pageModel);
  }
}

