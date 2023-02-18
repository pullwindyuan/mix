package com.futuremap.erp.module.auth.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

/**
 * <p>
 * 授权用户表
 * </p>
 *
 * @author futuremap
 * @since 2021-06-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("auth_user")
@ApiModel(value="User对象", description="授权用户表")
public class User {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 公司编码
     */
    @ApiModelProperty(value = "公司编码")
    @Excel(name = "公司编码")
    private String companyCode;

    /**
     * 编号
     */
    @ApiModelProperty(value = "编号")
    private String code;

    /**
     * 手机号
     */
    @ApiModelProperty(value = "手机号")
    private String phone;

    /**
     * 电子邮箱
     */
    @ApiModelProperty(value = "电子邮箱")

    private String email;

    /**
     * 账号
     */
    @ApiModelProperty(value = "账号")
    private String account;

    /**
     * 姓名
     */
    @ApiModelProperty(value = "姓名")
    private String name;

    /**
     * 密码
     */
    @ApiModelProperty(value = "密码")
    @Length(min = 8, max = 16, message = "密码至少8-16个字符(字母或数字)，至少1个大写字母，1个小写字母和1个数字")
    private String password;

    /**
     * 用户类型 0 常规帐号 1体验帐号 2 群体体验
     */
    @ApiModelProperty(value = "用户类型 0 常规帐号 1体验帐号 2 群体体验")
    private Integer userType;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createdAt;

    /**
     * 物理删除状态 （0:正常;1:删除）
     */
    @ApiModelProperty(value = "物理删除状态 （0:正常;1:删除）")
    private Integer delStatus;

    @TableField(exist=false)
    private String oldPassword;
    @TableField(exist=false)
    private String newPassword;


}
