package com.futuremap.custom.entity;
import lombok.Data;
import java.sql.Timestamp;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@TableName("auth_user")
public class User extends BaseEntity{
	
	//@TableId(type = IdType.UUID)
    //private String id;
    
    //private String companyId;
    
    private String name;
    
    private String password;
    
    private String email;
    
    private String avatar;
    
    private String phone;
    
    private String role;
    
    private String position;
    
    private Timestamp updatedAt;
    
	private Timestamp createdAt;
}
