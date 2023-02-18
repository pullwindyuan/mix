package com.futuremap.custom.dto.user;


import com.futuremap.custom.dto.BaseListQuery;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class UserQuery extends BaseListQuery{

    protected String name;
	
	protected Integer id;
	
    protected String fullName;
	
	protected String phone;
	
	protected String keyword;
}
