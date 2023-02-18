package com.futuremap.custom.mapper;

import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.futuremap.custom.entity.Function;

@Mapper
public interface FunctionMapper extends BaseMapper<Function>{

	/**
	 * @param string
	 * @return
	 */
	//@Select("select * from cfg_function where method_type = #{methodType} and method_name is not null")
	//List<Function> selectByMethodTypeAndMethodNameIsNotNull(@Param("methodType") String methodType);

	/**
	 * @param collect
	 * @param string
	 * @return
	 */
	//@Select("select * from cfg_function where id in #{ids} and method_type = #{methodType} and method_name is not null")
	//List<Function> selectByIdInAndMethodTypeAndMethodNameIsNotNull(@Param("ids") List<String> ids, @Param("methodType") String methodType);

	


}
