package com.futuremap.custom.mapper.mongo;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

import com.futuremap.custom.entity.mongo.Test;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author futuremap
 * @since 2021-01-27
 */
@Component
//extends MongoRepository<Test, String>
public interface MongodbMapper  {
	
}
