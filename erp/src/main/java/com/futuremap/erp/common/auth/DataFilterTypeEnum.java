package com.futuremap.erp.common.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public
enum DataFilterTypeEnum {
    ALL(1, "全部"),
    DEPT(2, "本人所在组织机构"),
    SELF(3, "本人"),
    DEPT_SETS(4, "自定义组织机构"),
    DIY(5, "自定义sql过滤");
    int type;
    String desc;

    public static DataFilterTypeEnum getStructureEnum(int type) {

        for (DataFilterTypeEnum result : DataFilterTypeEnum.values()) {
            if (result.type==type) {
                return result;
            }
        }
        return null;
    }
}
