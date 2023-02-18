package com.futuremap.custom.dto.function;

import java.util.List;

import com.futuremap.custom.dto.TreeAble;
import com.futuremap.custom.entity.Function;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper=false)
@AllArgsConstructor
@NoArgsConstructor
public class FunctionSummary extends Function implements TreeAble<FunctionSummary>{
	
	private  Integer select;
	
	private  Integer auth;
	
	private List<FunctionSummary> children;

}
