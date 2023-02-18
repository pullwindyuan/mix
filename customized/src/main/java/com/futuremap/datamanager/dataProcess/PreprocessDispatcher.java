package com.futuremap.datamanager.dataProcess;

import com.alibaba.fastjson.JSONObject;
import com.futuremap.base.lock.LockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

/**
 * 数据预处理的分发调度器，所有的预处理请求均先进入本调度器，然后由调度器决定使用什么方法处理数据,
 * 目前处理数据有两种方式：
 * 1、全量处理——每次处理均针对全部数据样本。这种方式代码相对简单，但效率较低，尤其是数据样本变化
 *       频繁的场景，本次实现就是基于这种方式；
 * 2、流式处理——将数据通过消息队列发送出去，接收到数据消息后逐条数据按照规则进行处理，这种方式
 *       可以进行增量处理，但代码复杂度极高，需要有可靠的出错补偿机制保证数据最终一致性。
 * @author dell
 */
@Service
@Slf4j
public class PreprocessDispatcher {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private LockService lockService;

    @Autowired
    private TemplateService templateService;

    /**
     * 命令解析器，负责将配置模板中的pretreatment信息进行解析并输出相应命令
     */
    public void commandParser(String templateId) {
            JSONObject template = templateService.findDataStructureTemplateById(templateId);
            JSONObject pretreatment = template.getJSONObject("pretreatment");


    }

}
