package com.futuremap.custom.service;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import com.futuremap.custom.dto.template.UserTemplateQuery;

public interface ExcelService {

	/**
	 * @param userTemplateQuery
	 */
	void exportTemplate(UserTemplateQuery userTemplateQuery, HttpServletResponse response);

	/**
	 * @param companyId
	 * @param file
	 */
	void importExcel(Integer companyId, MultipartFile file, String type)  throws Exception;

}
