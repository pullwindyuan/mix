package com.futuremap.mqService;

import com.alibaba.fastjson.JSONObject;
import com.futuremap.config.RabbitConfig;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class Mq4Base {
    private final Logger logger = LoggerFactory.getLogger(MqMessageListener.class);
    @Autowired
    private RedissonClient redissonClient;

    /**
     *
     * 测试发布到外网不加注解，因为外网暂时没有MQ
     */
    //@Autowired
    private RabbitConfig rabbitConfig;
    public void send(String queueName, String msg) {
        rabbitConfig.rabbitTemplate.convertAndSend(queueName, msg);
    }

    public void sendMsg(String queueName, String msg) throws AmqpException {
//        String key = String.valueOf((queueName + msg).hashCode());
//        //获取分布式锁，只要锁的名字一样，就是同一把锁
//        RLock lock = redissonClient.getLock(key);
//
//        if (lock.isLocked()) {
//            logger.warn("30秒内请勿重复发送消息:" + msg, new Object[0]);
//            return;
//        }
        //this.redisUtil.set(key, "1", 30L, TimeUnit.SECONDS);
        this.send(queueName, msg);
        //加锁（阻塞等待），默认过期时间是30秒
//        lock.lock();
//        logger.info("设置消息发送标志：" + key, new Object[0]);
    }


    public void forceSendMsg(String queueName, String msg) throws AmqpException {
        this.send(queueName, msg);
    }

    public void sendMsg(String queueName, String msg, long delay) throws AmqpException {
        logger.info("发送MQ延时消息:msg={},delay={}", new Object[] { msg, Long.valueOf(delay) });
        String key = String.valueOf((msg + queueName).hashCode());
        //获取分布式锁，只要锁的名字一样，就是同一把锁
        RLock lock = redissonClient.getLock(key);
        if (lock.isLocked()) {
            logger.warn("30秒内请勿重复发送消息:" + msg, new Object[0]);
            return;
        }
        //this.redisUtil.set(key, "1", 30L, TimeUnit.SECONDS);
        //加锁（阻塞等待），默认过期时间是30秒
        lock.lock();
        this.send(queueName, msg, delay);

        logger.info("设置消息发送标志：" + key, new Object[0]);
    }

    public void forceSendMsg(String queueName, String msg, long delay) throws AmqpException {
        logger.info("发送MQ延时消息:msg={},delay={}", new Object[] { msg, Long.valueOf(delay) });
        this.send(queueName, msg, delay);
    }

    public void send(String queueName, JSONObject msg) {
        rabbitConfig.rabbitTemplate.convertAndSend(queueName, msg);
    }

    public void sendBack(String queueName, String msg) {
        rabbitConfig.rabbitTemplate.convertAndSend(queueName + RabbitConfig.CALL_BACK, msg);
    }


    /**
     * 发送延迟消息,默认发送到common交换机
     *
     * @param msg
     * @param delay
     */
    public void send(String queue_name, String msg, long delay) {
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        //声明消息处理器  这个对消息进行处理  可以设置一些参数   对消息进行一些定制化处理   我们这里  来设置消息的编码  以及消息的过期时间  因为在.net 以及其他版本过期时间不一致   这里的时间毫秒值 为字符串
        MessagePostProcessor messagePostProcessor = message -> {
            MessageProperties messageProperties = message.getMessageProperties();
            //设置编码
            messageProperties.setContentEncoding("utf-8");
            //设置过期时间,单位：毫秒
            messageProperties.setExpiration(delay + "");
            //messageProperties.setDelay((int) (delay));
            return message;
        };
        //发送延时消息  delay毫秒后过期 形成死信
        rabbitConfig.rabbitTemplate.convertAndSend(RabbitConfig.getExchangeName(queue_name), RabbitConfig.getDelayQueueKey(queue_name), msg, messagePostProcessor, correlationData);
    }

    public void broadcast(String message) {
        rabbitConfig.rabbitTemplate.convertAndSend("${rabbitmq.nameSpace}" + "-FANOUT", "", message);
    }
}

