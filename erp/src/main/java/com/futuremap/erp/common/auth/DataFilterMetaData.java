package com.futuremap.erp.common.auth;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class DataFilterMetaData {
    public DataFilterTypeEnum filterType;
    public Set<Long> deptIds;
    public Long deptId;
    public String userCode;
    public String sql;
    public String[] orgScopeNames;

    public String[] selfScopeNames;
    public boolean dataScopeFlag;
    public boolean columnScopeFlag;


}
