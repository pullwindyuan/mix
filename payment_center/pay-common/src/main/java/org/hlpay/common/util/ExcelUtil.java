 package org.hlpay.common.util;

 import com.alibaba.fastjson.JSONObject;
 import java.io.File;
 import java.io.FileOutputStream;
 import java.io.IOException;
 import java.io.InputStream;
 import java.io.OutputStream;
 import java.lang.reflect.Field;
 import java.lang.reflect.Method;
 import java.math.BigDecimal;
 import java.text.DecimalFormat;
 import java.util.ArrayList;
 import java.util.Arrays;
 import java.util.Date;
 import java.util.HashMap;
 import java.util.List;
 import java.util.Map;
 import java.util.UUID;
 import org.apache.poi.hssf.usermodel.HSSFDateUtil;
 import org.apache.poi.ss.usermodel.BorderStyle;
 import org.apache.poi.ss.usermodel.Cell;
 import org.apache.poi.ss.usermodel.CellStyle;
 import org.apache.poi.ss.usermodel.CellType;
 import org.apache.poi.ss.usermodel.DataValidation;
 import org.apache.poi.ss.usermodel.DataValidationConstraint;
 import org.apache.poi.ss.usermodel.DataValidationHelper;
 import org.apache.poi.ss.usermodel.DateUtil;
 import org.apache.poi.ss.usermodel.FillPatternType;
 import org.apache.poi.ss.usermodel.Font;
 import org.apache.poi.ss.usermodel.HorizontalAlignment;
 import org.apache.poi.ss.usermodel.IndexedColors;
 import org.apache.poi.ss.usermodel.Row;
 import org.apache.poi.ss.usermodel.Sheet;
 import org.apache.poi.ss.usermodel.VerticalAlignment;
 import org.apache.poi.ss.usermodel.Workbook;
 import org.apache.poi.ss.usermodel.WorkbookFactory;
 import org.apache.poi.ss.util.CellRangeAddressList;
 import org.apache.poi.xssf.streaming.SXSSFWorkbook;
 import org.hlpay.common.annotation.Excel;
 import org.hlpay.common.annotation.Excels;
 import org.hlpay.common.entity.CommonResult;
 import org.springframework.core.env.Environment;

 public class ExcelUtil<T>
 {
   public static final int sheetSize = 65536;
   private Environment env;
   private String sheetName;
   private Excel.Type type;
   private Workbook wb;
   private Sheet sheet;
   private Map<String, CellStyle> styles;
   private List<T> list;
   private List<Object[]> fields;
   public Class<T> clazz;

   public ExcelUtil(Class<T> clazz) {
     this.clazz = clazz;
   }

   public void init(List<T> list, String sheetName, Excel.Type type) {
     if (list == null) {
       list = new ArrayList<>();
     }
     this.list = list;
     this.sheetName = sheetName;
     this.type = type;
     createExcelField();
     createWorkbook();
   }







   public List<T> importExcel(InputStream is) throws Exception {
     return importExcel("", is);
   }








   public List<T> importExcel(String sheetName, InputStream is) throws Exception {
     this.type = Excel.Type.IMPORT;
     this.wb = WorkbookFactory.create(is);
     List<T> list = new ArrayList<>();
     Sheet sheet = null;
     if (StringUtil.isNotEmpty(sheetName)) {

       sheet = this.wb.getSheet(sheetName);
     } else {

       sheet = this.wb.getSheetAt(0);
     }

     if (sheet == null) {
       throw new IOException("文件sheet不存在");
     }

     int rows = sheet.getPhysicalNumberOfRows();

     if (rows > 0) {

       Map<String, Integer> cellMap = new HashMap<>();

       Row heard = sheet.getRow(0);
       for (int i = 0; i < heard.getPhysicalNumberOfCells(); i++) {
         Cell cell = heard.getCell(i);
         if (StringUtil.isNotNull(Boolean.valueOf((cell != null)))) {
           String value = getCellValue(heard, i).toString();
           cellMap.put(value, Integer.valueOf(i));
         } else {
           cellMap.put(null, Integer.valueOf(i));
         }
       }

       Field[] allFields = this.clazz.getDeclaredFields();

       Map<Integer, Field> fieldsMap = new HashMap<>();
       for (int col = 0; col < allFields.length; col++) {
         Field field = allFields[col];
         Excel attr = field.<Excel>getAnnotation(Excel.class);
         if (attr != null && (attr.type() == Excel.Type.ALL || attr.type() == this.type)) {

           field.setAccessible(true);
           Integer column = cellMap.get(attr.name());
           fieldsMap.put(column, field);
         }
       }
       for (int j = 1; j < rows; j++) {

         Row row = sheet.getRow(j);
         T entity = null;
         for (Map.Entry<Integer, Field> entry : fieldsMap.entrySet()) {
           Object val = getCellValue(row, ((Integer)entry.getKey()).intValue());


           entity = (entity == null) ? this.clazz.newInstance() : entity;

           Field field = fieldsMap.get(entry.getKey());

           Class<?> fieldType = field.getType();
           if (String.class == fieldType) {
             String s = Convert.toStr(val);
             if (StringUtil.endsWith(s, ".0")) {
               val = StringUtil.substringBefore(s, ".0");
             } else {
               val = Convert.toStr(val);
             }
           } else if (int.class == fieldType || Integer.class == fieldType) {
             val = Convert.toInt(val);
           } else if (long.class == fieldType || Long.class == fieldType) {
             val = Convert.toLong(val);
           } else if (double.class == fieldType || Double.class == fieldType) {
             val = Convert.toDouble(val);
           } else if (float.class == fieldType || Float.class == fieldType) {
             val = Convert.toFloat(val);
           } else if (BigDecimal.class == fieldType) {
             val = Convert.toBigDecimal(val);
           } else if (Date.class == fieldType) {
             if (val instanceof String) {
               val = DateUtils.parseDate(val);
             } else if (val instanceof Double) {
               val = DateUtil.getJavaDate(((Double)val).doubleValue());
             }
           }
           if (StringUtil.isNotNull(fieldType)) {
             Excel attr = field.<Excel>getAnnotation(Excel.class);
             String propertyName = field.getName();
             if (StringUtil.isNotEmpty(attr.targetAttr())) {
               propertyName = field.getName() + "." + attr.targetAttr();
             } else if (StringUtil.isNotEmpty(attr.readConverterExp())) {
               val = reverseByExp(String.valueOf(val), attr.readConverterExp());
             }
             ReflectUtils.invokeSetter(entity, propertyName, val);
           }
         }
         list.add(entity);
       }
     }
     return list;
   }









   public CommonResult exportExcel(List<T> list, String sheetName, Environment env) {
     init(list, sheetName, Excel.Type.EXPORT);
     this.env = env;
     return exportExcel();
   }







   public CommonResult importTemplateExcel(String sheetName) {
     init(null, sheetName, Excel.Type.IMPORT);
     return exportExcel();
   }






   public CommonResult exportExcel() {
     OutputStream out = null;

     try {
       double sheetNo = Math.ceil((this.list.size() / 65536));
       for (int index = 0; index <= sheetNo; index++) {
         createSheet(sheetNo, index);


         Row row = this.sheet.createRow(0);
         int column = 0;

         for (Object[] os : this.fields) {
           Excel excel = (Excel)os[1];
           createCell(excel, row, column++);
         }
         if (Excel.Type.EXPORT.equals(this.type)) {
           fillExcelData(index, row);
         }
       }
       String filename = encodingFilename(this.sheetName);
       out = new FileOutputStream(getAbsoluteFile(filename));
       this.wb.write(out);
       return CommonResult.success(filename);
     } catch (Exception e) {
       throw new RuntimeException("导出Excel失败-" + JSONObject.toJSONString(e.getStackTrace()));
     } finally {
       if (this.wb != null) {
         try {
           this.wb.close();
         } catch (IOException e1) {
           e1.printStackTrace();
         }
       }
       if (out != null) {
         try {
           out.close();
         } catch (IOException e1) {
           e1.printStackTrace();
         }
       }
     }
   }







   public void fillExcelData(int index, Row row) {
     int startNo = index * 65536;
     int endNo = Math.min(startNo + 65536, this.list.size());
     for (int i = startNo; i < endNo; i++) {
       row = this.sheet.createRow(i + 1 - startNo);

       T vo = this.list.get(i);
       int column = 0;
       for (Object[] os : this.fields) {
         Field field = (Field)os[0];
         Excel excel = (Excel)os[1];

         field.setAccessible(true);
         addCell(excel, row, vo, field, column++);
       }
     }
   }








   private Map<String, CellStyle> createStyles(Workbook wb) {
     Map<String, CellStyle> styles = new HashMap<>();
     CellStyle style = wb.createCellStyle();
     style.setAlignment(HorizontalAlignment.CENTER);
     style.setVerticalAlignment(VerticalAlignment.CENTER);
     style.setBorderRight(BorderStyle.THIN);
     style.setRightBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
     style.setBorderLeft(BorderStyle.THIN);
     style.setLeftBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
     style.setBorderTop(BorderStyle.THIN);
     style.setTopBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
     style.setBorderBottom(BorderStyle.THIN);
     style.setBottomBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
     Font dataFont = wb.createFont();
     dataFont.setFontName("Arial");
     dataFont.setFontHeightInPoints((short)10);
     style.setFont(dataFont);
     styles.put("data", style);

     style = wb.createCellStyle();
     style.cloneStyleFrom(styles.get("data"));
     style.setAlignment(HorizontalAlignment.CENTER);
     style.setVerticalAlignment(VerticalAlignment.CENTER);
     style.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
     style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
     Font headerFont = wb.createFont();
     headerFont.setFontName("Arial");
     headerFont.setFontHeightInPoints((short)10);
     headerFont.setBold(true);
     headerFont.setColor(IndexedColors.WHITE.getIndex());
     style.setFont(headerFont);
     styles.put("header", style);

     return styles;
   }





   public Cell createCell(Excel attr, Row row, int column) {
     Cell cell = row.createCell(column);

     cell.setCellValue(attr.name());
     setDataValidation(attr, row, column);
     cell.setCellStyle(this.styles.get("header"));
     return cell;
   }








   public void setCellVo(Object value, Excel attr, Cell cell) {
     if (Excel.ColumnType.STRING == attr.cellType()) {
       cell.setCellType(CellType.NUMERIC);
       cell.setCellValue(StringUtil.isNull(value) ? attr.defaultValue() : (value + attr.suffix()));
     } else if (Excel.ColumnType.NUMERIC == attr.cellType()) {
       cell.setCellType(CellType.NUMERIC);
       cell.setCellValue(Integer.parseInt(value + ""));
     }
   }




   public void setDataValidation(Excel attr, Row row, int column) {
     if (attr.name().indexOf("注：") >= 0) {
       this.sheet.setColumnWidth(column, 6000);
     } else {

       this.sheet.setColumnWidth(column, (int)((attr.width() + 0.72D) * 256.0D));
       row.setHeight((short)(int)(attr.height() * 20.0D));
     }

     if (StringUtil.isNotEmpty(attr.prompt()))
     {
       setXSSFPrompt(this.sheet, "", attr.prompt(), 1, 100, column, column);
     }

     if ((attr.combo()).length > 0)
     {
       setXSSFValidation(this.sheet, attr.combo(), 1, 100, column, column);
     }
   }




   public Cell addCell(Excel attr, Row row, T vo, Field field, int column) {
     Cell cell = null;

     try {
       row.setHeight((short)(int)(attr.height() * 20.0D));

       if (attr.isExport()) {

         cell = row.createCell(column);
         cell.setCellStyle(this.styles.get("data"));


         Object value = getTargetValue(vo, field, attr);
         String dateFormat = attr.dateFormat();
         String readConverterExp = attr.readConverterExp();
         if (StringUtil.isNotEmpty(dateFormat) && StringUtil.isNotNull(value)) {
           cell.setCellValue(DateUtils.parseDateToStr(dateFormat, (Date)value));
         } else if (StringUtil.isNotEmpty(readConverterExp) && StringUtil.isNotNull(value)) {
           cell.setCellValue(convertByExp(String.valueOf(value), readConverterExp));
         } else {

           setCellVo(value, attr, cell);
         }
       }
     } catch (Exception e) {
       e.printStackTrace();
     }
     return cell;
   }













   public void setXSSFPrompt(Sheet sheet, String promptTitle, String promptContent, int firstRow, int endRow, int firstCol, int endCol) {
     DataValidationHelper helper = sheet.getDataValidationHelper();
     DataValidationConstraint constraint = helper.createCustomConstraint("DD1");
     CellRangeAddressList regions = new CellRangeAddressList(firstRow, endRow, firstCol, endCol);
     DataValidation dataValidation = helper.createValidation(constraint, regions);
     dataValidation.createPromptBox(promptTitle, promptContent);
     dataValidation.setShowPromptBox(true);
     sheet.addValidationData(dataValidation);
   }












   public void setXSSFValidation(Sheet sheet, String[] textlist, int firstRow, int endRow, int firstCol, int endCol) {
     DataValidationHelper helper = sheet.getDataValidationHelper();

     DataValidationConstraint constraint = helper.createExplicitListConstraint(textlist);

     CellRangeAddressList regions = new CellRangeAddressList(firstRow, endRow, firstCol, endCol);

     DataValidation dataValidation = helper.createValidation(constraint, regions);

     if (dataValidation instanceof org.apache.poi.xssf.usermodel.XSSFDataValidation) {
       dataValidation.setSuppressDropDownArrow(true);
       dataValidation.setShowErrorBox(true);
     } else {
       dataValidation.setSuppressDropDownArrow(false);
     }

     sheet.addValidationData(dataValidation);
   }









   public static String convertByExp(String propertyValue, String converterExp) throws Exception {
     try {
       String[] convertSource = converterExp.split(",");
       for (String item : convertSource) {
         String[] itemArray = item.split("=");
         if (itemArray[0].equals(propertyValue)) {
           return itemArray[1];
         }
       }
     } catch (Exception e) {
       throw e;
     }
     return propertyValue;
   }









   public static String reverseByExp(String propertyValue, String converterExp) throws Exception {
     try {
       String[] convertSource = converterExp.split(",");
       for (String item : convertSource) {
         String[] itemArray = item.split("=");
         if (itemArray[1].equals(propertyValue)) {
           return itemArray[0];
         }
       }
     } catch (Exception e) {
       throw e;
     }
     return propertyValue;
   }




   public String encodingFilename(String filename) {
     filename = UUID.randomUUID().toString() + "_" + filename + ".xlsx";
     return filename;
   }






   public String getAbsoluteFile(String filename) {
     String downloadPath = this.env.getProperty("uploadPath") + filename;
     File desc = new File(downloadPath);
     if (!desc.getParentFile().exists()) {
       desc.getParentFile().mkdirs();
     }
     return downloadPath;
   }










   private Object getTargetValue(T vo, Field field, Excel excel) throws Exception {
     Object o = field.get(vo);
     if (StringUtil.isNotEmpty(excel.targetAttr())) {
       String target = excel.targetAttr();
       if (target.indexOf(".") > -1) {
         String[] targets = target.split("[.]");
         for (String name : targets) {
           o = getValue(o, name);
         }
       } else {
         o = getValue(o, target);
       }
     }
     return o;
   }









   private Object getValue(Object o, String name) throws Exception {
     if (StringUtil.isNotEmpty(name)) {
       Class<?> clazz = o.getClass();
       String methodName = "get" + name.substring(0, 1).toUpperCase() + name.substring(1);
       Method method = clazz.getMethod(methodName, new Class[0]);
       o = method.invoke(o, new Object[0]);
     }
     return o;
   }




   private void createExcelField() {
     this.fields = new ArrayList();
     List<Field> tempFields = new ArrayList<>();
     tempFields.addAll(Arrays.asList(this.clazz.getSuperclass().getDeclaredFields()));
     tempFields.addAll(Arrays.asList(this.clazz.getDeclaredFields()));
     for (Field field : tempFields) {

       if (field.isAnnotationPresent((Class)Excel.class)) {
         putToField(field, field.<Excel>getAnnotation(Excel.class));
       }


       if (field.isAnnotationPresent((Class)Excels.class)) {
         Excels attrs = field.<Excels>getAnnotation(Excels.class);
         Excel[] excels = attrs.value();
         for (Excel excel : excels) {
           putToField(field, excel);
         }
       }
     }
   }




   private void putToField(Field field, Excel attr) {
     if (attr != null && (attr.type() == Excel.Type.ALL || attr.type() == this.type)) {
       this.fields.add(new Object[] { field, attr });
     }
   }




   public void createWorkbook() {
     this.wb = (Workbook)new SXSSFWorkbook(500);
   }







   public void createSheet(double sheetNo, int index) {
     this.sheet = this.wb.createSheet();
     this.styles = createStyles(this.wb);

     if (sheetNo == 0.0D) {
       this.wb.setSheetName(index, this.sheetName);
     } else {
       this.wb.setSheetName(index, this.sheetName + index);
     }
   }








   public Object getCellValue(Row row, int column) {
     if (row == null) {
       return row;
     }
     Object val = "";
     try {
       Cell cell = row.getCell(column);
       if (cell != null) {
         if (cell.getCellTypeEnum() == CellType.NUMERIC || cell.getCellTypeEnum() == CellType.FORMULA) {
           val = Double.valueOf(cell.getNumericCellValue());
           if (HSSFDateUtil.isCellDateFormatted(cell)) {
             val = DateUtil.getJavaDate(((Double)val).doubleValue());
           }
           else if (((Double)val).doubleValue() % 1.0D > 0.0D) {
             val = (new DecimalFormat("0.00")).format(val);
           } else {
             val = (new DecimalFormat("0")).format(val);
           }

         } else if (cell.getCellTypeEnum() == CellType.STRING) {
           val = cell.getStringCellValue();
         } else if (cell.getCellTypeEnum() == CellType.BOOLEAN) {
           val = Boolean.valueOf(cell.getBooleanCellValue());
         } else if (cell.getCellTypeEnum() == CellType.ERROR) {
           val = Byte.valueOf(cell.getErrorCellValue());
         }

       }
     } catch (Exception e) {
       return val;
     }
     return val;
   }
 }
