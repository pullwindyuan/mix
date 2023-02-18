package com.futuremap.config;

/**
 * java类简单作用描述
 *
 * @ProjectName: newcard
 * @Package: com.hl.card.common.mq
 * @ClassName: ${TYPE_NAME}
 * @Description: java类作用描述
 * @Author: 作者姓名
 * @CreateDate: 2018/12/1 9:37
 * @UpdateUser:
 * @UpdateDate: 2018/12/1 9:37
 * @UpdateRemark: The modified content
 * @Version: 1.0
 * <p>Copyright: Copyright (c) 2018</p>
 */
public enum QueueConfig {
    NORMAL(00, "普通队列"),
    /*
     * 成功
     */
    WITH_DELAY(01, "带延时消息队列"),
    /*
     * 失败
     */
    WITH_CALLBACK(02, "带回调消息队列"),
    /*
     * 错误
     */
    WITH_DELAY_CALLBACK(03, "带延时和回调消息队列"),


    WITH_FANOUT(04, "广播模式");


    private int type;
    private String name;

    public int getType() {
        return type;
    }

    public void setType(int status) {
        this.type = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private QueueConfig(int type, String name) {
        this.type = type;
        this.name = name;
    }
}
