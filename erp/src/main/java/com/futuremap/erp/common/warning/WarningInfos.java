package com.futuremap.erp.common.warning;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author dell
 */
@Data
public class WarningInfos implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 预警信息
     */
    @ApiModelProperty(value = "预警类别:" +
            "    DELAY(code:0, level:1, color:\"red\", desc:\"逾期\")," +
            "    WRONG_NUM(code:10, level:1, color:\"red\", desc:\"数量有误\")," +
            "    WRONG_AMOUNT(code:20, level:1, color:\"red\", desc:\"金额有误\")," +
            "    IN_PROGRESS(code:30, level:8, color:\"blue\", desc:\"进行中\")," +
            "    COMPLETED(4code:0, level:10, color:\"green\", desc:\"完成\")," +
            "    COMPLETED_BUT_DELAY(code:50, level:9, color:\"yellow\", desc:\"完成但逾期\")," +
            "    SWITCH_DELAY(code:100, level:1, color:\"orange\", desc:\"超半年未挪完\")," +
            "    RECEIVE_DELAY(code:200, level:1, color:\"red\", desc:\"收款超过应收时间\")," +
            "    MAKE_DELAY(code:360, level:1, color:\"red\", desc:\"产品生产逾期\")," +
            "    SALE_DELAY(code:470, level:1, color:\"red\", desc:\"销售发货逾期\")," +
            "    MAKE_COMPLETED(code:380, level:10, color:\"green\", desc:\"生产完成\")," +
            "    MAKE_COMPLETED_BUT_DELAY(code:390, level:9, color:\"yellow\", desc:\"生产完成但逾期\")," +
            "    SALE_COMPLETED(code:480, level:10, color:\"green\", desc:\"销售发货完成\")," +
            "    SALE_COMPLETED_BUT_DELAY(code:490, level:9, color:\"yellow\", desc:\"销售发货完成但逾期\")," +
            "    EMPTY(code:999, level:10, color:\"none\", desc:\"无警示\");", notes = "")
    @TableField(exist=false)
    //@JSONField(serializeUsing = FastJsonEnumSerializer.class, deserializeUsing = FastJsonEnumDeserializer.class)
    private Map<String, Warning> warning;

    public Map<String, Warning> getWarning() {
        if(warning == null) {
            warning = new HashMap<>();
        }
        return warning;
    }
}
