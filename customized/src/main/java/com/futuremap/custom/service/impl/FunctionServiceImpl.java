package com.futuremap.custom.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.futuremap.custom.entity.Function;
import com.futuremap.custom.mapper.FunctionMapper;
import com.futuremap.custom.service.CfgFunctionService;

import java.util.List;

import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author futuremap
 * @since 2021-01-27
 */
@Service
public class FunctionServiceImpl extends ServiceImpl<FunctionMapper, Function> implements CfgFunctionService {
	public FunctionMapper getMapper() {
        return this.baseMapper;
    }
	/**
	 * @param string
	 * @return
	 */
	public List<Function> selectByMethodTypeAndMethodNameIsNotNull(String methodType) {
		QueryWrapper<Function> qw = new QueryWrapper<>();
		qw.eq("method_type", methodType).isNotNull("method_name");
		return this.baseMapper.selectList(qw);
	}
	
	
	public List<Function> selectByIdInAndMethodTypeAndMethodNameIsNotNull(List<String> ids, String methodType) {
		QueryWrapper<Function> qw = new QueryWrapper<>();
		qw.in("id", ids).eq("method_type", methodType).isNotNull("method_name");
		return this.baseMapper.selectList(qw);
		
	}

}
