package com.futuremap.config;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Address;
import com.rabbitmq.client.AddressResolver;
import com.rabbitmq.client.BuiltinExchangeType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;
/**
 *
 * 测试发布到外网不加注解，因为外网暂时没有MQ
 */
//@Configuration
//@Service
public class RabbitConfig implements AddressResolver {
    Logger log = LoggerFactory.getLogger(RabbitTemplate.class);
    public static final String CALL_BACK = ":callback";

    @Resource
    private Environment env;

    private String projectName;

    //@Autowired
    public RabbitTemplate rabbitTemplate;

    //@Value("${spring.rabbitmq.port}")
    private int port;

    //@Value("${spring.rabbitmq.host}")
    private String host;

    //@Value("${spring.rabbitmq.username}")
    private String username;

    //@Value("${spring.rabbitmq.password}")
    private String password;

    //@Value("${spring.rabbitmq.virtualHost}")
    String mqRabbitVirtualHost;

    List<Address> addressList;

    Address address;

    private CachingConnectionFactory connectionFactory;

    private String CUSTOM_DIRECT_EXCHANGE_NAME;

    private String CUSTOM_FANOUT_EXCHANGE_NAME;

    public static Map<String, QueueConfig> QUEUE_MAP;

    public static Map<String, String> QUEUE_EXCHANGE_NAME_MAP;

    public static Map<String, Exchange> QUEUE_EXCHANGE_MAP;


    /**
     * 定制化amqp模版      可根据需要定制多个
     * <p>
     * <p>
     * 此处为模版类定义 Jackson消息转换器
     * ConfirmCallback接口用于实现消息发送到RabbitMQ交换器后接收ack回调   即消息发送到exchange  ack
     * ReturnCallback接口用于实现消息发送到RabbitMQ 交换器，但无相应队列与交换器绑定时的回调  即消息发送不到任何一个队列中  ack
     *
     * @return the amqp template
     */
    @Bean
    public AmqpTemplate amqpTemplate() {
//          使用jackson 消息转换器
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        rabbitTemplate.setEncoding("UTF-8");
//        开启returncallback     yml 需要 配置    publisher-returns: true
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {
//            String correlationId = message.getMessageProperties().getCorrelationIdString();
            log.debug("消息：{} 发送失败, 应答码：{} 原因：{} 交换机: {}  路由键: {}", "1111", replyCode, replyText, exchange, routingKey);
        });
        //        消息确认  yml 需要配置   publisher-returns: true
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            if (ack) {
                log.debug("消息发送到exchange成功,id: {}", correlationData.getId());
            } else {
                log.debug("消息发送到exchange失败,原因: {}", cause);
            }
        });
        return rabbitTemplate;
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
         rabbitTemplate = new RabbitTemplate(connectionFactory);
        return rabbitTemplate;
    }


    //创建mq连接
    @Bean
    public ConnectionFactory connectionFactory() {
        this.username = this.env.getProperty("spring.rabbitmq.username");
        this.password = this.env.getProperty("spring.rabbitmq.password");
        this.mqRabbitVirtualHost = this.env.getProperty("spring.rabbitmq.virtualHost");
        this.host = this.env.getProperty("spring.rabbitmq.host");
        this.port = Integer.parseInt(this.env.getProperty("spring.rabbitmq.port"));
        if (this.connectionFactory == null) {
            this.connectionFactory = new CachingConnectionFactory(this.port);
        } else {
            return this.connectionFactory;
        }
        if (org.apache.commons.lang3.StringUtils.isNotBlank(this.username)) {
            this.connectionFactory.setUsername(this.username);
            this.connectionFactory.setPassword(this.password);
        }
        this.connectionFactory.setVirtualHost(this.mqRabbitVirtualHost);
        this.address = new Address(this.host, this.port);
        this.addressList = new ArrayList<>();
        this.addressList.add(this.address);
        //this.connectionFactory.setAddressResolver(this);
        this.connectionFactory.setAddresses(this.address.getHost());
        this.connectionFactory.setPort(this.address.getPort());
        return this.connectionFactory;
    }

    /**
     * 初始化消息队列
     *
     * @param -Map- <key>: 消息队列名称
     *              <value>=: 是否对该队列启用延时消息功能
     * @return boolean
     */
    @Bean
    public boolean init() throws IOException {
        Map<String, QueueConfig> queue_config = MqCustomConfig.QUEUE_DEFINE;
        QUEUE_EXCHANGE_MAP = new HashMap<>();
        QUEUE_EXCHANGE_NAME_MAP = new HashMap<>();
        //this.enabled = Boolean.parseBoolean(env.getProperty("RabbitMQ.enabled"));
        this.projectName = this.env.getProperty("rabbitmq.nameSpace");
        //交换机
        this.CUSTOM_DIRECT_EXCHANGE_NAME = projectName + "-DIRECT"; //+ "-DIRECT"
        this.CUSTOM_FANOUT_EXCHANGE_NAME = projectName + "-FANOUT"; //+ "-FANOUT"

        if (queue_config == null) {
            return false;
        } else {
            QUEUE_MAP = queue_config;
        }

        Iterator iterator = QUEUE_MAP.entrySet().iterator();
        String queueName;

        do {
            if (!iterator.hasNext()) {
                break;
            }

            Map.Entry<String, QueueConfig> entry = (Map.Entry) iterator.next();
            queueName = entry.getKey();
            QueueConfig config = entry.getValue();
            if (!StringUtils.isEmpty(queueName)) {
                if (config.getType() == QueueConfig.WITH_DELAY_CALLBACK.getType()) {//开通延时消息功能的队列和普通队列使用不同的交换机
                    QUEUE_EXCHANGE_NAME_MAP.put(queueName, "DELAY-" + CUSTOM_DIRECT_EXCHANGE_NAME); //"DELAY-" +
                    QUEUE_EXCHANGE_NAME_MAP.put(getDelayQueueName(queueName), "DELAY-" + CUSTOM_DIRECT_EXCHANGE_NAME); //"DELAY-" +
                    QUEUE_EXCHANGE_NAME_MAP.put(queueName + CALL_BACK, "DELAY-" + CUSTOM_DIRECT_EXCHANGE_NAME); //"DELAY-"
                } else if (config.getType() == QueueConfig.WITH_DELAY.getType()) {
                    QUEUE_EXCHANGE_NAME_MAP.put(queueName, "DELAY-" + CUSTOM_DIRECT_EXCHANGE_NAME); //"DELAY-" +
                    QUEUE_EXCHANGE_NAME_MAP.put(getDelayQueueName(queueName), "DELAY-" + CUSTOM_DIRECT_EXCHANGE_NAME); //"DELAY-" +
                } else if (config.getType() == QueueConfig.WITH_CALLBACK.getType()) {
                    QUEUE_EXCHANGE_NAME_MAP.put(queueName, CUSTOM_DIRECT_EXCHANGE_NAME);
                    QUEUE_EXCHANGE_NAME_MAP.put(queueName + CALL_BACK, CUSTOM_DIRECT_EXCHANGE_NAME); //+ CALL_BACK
                } else if (config.getType() == QueueConfig.NORMAL.getType()) {
                    QUEUE_EXCHANGE_NAME_MAP.put(queueName, CUSTOM_DIRECT_EXCHANGE_NAME);
                } else if (config.getType() == QueueConfig.WITH_FANOUT.getType()) {
                    QUEUE_EXCHANGE_NAME_MAP.put(queueName, CUSTOM_FANOUT_EXCHANGE_NAME);
                }
            }
        } while (!StringUtils.isEmpty(queueName));
        return autoConfig();
    }

    public boolean autoConfig() throws IOException {
        //if(!enabled || QUEUE_MAP == null) {
        if (QUEUE_MAP == null) {
            return false;
        }
        Iterator iterator = QUEUE_MAP.entrySet().iterator();
        String queueName;
        QueueConfig queueConfig;
        do {
            if (!iterator.hasNext()) {
                return false;
            }

            Map.Entry<String, QueueConfig> entry = (Map.Entry) iterator.next();
            queueName = entry.getKey();
            queueConfig = entry.getValue();
            AMQP.Queue.DeclareOk declareOk;
            AMQP.Queue.BindOk bindOk;
            /**
             * 声明一个队列 支持持久化.
             *
             * @return the queue
             */
            if (!StringUtils.isEmpty(queueName)) {
                log.debug("消息队列名称: {}", queueName);
                /**
                 * 通过绑定键 将指定队列绑定到一个指定的交换机 .
                 *
                 * @param queue    the queue
                 * @param exchange the exchange
                 * @return the binding
                 */
                String exchangeName = getExchange(queueName);
                //(String queue, boolean durable, boolean exclusive, boolean autoDelete, Map<String, Object> arguments)
//                public com.rabbitmq.client.AMQP.Queue.DeclareOk queueDeclare(String queue, boolean durable, boolean exclusive, boolean autoDelete, Map<String, Object> arguments) throws IOException {
//                    return this.delegate.queueDeclare(queue, durable, exclusive, autoDelete, arguments);
//                }
                declareOk = this.connectionFactory.createConnection().createChannel(false).queueDeclare(queueName, true, false, false, null);
                bindOk = this.connectionFactory.createConnection().createChannel(false).queueBind(queueName, exchangeName, getQueueKey(queueName));
                //自动创建callback队列
                if (queueConfig.getType() == QueueConfig.WITH_DELAY_CALLBACK.getType() || queueConfig.getType() == QueueConfig.WITH_CALLBACK.getType()) {
                    declareOk = this.connectionFactory.createConnection().createChannel(false).queueDeclare(queueName + CALL_BACK, true, false, false, null);
                    bindOk = this.connectionFactory.createConnection().createChannel(false).queueBind(queueName + CALL_BACK, exchangeName, getQueueKey(queueName));
                }
                if (queueConfig.getType() == QueueConfig.WITH_DELAY_CALLBACK.getType() || queueConfig.getType() == QueueConfig.WITH_DELAY.getType()) {
                    /**
                     * 声明一个死信队列.
                     * x-dead-letter-exchange   对应  死信交换机
                     * x-dead-letter-routing-key  对应 死信队列
                     *
                     * @return the queue
                     */
                    Map<String, Object> args = new HashMap<>(2);
                    //String delayExchangeName = getExchange(getDelayQueueName(queueName));
                    //x-dead-letter-exchange    声明  死信交换机
                    args.put("x-dead-letter-exchange", exchangeName);
                    //       x-dead-letter-routing-key    声明 死信路由键
                    args.put("x-dead-letter-routing-key", getQueueKey(queueName));
                    //(String queue, boolean durable, boolean exclusive, boolean autoDelete, Map<String, Object> arguments)
                    declareOk = this.connectionFactory.createConnection().createChannel(false).queueDeclare(getDelayQueueName(queueName), true, false, false, args);
                    /**
                     * 死信路由通过 DL_KEY 绑定键绑定到死信队列上.
                     *
                     * @return the binding
                     */
                    bindOk = this.connectionFactory.createConnection().createChannel(false).queueBind(getDelayQueueName(queueName), exchangeName, getDelayQueueKey(queueName), null);
                    /**
                     * 死信路由通过 KEY_R 绑定键绑定到死信队列上.
                     *
                     * @return the binding
                     */
                    //String queue, String exchange, String routingKey, Map<String, Object> arguments
                    bindOk = this.connectionFactory.createConnection().createChannel(false).queueBind(queueName, exchangeName, getQueueKey(queueName), null);
                }

                //boolean delay = getDelayQueueEnabled(queueName);
                //创建延时队列
            }
        } while (!StringUtils.isEmpty(queueName));
        return true;
    }

    public static QueueConfig getQueueType(String queueName) {
        return QUEUE_MAP.get(queueName);
    }

    public static String getDelayQueueKey(String queueName) {
        return "DELAY:" + queueName + "-KEY";
    }

    public static String getQueueKey(String queueName) {
        return queueName + "-KEY";
    }

    public static String getDelayQueueName(String queueName) {
        return "DELAY:" + queueName;
    }

    public static String getExchangeName(String queueName) {
        return QUEUE_EXCHANGE_NAME_MAP.get(queueName);
    }

    public String getExchange(String queueName) {
        Exchange exchange = QUEUE_EXCHANGE_MAP.get(queueName);
        if (exchange == null) {
            String exchangeName = QUEUE_EXCHANGE_NAME_MAP.get(queueName);
            if (!StringUtils.isEmpty(exchangeName)) {
                if (exchangeName.indexOf("-FANOUT") >= 0) {
                    //如果equals 我就给它创建成发布订阅模式
                    try {
                        this.connectionFactory.createConnection().createChannel(false).exchangeDeclare(exchangeName, BuiltinExchangeType.FANOUT, true);
                        return exchangeName;
                    } catch (IOException e) {
                        //注释掉打印的异常堆栈信息,以免启动报错
                        //e.printStackTrace();
                        return exchangeName;
                    }
                } else {
                    try {
                        //反之就是DIRECT 目前使用到的就是着两种模式
                        this.connectionFactory.createConnection().createChannel(false).exchangeDeclare(exchangeName, BuiltinExchangeType.DIRECT, true);
                        return exchangeName;
                    } catch (IOException e) {
                        //e.printStackTrace();
                        //注释掉打印的异常堆栈信息,以免启动报错
                        return exchangeName;
                    }
                }

                //                try {
//                    this.connectionFactory.createConnection().createChannel(false).exchangeDeclare(exchangeName, BuiltinExchangeType.DIRECT);
//                    return exchangeName;
//                } catch (IOException e) {
//                    e.printStackTrace();
//                    return null;
//                }
            }
        }
        return getExchangeName(queueName);
    }

    @Override
    public List<Address> getAddresses() throws IOException {
        return this.addressList;
    }
}
