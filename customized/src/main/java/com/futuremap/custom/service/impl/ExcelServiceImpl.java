package com.futuremap.custom.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.futuremap.custom.dto.template.BasicTemplate;
import com.futuremap.custom.dto.template.UserTemplateQuery;
import com.futuremap.custom.entity.Template;
import com.futuremap.custom.exception.BadRequestException;
import com.futuremap.custom.service.ExcelService;
import com.futuremap.custom.service.ITemplateService;
import com.futuremap.custom.service.UserService;
import com.futuremap.custom.util.DateUtil;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.usermodel.DataValidationHelper;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFDataValidation;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author futuremap
 * @since 2021-01-27
 */
@Service
@Slf4j
public class ExcelServiceImpl implements ExcelService {
	
	protected final Logger logger = LoggerFactory.getLogger(UserService.class);
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	MongoTemplate mongoTemplate;
	
	@Autowired
	ITemplateService templateService;
	
	private List<Template> templateList = new ArrayList<Template>();

	
	 /**
     * excel 扩展名 old
     */
    String EXCEL_EXTENSION_XLS = ".xls";
    /**
     * excel 扩展名 new
     */
    String EXCEL_EXTENSION_XLSX = ".xlsx";

	@Override
	public void exportTemplate(UserTemplateQuery userTemplateQuery, HttpServletResponse response) {
		List<Template> list = getTemplate(userTemplateQuery);
		try {
			exportTemplate(response, list, userTemplateQuery);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	private List<Template> getTemplate(UserTemplateQuery userTemplateQuery) {
		List<BasicTemplate> userTemplate = templateService.searchUserTemplate(userTemplateQuery);
		Optional<BasicTemplate> option = userTemplate.stream().findFirst();
		if (!option.isPresent()) {
			throw new BadRequestException("");
		}
		BasicTemplate ut = option.get();
		
		JSONObject kv = ut.getKv();
		log.info(JSON.toJSONString(kv));
		List<Template> list = new ArrayList<>();
		// 排序
		kv.keySet().forEach(key -> {
			Template template = new Template();
			// 字段名
			template.setEn(key);
			JSONObject column = kv.getJSONObject(key);
			// 名称
			template.setName(column.getString("name"));
			// 排序
			template.setSort(column.getInteger("index"));
			
			JSONObject rule = column.getJSONObject("rule");
			JSONObject mysql = rule.getJSONObject("mysql");
			String dataType = "varchar";
			if(mysql != null) {
				 dataType = mysql.getString("dataType");
			}
		
			template.setDateType(dataType);
			
			list.add(template);
		});
		list.sort((a,b)->Integer.compare(a.getSort(), b.getSort()));
		return list;
	}
	
	
	
	public void exportTemplate(HttpServletResponse response, List<Template> templateList, UserTemplateQuery userTemplateQuery) throws Exception {
		String type = userTemplateQuery.getType();
		if (null == templateList || templateList.size() == 0) {
			throw new Exception("未配置此模板数据");
		}
		// 创建xSSFWorkbook对象
		XSSFWorkbook wb = new XSSFWorkbook();
		// 创建xSSFSheet对象
		XSSFSheet sheet = wb.createSheet("template");
		// 设置缺省列高
		sheet.setDefaultRowHeightInPoints(20);
		// 设置缺省列宽
		sheet.setDefaultColumnWidth(20);
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, templateList.size() - 1));
		// 创建xSSFRow对象
		XSSFRow row1 = sheet.createRow(0);
		row1.setHeight((short) 1400);

		// 创建xSSFCell对象 第一行说明
		XSSFCell cell = row1.createCell(0);
		CellStyle cs = wb.createCellStyle();
		cs.setWrapText(true);
		cell.setCellStyle(cs);
		// 设置单元格的值
		cell.setCellValue(new XSSFRichTextString(getHeadTips(type)));
		// 版本校验
		setVailCell(type, wb, row1.createCell(templateList.size() + 2));
		// 第二行字段行
		XSSFRow row2 = sheet.createRow(1);
		row2.setZeroHeight(true);
		// 第三行字段行
		XSSFRow row3 = sheet.createRow(2);

		// 日期格式
		XSSFCellStyle dateStyle = wb.createCellStyle();
		XSSFDataFormat dateformat = wb.createDataFormat();
		dateStyle.setDataFormat(dateformat.getFormat("yyyy-MM-dd"));
		// cell.setCellStyle(dateStyle);

		XSSFCellStyle textStyle = wb.createCellStyle();
		XSSFDataFormat format = wb.createDataFormat();
		textStyle.setDataFormat(format.getFormat("@"));

		DataValidationHelper helper = sheet.getDataValidationHelper();// 设置下拉框xlsx格式
		for (int i = 0; i < templateList.size(); i++) {
			Template template = templateList.get(i);
			// 创建XSSFCell对象
			XSSFCell cell2 = row2.createCell(i);
			cell2.setCellValue(template.getEn());
			XSSFCell cell3 = row3.createCell(i);
			cell3.setCellStyle(getHeaderStyle(wb));
			// 设置单元格的值 *
			cell3.setCellValue(template.getIsRequired() == 1 ? "*" + template.getName() : template.getName());

			if (template.getEn().contains("Flag")) {
				String[] typeDesc = { "是", "否" };
				creatDropDownList(sheet, helper, typeDesc, 1, 1000, i, i, true);
			}

			if (template.getDateType().contains("timestamp")) {
				sheet.setDefaultColumnStyle(i, textStyle);
				// 结束时间需要在开始时间下一个
				// sheet.setDefaultColumnStyle((i + 1), dateStyle);

			}
			sheet.autoSizeColumn(i);
			sheet.setColumnWidth(i, sheet.getColumnWidth(i) * 17 / 10);
		}
		response.setContentType("application/octet-stream");
		response.setHeader("Content-disposition", "attachment;filename=" + type + ".xlsx");
		response.flushBuffer();
		wb.write(response.getOutputStream());
	}
	
	
	/**
	 * 版本位校验
	 */
	private void setVailCell(String type, XSSFWorkbook wb, XSSFCell cell) {
		CellStyle cs = wb.createCellStyle();
		cs.setFillForegroundColor(IndexedColors.WHITE.getIndex());
		Font font = wb.createFont();
		font.setColor(IndexedColors.WHITE.getIndex());
		cs.setFont(font);
		cell.setCellStyle(cs);
		cell.setCellValue("test");
	}

	/**
	 * 头部提示
	 * 
	 * @param type
	 * @return
	 */
	private String getHeadTips(String type) {
		String str = "";
		switch (type) {
		case "sell":
			str = "温馨提示：" + "\r\n" + "1.带*的为必填项 \r\n 2.时间类型字段请严格按照 XXXX-XX-XX 格式来填写，如：2021年1月1日，要这样填写：2021-01-01（正确）";
			break;
		default:
			return "无";
		}
		return str;
	}

	/**
	 * 头部样式
	 * 
	 * @param wb
	 * @return
	 */
	private XSSFCellStyle getHeaderStyle(XSSFWorkbook wb) {
		XSSFCellStyle headerStyle = wb.createCellStyle();
		headerStyle.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.index);
		headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		return headerStyle;
	}
	
	// 创建下拉框
	private static void creatDropDownList(XSSFSheet taskInfoSheet, DataValidationHelper helper, String[] list,
			Integer firstRow, Integer lastRow, Integer firstCol, Integer lastCol, boolean errorStatus) {
		CellRangeAddressList addressList = new CellRangeAddressList(firstRow, lastRow, firstCol, lastCol);
		// 设置下拉框数据
		DataValidationConstraint constraint = helper.createExplicitListConstraint(list);
		DataValidation dataValidation = helper.createValidation(constraint, addressList);
		// 处理Excel兼容性问题
		if (dataValidation instanceof XSSFDataValidation) {
			dataValidation.setSuppressDropDownArrow(true);
			dataValidation.setShowErrorBox(errorStatus);
		} else {
			dataValidation.setSuppressDropDownArrow(false);
		}
		taskInfoSheet.addValidationData(dataValidation);
	}


	/**
	 * 导入EXCEL表格
	 * 
	 * @param file
	 * @param type
	 * @param companyId
	 * @throws Exception
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void importExcel(Integer companyId, MultipartFile file, String type) throws Exception {
		// 参数判断
		if (file == null || file.getResource() == null) {
			throw new BadRequestException("文件模板格式不正确，请下载最新模板后再进行导入操作！");
		}
		// 文件名
		String filename = file.getResource().getFilename().toLowerCase();
		if (!filename.endsWith(EXCEL_EXTENSION_XLSX)
				&& !filename.endsWith(EXCEL_EXTENSION_XLS)) {
			throw new BadRequestException("文件模板格式不正确，请下载最新模板后再进行导入操作！");
		}

		XSSFWorkbook wb = new XSSFWorkbook(file.getInputStream());
		XSSFSheet sheet = wb.getSheet("template");
		UserTemplateQuery userTemplateQuery = new UserTemplateQuery();
		userTemplateQuery.setCompanyId(companyId.toString());
		userTemplateQuery.setType(type);
		templateList = getTemplate(userTemplateQuery);
		if (null == templateList || templateList.size() == 0) {
			throw new Exception("未配置此模板数据 type: " + type);
		}
		if (null == sheet) {
			throw new Exception("请下载最新模板后再进行导入操作");
		}

		XSSFRow row1 = sheet.getRow(0);

		// 验证版本
		//vailVersion(type, row1, templateList.size() + 2);

		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		int length = sheet.getPhysicalNumberOfRows();
		if(length > 1003) {
			throw new  BadRequestException("一次最多允许添加1000条记录");
		}
		// 跳过前三行
		for (int j = 3; j < length; j++) {
			XSSFRow row = sheet.getRow(j);
			// 如果当前行没有数据，跳出循环
			try {
				if (row.getCell(2).toString().equals("")) {
					throw new Exception("第" + (j + 1) + "行不能为空!请修改后重试!");
				}
			} catch (NullPointerException e1) {
				throw new Exception("第" + (j + 1) + "行不能为空!必填项不能为空，请修改后重试!");
			} catch (Exception e) {
				throw new Exception("第" + (j + 1) + "行数据异常!必填项不能为空，请修改后重试!");
			}
			try {
				// 验证空值情况
				vaildRequired(row);
				// 再进行合法性校验 以及设置初值
				vaildDataRule(templateList, type, row);
			} catch (Exception e) {
				log.error("导入异常：", e);
				throw new Exception("第 " + (j + 1) + "行 " + e.getMessage());
			}

			// 通过全部验证与设置值后拼装数据
			dataList.add(getDataMap(row));
		}
		// 暂时不考虑导入的数据中原有的重复数据
		try {
			batchInsert(dataList);
		} catch (Exception e) {
			log.error("导入异常：", e);
			throw new BadRequestException(e.getMessage());
		}

	}
	
	/**
	 * 校验必填
	 * 
	 * @param row
	 * @throws Exception
	 */
	private void vaildRequired(XSSFRow row) throws Exception {
		// 必填校验
		for (int i = 0; i < templateList.size(); i++) {
			Cell cell = row.getCell(i);
			// 未设置情况
			if (cell == null) {
				if (1 == templateList.get(i).getIsRequired()) {
					throw new Exception(templateList.get(i).getName() + "为必填项,不能为空!");
				}
				continue;
			}
			// 设置空值情况
			cell.setCellType(CellType.STRING);
			String cellVal = cell.getStringCellValue();
			if (1 == templateList.get(i).getIsRequired() && (cellVal == null || "".equals(cellVal.trim()))) {
				throw new Exception(templateList.get(i).getName() + "为必填项,不能为空!");
			}
		}
	}
	
	/**
	 * 拼装数据
	 *
	 * @param row
	 * @return
	 */
	private Map<String, Object> getDataMap(XSSFRow row) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		int end = row.getLastCellNum();
		log.info("end" + end);
		for (int i = 0; i < end; i++) {
			Cell cell = row.getCell(i);
			dataMap.put(templateList.get(i).getEn(), getValue(cell));
		}
		return dataMap;
	}

	private Object getValue(Cell cell) {
		Object obj = null;
		if (cell == null) {
			return null;
		}
		switch (cell.getCellType()) {
		case BOOLEAN:
			obj = cell.getBooleanCellValue();
			break;
		case ERROR:
			obj = cell.getErrorCellValue();
			break;
		case NUMERIC:
			// 如果判断条件里面的条件报红，就自己set进去
			short format = cell.getCellStyle().getDataFormat();
			SimpleDateFormat sdf = null;
			if (format == 14 || format == 31 || format == 57 || format == 58) {
				// 日期
				sdf = new SimpleDateFormat("yyyy-MM-dd");
			} else if (format == 20 || format == 32) {
				// 时间
				sdf = new SimpleDateFormat("HH:mm");
			} // 如果报空指针，就自己定义一个format,符合自己数据的
			double value = cell.getNumericCellValue();
			Date date = org.apache.poi.ss.usermodel.DateUtil.getJavaDate(value);
			obj = sdf.format(date);
			break;
		case STRING:
			obj = cell.getStringCellValue().trim();
			break;
		default:
			break;
		}
		return obj;
	}
	
	/**
	 * 校验位
	 * 
	 * @param row
	 * @param i
	 * @throws Exception
	 */
	private void vailVersion(String type, XSSFRow row, int i) throws Exception {
		try {
			String verStr = row.getCell(i).getStringCellValue();
			String version = "test";
			if (!version.equals(verStr)) {
				throw new Exception("请下载最新模板后再进行导入操作");
			}
		} catch (Exception e) {
			throw new Exception("请下载最新模板后再进行导入操作");
		}

	}
	
	@Transactional(rollbackFor = Exception.class)
	protected void vaildDataRule(List<Template> templates, String type, XSSFRow row) throws Exception {
		vaildCommentRule(templates, row);
	}

	private void vaildCommentRule(List<Template> templates, XSSFRow row) {
		for (int i = 0; i < templates.size(); i++) {
			Template template = templates.get(i);
			if (template.getPattern() != null) {
				Cell cell = row.getCell(i);
				String value = (String) getValue(cell);
				if (StringUtils.isNotEmpty(value) && !isRegEx(value, template.getPattern())) {
					log.info("value" + value);
					throw new BadRequestException(template.getName() + " " + template.getTips());
				}
			}
		}

	}
	
	public boolean isRegEx(String string, String regEx) {
		if (string == null) {
			return false;
		}
		Pattern p;
		Matcher m;
		p = Pattern.compile(regEx);
		m = p.matcher(string);
		if (m.matches()) {
			return true;
		}
		else {
			return false;
		}
	}

	
	/**
	 * @param dataList
	 */
	private void batchInsert(List<Map<String, Object>> dataList) {
		/**
		dataList.forEach(e -> {
			try {
				UserSell sell = (UserSell) this.mapToObject4Excel(e, UserSell.class);
				sell.setCompanyId(companyId);
				userSell.add(sell);
			} catch (Exception e1) {
				log.error("mapToObject4Excel", e);
				log.error("mapToObject4Excel", e1);
			}
		});
		//log.error("saveBatch" + JSON.toJSONString(userSell));
		 * 
		 */
		mongoTemplate.dropCollection("user_sell");
		dataList.forEach(value -> {
			try {
				this.mapToObject4Excel(value);
				mongoTemplate.insert(value, "user_sell");
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		
		//iUserSellService.saveBatch(userSell, 1000);
	}
	
	// Map转Object
	public  Object mapToObject4Excel(Map<String, Object> map, Class<?> beanClass) throws Exception {
		if (map == null) {
			return null;
		}
		Map<String, String> templateMap  = templateList.stream().collect(Collectors.toMap(e->e.getEn(), e->e.getDateType()));
		Object obj = beanClass.newInstance();
		Field[] fields = obj.getClass().getDeclaredFields();
		for (Field field : fields) {
			int mod = field.getModifiers();
			if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
				continue;
			}
			field.setAccessible(true);
			if (map.containsKey(field.getName())) {

				Object str = map.get(field.getName());
				if (str != null && StringUtils.isNotEmpty(str.toString())) {
					
					String dateType = templateMap.get(field.getName());
					
					if (dateType.contains("boolean")) {
						map.put(field.getName(), "是".equals(map.get(field.getName())) ? 1 : 0);
					}
					// poi 的时间 导入 有时间是数字 有时候是字符串 导致时间类型的数据不好获取与判断 统一在这里处理
					if (dateType.contains("timestamp")) {
						try {
							try {
								map.put(field.getName(),
										DateUtil.getTimeStamp(map.get(field.getName()).toString(), "yyyy-MM-dd"));
							} catch (Exception e1) {
								Date date = org.apache.poi.ss.usermodel.DateUtil
										.getJavaDate(Double.valueOf(map.get(field.getName()).toString()));
								map.put(field.getName(), new Timestamp(date.getTime()));
							}
						} catch (Exception ex) {
							log.error("", ex);
							throw new BadRequestException("请输入格式为  2020-01-01 时间字符 或 单元格格式为日期的数据");
						}

					}
					if (dateType.contains("decimal")) {
						map.put(field.getName(), new BigDecimal(map.get(field.getName()).toString()));
					}
				} else {
					map.put(field.getName(), null);
				}

				field.set(obj, map.get(field.getName()));
			}
		}
		return obj;
	}
	
	// Map转Object
		public  void mapToObject4Excel(Map<String, Object> map) throws Exception {
			if (map == null) {
				return ;
			}
			Map<String, String> templateMap  = templateList.stream().collect(Collectors.toMap(e->e.getEn(), e->e.getDateType()));
			for (String field : map.keySet()) {
				if (map.containsKey(field)) {

					Object str = map.get(field);
					if (str != null && StringUtils.isNotEmpty(str.toString())) {
						
						String dateType = templateMap.get(field);
						
						if (dateType.contains("boolean")) {
							map.put(field, "是".equals(map.get(field)) ? 1 : 0);
						}
						// poi 的时间 导入 有时间是数字 有时候是字符串 导致时间类型的数据不好获取与判断 统一在这里处理
						if (dateType.contains("timestamp")) {
							try {
								try {
									map.put(field,
											DateUtil.getTimeStamp(map.get(field).toString(), "yyyy-MM-dd"));
								} catch (Exception e1) {
									Date date = org.apache.poi.ss.usermodel.DateUtil
											.getJavaDate(Double.valueOf(map.get(field).toString()));
									map.put(field, new Timestamp(date.getTime()));
								}
							} catch (Exception ex) {
								log.error("", ex);
								throw new BadRequestException("请输入格式为  2020-01-01 时间字符 或 单元格格式为日期的数据");
							}

						}
						if (dateType.contains("decimal")) {
							map.put(field, new BigDecimal(map.get(field).toString()));
						}
					} else {
						map.put(field, null);
					}
				}
			}
			return;
		}
}
