package com.futuremap.custom.entity.mongo;
import lombok.Data;
import java.sql.Timestamp;

import org.springframework.data.mongodb.core.mapping.Document;

import com.baomidou.mybatisplus.annotation.TableName;

@Data
//@TableName("test")
@Document(collection = "test")
public class Test {
	
    
    private String test;
    
    private String password;
    
    private String email;
    
    private String avatar;
    
    private String phone;
    
    private String role;
    
    private String position;
    
    private Timestamp updatedAt;
    
	private Timestamp createdAt;
}
