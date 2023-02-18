package com.futuremap.resourceManager.api;

import com.alibaba.fastjson.JSONObject;
import com.futuremap.annotation.CustomResponse;
import com.futuremap.resourceManager.service.MenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Api(value = "数据提取", tags = {"数据提取"})
@RestController
@RequestMapping("/config")
public class ConfigController {
	@Autowired
	private MenuService menuService;

	@PostMapping("/all")
	@ApiOperation(value = "获取所有配置", notes = "获取所有配置")
	@CustomResponse
	public JSONObject getConfig() {
		return menuService.getConfig();
	}

	@PostMapping("/nav")
	@ApiOperation(value = "获取导航菜单", notes = "获取导航菜单")
	@CustomResponse
	public List<JSONObject> getNavicat() {
		List<JSONObject> list = new ArrayList<>();
		list.add(menuService.getMenu("MENU0000"));
		list.add(menuService.getMenu("MENU0000"));
		return list;
	}

	@PostMapping("/scene")
	@ApiOperation(value = "获取场景配置", notes = "获取场景配置")
	@CustomResponse
	public List<JSONObject> getScene() {
		List<JSONObject> list = new ArrayList<>();
		list.add(menuService.getMenu("MENU0000"));
		return list;
	}

	@PostMapping("/cell")
	@ApiOperation(value = "获取细胞功能配置", notes = "获取细胞功能配置")
	@CustomResponse
	public List<JSONObject> getCell() {
		List<JSONObject> list = new ArrayList<>();
		list.add(menuService.getMenu("MENU0000"));
		list.add(menuService.getMenu("MENU0000"));
		return list;
	}

	@PostMapping("/function")
	@ApiOperation(value = "获取功能配置", notes = "获取功能配置")
	@CustomResponse
	public List<JSONObject> getFunction() {
		List<JSONObject> list = new ArrayList<>();
		list.add(menuService.getMenu("MENU0000"));
		list.add(menuService.getMenu("MENU0000"));
		return list;
	}

	@PostMapping("/view")
	@ApiOperation(value = "获取可视化配置", notes = "获取可视化配置")
	@CustomResponse
	public List<JSONObject> getView() {
		List<JSONObject> list = new ArrayList<>();
		list.add(menuService.getMenu("MENU0000"));
		list.add(menuService.getMenu("MENU0000"));
		return list;
	}

	@PostMapping("/element")
	@ApiOperation(value = "获取UI元素配置", notes = "获取UI元素配置")
	@CustomResponse
	public List<JSONObject> getElement() {
		List<JSONObject> list = new ArrayList<>();
		list.add(menuService.getMenu("MENU0000"));
		list.add(menuService.getMenu("MENU0000"));
		return list;
	}

	@PostMapping("/resource")
	@ApiOperation(value = "获取资源配置", notes = "获取资源配置")
	@CustomResponse
	public List<JSONObject> getResource() {
		List<JSONObject> list = new ArrayList<>();
		list.add(menuService.getMenu("MENU0000"));
		list.add(menuService.getMenu("MENU0000"));
		return list;
	}
}
