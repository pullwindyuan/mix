package com.futuremap.custom.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.futuremap.custom.dto.template.UserTemplateQuery;
import com.futuremap.custom.service.ExcelService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("excel")
@Api(tags = "excel相关接口", description = "excel 导入　导出相关操作")
public class ExcelController {

	@Autowired
	ExcelService excelService;

	@PostMapping("/export/template")
	@ApiOperation("导出模板")
	public void exportTemplate(@RequestBody UserTemplateQuery userTemplateQuery, HttpServletResponse response) {
		excelService.exportTemplate(userTemplateQuery, response);
	}

	
	@PostMapping("/import/template")
	@ApiOperation("导入Excel")
	public void downloadTemplate(@RequestParam MultipartFile file,
			@ApiParam(name = "type", value = "类型 销售 sell ") String type,
			@ApiParam(name = "companyId", value = "公司ID") Integer companyId) throws Exception {
		excelService.importExcel(companyId, file, type);
	}

}
