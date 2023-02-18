package com.futuremap.custom.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.futuremap.custom.dto.function.FunctionRoleCreate;
import com.futuremap.custom.dto.function.FunctionSummary;
import com.futuremap.custom.dto.role.RoleCreate;
import com.futuremap.custom.dto.role.RoleUpdate;
import com.futuremap.custom.entity.Role;
import com.futuremap.custom.entity.RoleFunction;
import com.futuremap.custom.mapper.RoleMapper;
import com.futuremap.custom.service.AuthRoleService;
import com.futuremap.custom.util.TreeUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements AuthRoleService {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	FunctionServiceImpl functionService;

	@Autowired
	RoleFunctionServiceImpl roleFunctionService;
	
	@Autowired
	UserRoleServiceImpl userRoleService;

	public RoleMapper getMapper() {
		return this.baseMapper;
	}

	/**
	 * @param ids
	 * @return
	 */
	public List<Role> findByIdIn(List<String> ids) {
		if (CollectionUtils.isEmpty(ids)) {
			return new ArrayList<>();
		}
		return getMapper().selectByIdIn(ids);
	}

	/**
	 * @param roleCreate
	 */
	public void create(RoleCreate roleCreate) {
		Role role = modelMapper.map(roleCreate, Role.class);
		this.save(role);
	}

	/**
	 * @param functionRoleCreate
	 */
	public void createFunctionRole(FunctionRoleCreate fr) {
		roleFunctionService.deleteByRoleId(fr.getRoleId());
		String roleId = fr.getRoleId();
		List<RoleFunction> rfs = fr.getFunctionIds().stream()
				.map(e -> RoleFunction.builder().roleId(roleId).functionId(e).build()).collect(Collectors.toList());
		roleFunctionService.saveBatch(rfs);
	}

	/**
	 * @param roleId
	 * @return
	 */
	public List<FunctionSummary> getFunctions(String roleId) {

		List<FunctionSummary> functionSummaries = functionService.list(null).stream()
				.map(e -> modelMapper.map(e, FunctionSummary.class)).collect(Collectors.toList());
		List<RoleFunction> rf = roleFunctionService.selectByRoleId(roleId);
		Map<String, String> functionMap = rf.stream().collect(Collectors.toMap(RoleFunction::getFunctionId, e->e.getId()));
		functionSummaries.forEach(e-> {
			if(functionMap.get(e.getId()) == null) {
				e.setSelect(0);
			}else {
				e.setSelect(1);
			}
		});
		return TreeUtil.getZeroParentTree(functionSummaries);
	
	}

	/**
	 * @return
	 */
	public List<FunctionSummary> listFunction() {
		return functionService.list(null).stream().map(e->modelMapper.map(e, FunctionSummary.class)).collect(Collectors.toList());
	}

	/**
	 * @param collect
	 * @return
	 */
	public Map<String, Role> selectMapByIdIn(List<String> ids) {
		QueryWrapper<Role> query = new QueryWrapper<Role>();
		query.in("id", ids);
	    return this.list(query).stream().collect(Collectors.toMap(e->e.getId(), e->e));
	}

	/**
	 * @param roleId
	 */
	public void deleteRole(String roleId) {
		this.baseMapper.deleteById(roleId);
		userRoleService.deleteByRoleId(roleId);
	}

	/**
	 * @param roleUpdate
	 */
	public void updateRole(RoleUpdate roleUpdate) {
		Role role = modelMapper.map(roleUpdate, Role.class);
		this.saveOrUpdate(role);
	}


}
