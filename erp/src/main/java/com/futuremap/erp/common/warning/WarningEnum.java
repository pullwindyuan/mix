package com.futuremap.erp.common.warning;

import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

/**
 * @author dell
 */

@Getter
@AllArgsConstructor
public enum WarningEnum implements Serializable {
    //场景部分
    SWITCH_NORMAL("挪货",100, 1, "none", "无警示"),
    SWITCH_DELAY("挪货",110, 1, "red", "超半年未挪完"),
    RECEIVE_NORMAL("收款",200, 1, "none", "无警示"),
    RECEIVE_WARNING("收款",210, 1, "orange", "发货已过30天，收款提醒"),
    RECEIVE_DELAY("收款",220, 1, "red", "已到帐期未收款"),
    RECEIVE_COMPLETED("收款",230, 1, "green", "已到帐期已收款"),
    MAKE_DELAY("生产",360, 1, "red", "产品生产逾期"),
    SALE_NORMAL("发货",450, 1, "none", "无警示"),
    SALE_START("发货",460, 1, "blue", "开始部分发货"),
    SALE_NOT_START("发货",460, 1, "red", "逾期从未发货"),
    SALE_DELAY("发货",470, 1, "red", "部分发货已逾期"),
    MAKE_COMPLETED("生产",380, 10, "green", "生产完成"),
    MAKE_COMPLETED_BUT_DELAY("生产",390, 9, "yellow", "生产完成但逾期"),
    SALE_COMPLETED("发货",480, 10, "green", "发货完成"),
    SALE_COMPLETED_BUT_DELAY("发货",490, 9, "yellow", "发货完成但逾期"),
    //通用部分
    DELAY("通用",0, 1, "red", "逾期"),
    WRONG_NUM("通用",10, 1, "red", "数量有误"),
    WRONG_AMOUNT("通用",120, 1, "red", "金额有误"),
    SWITCH_IN_PROGRESS("挪货",130, 8, "blue", "挪货中"),
    SWITCH_COMPLETED("挪货",140, 10, "green", "挪货完成"),
    SWITCH_COMPLETED_BUT_DELAY("挪货",150, 9, "yellow", "挪货完成但逾期"),
    EMPTY("通用",999, 10, "none", "无警示");


    private static final long serialVersionUID = 1L;
    private String typeName;
    private Integer code;
    private Integer level;
    private String color;
    private String desc;

    public JSONObject getObjcet() {
        JSONObject object = new JSONObject();
        object.put("name", this.name());
        object.put("typeName", this.typeName);
        object.put("code", this.code);
        object.put("level", this.level);
        object.put("color", this.color);
        object.put("desc", this.desc);
        return object;
    }

    public WarningBean getWarningBean() {
        WarningBean warningBean = new WarningBean();
        warningBean.setName(this.name());
        warningBean.setCode(this.code);
        warningBean.setLevel(this.level);
        warningBean.setColor(this.color);
        warningBean.setDesc(this.desc);
        return warningBean;
    }
}
