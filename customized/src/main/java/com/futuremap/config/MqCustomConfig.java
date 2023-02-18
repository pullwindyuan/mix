package com.futuremap.config;

import java.util.HashMap;
import java.util.Map;


/**
 * 消息队列配置，这里只能修改值，不可以增删改变量
 */
public class MqCustomConfig {
    //===================================common普通消息队列=====================================
    public static final String QUEUE_DATA_PROCESS = "queue_data_process"; //数据传输通道
    public static final String QUEUE_DATA_PROCESS_SYS = "queue_data_process_sys"; //系统消息通道

    public static final Map<String, QueueConfig> QUEUE_DEFINE;

    static {
        QUEUE_DEFINE = new HashMap<>();
        QUEUE_DEFINE.put(QUEUE_DATA_PROCESS, QueueConfig.WITH_DELAY);
        QUEUE_DEFINE.put(QUEUE_DATA_PROCESS_SYS, QueueConfig.WITH_FANOUT);
    }
}
