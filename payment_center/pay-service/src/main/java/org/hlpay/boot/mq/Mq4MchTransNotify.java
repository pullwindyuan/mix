 package org.hlpay.boot.mq;

 import com.alibaba.fastjson.JSON;
 import com.alibaba.fastjson.JSONObject;
 import com.google.common.hash.HashCode;
 import com.rabbitmq.client.Channel;
import java.io.IOException;
import org.apache.commons.lang3.StringUtils;
import org.hlpay.base.service.BaseService4TransOrder;
import org.hlpay.base.service.mq.Mq4MchNotify;
import org.hlpay.common.util.MyLog;
import org.hlpay.common.util.RedisUtil;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Mq4MchTransNotify
  extends Mq4MchNotify
{
  @Autowired
  private BaseService4TransOrder baseService4TransOrder;
  @Autowired
  private RedisUtil redisUtil;
  private static final MyLog _log = MyLog.getLog(org.hlpay.boot.mq.Mq4MchTransNotify.class);

  public void send(String msg) {
    send("queue.notify.mch.trans", msg);
  }



  @RabbitListener(queues = {"queue.notify.mch.trans"})
  public void receive(Message message, Channel channel) throws IOException {
    String logPrefix = "【商户转账通知】";
    String msg = StringEscapeUtils.unescapeJson(new String(message.getBody()));
    String jsonStr = msg.substring(1, msg.length() - 1);
    _log.info("{}接收消息:msg={}", new Object[] { logPrefix, jsonStr });
    JSONObject msgObj = JSON.parseObject(jsonStr);
    String respUrl = msgObj.getString("url");
    String orderId = msgObj.getString("orderId");
    int count = msgObj.getInteger("count").intValue();
    try {
      if (StringUtils.isBlank(respUrl)) {
        _log.warn("{}商户通知URL为空,respUrl={}", new Object[] { logPrefix, respUrl });
        return;
      }
      String httpResult = httpPost(respUrl);
      int cnt = count + 1;
      _log.info("{}notifyCount={}", new Object[] { logPrefix, Integer.valueOf(cnt) });
      if ("success".equalsIgnoreCase(httpResult)) {

        try {
          int result = this.baseService4TransOrder.baseUpdateStatus4Complete(orderId);
          _log.info("{}修改payOrderId={},订单状态为处理完成->{}", new Object[] { logPrefix, orderId, (result == 1) ? "成功" : "失败" });
        } catch (Exception e) {
          _log.error(e, "修改订单状态为处理完成异常");
        }

        try {
          int result = baseUpdateMchNotifySuccess(orderId, httpResult, (byte)cnt);
          _log.info("{}修改商户通知,orderId={},result={},notifyCount={},结果:{}", new Object[] { logPrefix, orderId, httpResult, Integer.valueOf(cnt), (result == 1) ? "成功" : "失败" });
        } catch (Exception e) {
          _log.error(e, "修改商户支付通知异常");
        }

        return;
      }
      try {
        int result = baseUpdateMchNotifyFail(orderId, httpResult, (byte)cnt);
        _log.info("{}修改商户通知,orderId={},result={},notifyCount={},结果:{}", new Object[] { logPrefix, orderId, httpResult, Integer.valueOf(cnt), (result == 1) ? "成功" : "失败" });
      } catch (Exception e) {
        _log.error(e, "修改商户支付通知异常");
      }
      if (cnt > 5) {
        _log.info("{}通知次数notifyCount()>5,停止通知", new Object[] { respUrl, Integer.valueOf(cnt) });

        return;
      }
      msgObj.put("count", Integer.valueOf(cnt));
      send("queue.notify.mch.trans", msgObj.toJSONString(), (cnt * 60 * 1000));
      _log.info("{}发送延时通知完成,通知次数:{},{}秒后执行通知", new Object[] { respUrl, Integer.valueOf(cnt), Integer.valueOf(cnt * 60) });
    }
    finally {

      HashCode hashCode = HashCode.fromString(jsonStr);
      String key = hashCode.toString();
      this.redisUtil.delete(key);
      _log.info("删除消息发送标志：" + key, new Object[0]);
    }
  }
}


