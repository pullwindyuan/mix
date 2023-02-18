package com.futuremap.datamanager.controller;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import com.futuremap.annotation.CustomResponse;
import com.futuremap.datamanager.dataProcess.Extract;
import com.futuremap.base.dictionary.bean.VO.DictionaryForestVO;
import com.futuremap.datamanager.dataProcess.TestSortForest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(value = "数据提取", tags = {"数据提取"})
@RestController
@RequestMapping("/process")
public class ExtractForestController {
	@Autowired
	private Extract extract;

	@Autowired
	private TestSortForest testSortForest;

	@PostMapping("/forest/{templateId}")
	@ApiOperation(value = "获取从数据中提取的分类筛选目录树", notes = "templateId为和导入的数据配套的表头配置模板ID")
	@CustomResponse
	public DictionaryForestVO getDictionaryForestFromData(@PathVariable String templateId) {
		return extract.getForestByTemplateId(templateId);
	}

	@PostMapping("/extract")
	@ApiOperation(value = "获取从数据中提取的分类筛选目录树", notes = "templateId为和导入的数据配套的表头配置模板ID")
	@CustomResponse
	public DictionaryForestVO getDictionaryForest() {
		return extract.getForestByTemplateId("617fba4abc59492bd781eff2");
	}

	@PostMapping("/forest/msg")
	@ApiOperation(value = "测试通过消息队列驱动从数据中提取的分类筛选目录树", notes = "templateId为和导入的数据配套的表头配置模板ID")
	@CustomResponse
	public DictionaryForestVO processData() {
		return testSortForest.testExtractForestFromTable();
	}
}
