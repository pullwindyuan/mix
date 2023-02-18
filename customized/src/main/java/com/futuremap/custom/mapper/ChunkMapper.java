package com.futuremap.custom.mapper;


import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.futuremap.custom.entity.file.Chunk;

@Mapper
public interface ChunkMapper  extends BaseMapper<Chunk> {
	
}
