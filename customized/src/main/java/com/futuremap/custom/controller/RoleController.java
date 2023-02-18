package com.futuremap.custom.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.futuremap.custom.annotation.CustomResponse;
import com.futuremap.custom.dto.function.FunctionRoleCreate;
import com.futuremap.custom.dto.function.FunctionSummary;
import com.futuremap.custom.dto.role.RoleCreate;
import com.futuremap.custom.dto.role.RoleUpdate;
import com.futuremap.custom.entity.Role;
import com.futuremap.custom.service.impl.RoleServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 *
 * @author futuremap
 * @since 2021-01-27
 */
@RestController
@Api(tags = "角色与权限相关接口", description = "角色接口等")
public class RoleController {
	

	@Autowired
	RoleServiceImpl roleServiceImpl;

	@PostMapping("/role")
    @ApiOperation("添加角色")
	@CustomResponse
    public void create(@RequestBody RoleCreate roleCreate) {
		roleServiceImpl.create(roleCreate);
    }
	
	
	@GetMapping("/role")
    @ApiOperation("查询角色列表")
	@CustomResponse
    public List<Role> queryRole() {
		return roleServiceImpl.list(null);
    }
	
	
	@DeleteMapping("/role")
    @ApiOperation("修改角色")
	@CustomResponse
    public void updateRole(@RequestBody RoleUpdate roleUpdate) {
		roleServiceImpl.updateRole(roleUpdate);
    }
	
	@DeleteMapping("/role/{roleId}")
    @ApiOperation("删除角色")
	@CustomResponse
    public void deleteRole(@PathVariable String roleId) {
		roleServiceImpl.deleteRole(roleId);
    }
	
	@GetMapping("/roleFunction/{roleId}")
    @ApiOperation("查询角色的权限")
	@CustomResponse
    public List<FunctionSummary> queryRoleFunction(@PathVariable String roleId) {
		return roleServiceImpl.getFunctions(roleId);
    }
	
	
	@PostMapping("/role/function")
    @ApiOperation("添加角色的权限")
	@CustomResponse
    public void create(@RequestBody FunctionRoleCreate functionRoleCreate) {
		roleServiceImpl.createFunctionRole(functionRoleCreate);
    }
	
	

}
