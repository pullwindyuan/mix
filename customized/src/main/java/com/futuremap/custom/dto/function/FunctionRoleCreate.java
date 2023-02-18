package com.futuremap.custom.dto.function;

import java.util.List;

import lombok.Data;

@Data
public class FunctionRoleCreate {
	String roleId;
	
	List<String> functionIds;

}
