package com.futuremap.mqService;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.futuremap.base.util.ThreadExecutorUtil;
import com.futuremap.config.MqCustomConfig;
import com.futuremap.datamanager.enums.DataProcessEnum;
import com.futuremap.datamanager.dataProcess.Extract;
import org.apache.commons.lang.StringEscapeUtils;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 * @Author ZuRangTang
 * @Date 2019/11/5
 **/

@Component
public class MqMessageListener extends Mq4Base {

    private final Logger logger = LoggerFactory.getLogger(MqMessageListener.class);
    //使用4个线程处理数据
    private final ExecutorService fixedThreadPool = ThreadExecutorUtil.newFixedThreadPool(4);

//    @Autowired
//    private Mq4Notify mq4Notify;
//
//    @Autowired
//    private RedisUtil redisUtil;

    @Autowired
    private Extract extract;

    @Autowired
    private RedissonClient redissonClient;

    /**
     *
     * 测试发布到外网不加注解，因为外网暂时没有MQ
     */
    //@RabbitListener(queues = MqCustomConfig.QUEUE_DATA_PROCESS_SYS)
    public void processCareFor(Message message) {

//        MessageProperties messageProperties = message.getMessageProperties();
//        String consumerQueue = messageProperties.getConsumerQueue();
        String msg = new String(message.getBody());
        logger.info("message={}", msg);
        msg = StringEscapeUtils.unescapeJava(msg);
        JSONObject targetMsg = JSONObject.parseObject(msg);
        if (targetMsg != null) {
            if ("destroy".equals(targetMsg.getString("action"))) {

            }
        }
    }

    /**
     * 数据处理消息监听
     * @param message
     */
    /**
     *
     * 测试发布到外网不加注解，因为外网暂时没有MQ
     */
    //@RabbitListener(queues = MqCustomConfig.QUEUE_DATA_PROCESS)
    public void process(Message message) {
        logger.info("message={}", message);
        //不适用多线程处理，由于数据具有整体性，用数据流的方式处理最好，避免很多问题
        //fixedThreadPool.execute(() -> {
            String msg = StringEscapeUtils.unescapeJava(new String(message.getBody()));
            String jsonStr = msg.substring(1, msg.length() - 1);
            try {
                JSONObject msgObj = JSONObject.parseObject(jsonStr);
                String action = msgObj.getString("action");
                List<JSONObject> dataList = JSONArray.parseArray(msgObj.getString("data"), JSONObject.class);
                if(DataProcessEnum.EXTRACT_FOREST.name().equals(action)) {
                    extract.extractForest(dataList);
                }else if(DataProcessEnum.SUM.name().equals(action)) {

                }else if(DataProcessEnum.SUM.name().equals(action)) {

                }else if(DataProcessEnum.SUM.name().equals(action)) {

                }
                logger.info("消息处理完成");
            } finally {
                String key = String.valueOf((MqCustomConfig.QUEUE_DATA_PROCESS + jsonStr).hashCode());
                //this.redisUtil.delete(key);
                RLock lock = redissonClient.getLock(key);
                //解锁，如果业务执行完成，就不会继续续期，即使没有手动释放锁，在30秒过后，也会释放锁
                if(lock.isLocked()){
                    if(lock.isHeldByCurrentThread()){
                        lock.unlock();
                    }
                }
                logger.info("消息处理完成，删除消息发送标志：" + key, new Object[0]);
            }
        //});
    }

    /**
     * 广播监听
     * @param payload
     */
    /**
     *
     * 测试发布到外网不加注解，因为外网暂时没有MQ
     */
    //@RabbitListener(bindings = {@QueueBinding(value = @Queue, exchange = @Exchange(value = "${rabbitmq.nameSpace}"+"-FANOUT", type = "fanout"))})
    public void platformConfigUpdate(String payload)  {
        String msg = payload;
        logger.info("平台配置更新广播:" + payload, new Object[0]);
    }
}
