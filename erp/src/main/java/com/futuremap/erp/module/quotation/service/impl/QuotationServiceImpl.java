package com.futuremap.erp.module.quotation.service.impl;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.futuremap.erp.common.exception.FuturemapBaseException;
import com.futuremap.erp.module.comp.entity.CompanyInfo;
import com.futuremap.erp.module.comp.service.impl.CompanyInfoServiceImpl;
import com.futuremap.erp.module.constants.Constants;
import com.futuremap.erp.module.constants.OrderProcessConstants;
import com.futuremap.erp.module.orderCost.mapper.OrderCostDS2Mapper;
import com.futuremap.erp.module.quotation.entity.Quotation;
import com.futuremap.erp.module.quotation.mapper.QuotationMapper;
import com.futuremap.erp.module.quotation.service.IQuotationService;
import com.futuremap.erp.utils.BeanCopierUtils;
import com.futuremap.erp.utils.EasyPoiExcelUtil;
import com.futuremap.erp.utils.GeneralUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author futuremap
 * @since 2021-06-10
 */
@Service
@Slf4j
public class QuotationServiceImpl extends ServiceImpl<QuotationMapper, Quotation> implements IQuotationService {
    @Autowired
    QuotationMapper quotationMapper;
    @Autowired
    OrderCostDS2Mapper orderCostDS2Mapper;
    @Autowired
    CompanyInfoServiceImpl companyInfoService;

    @Override
    public List<Quotation> exportQuotations(InputStream fileInputStream) {
        List<Quotation> resultList = new ArrayList<>();
        try {
            ByteArrayOutputStream baos = cloneInputStream(fileInputStream);
            InputStream stream1 = new ByteArrayInputStream(baos.toByteArray());
            InputStream stream2 = new ByteArrayInputStream(baos.toByteArray());
            ExcelReader reader = ExcelUtil.getReader(stream1);
            //获取第四行第三列单元格内容
            //提点
            Object commissionPercent = reader.readCellValue(4, 1);
            //业务员
            Object salemanNames = reader.readCellValue(2, 1);
            //订单号
            Object saleOrder = reader.readCellValue(0, 1);
            //校验
            if (commissionPercent == null || salemanNames == null || saleOrder == null) {
                throw new FuturemapBaseException("上传数据不完整");
            }


            //导入其他数据
            List<Quotation> quotations = EasyPoiExcelUtil.importExcel(stream2, 3, 1, Quotation.class);

            List<CompanyInfo> companyInfoList = companyInfoService.list();

            for (Quotation quotation : quotations) {
                if (quotation.getCinvcode() != null) {
                    quotation.setCommissionPercent(new BigDecimal(String.valueOf(commissionPercent)));
                    quotation.setCsocode(String.valueOf(saleOrder));
                    quotation.setSalemanName(String.valueOf(salemanNames));

                    String csocode = quotation.getCsocode();
                    String cinvcode = quotation.getCinvcode();
                    BigDecimal fquantity = GeneralUtils.getBigDecimalVal(quotation.getFquantity());
                    BigDecimal exchangeRate = GeneralUtils.getBigDecimalVal(quotation.getExchangeRate());
                    BigDecimal unitPrice = GeneralUtils.getBigDecimalVal(quotation.getUnitPrice());
                    unitPrice = unitPrice.multiply(exchangeRate);
                    quotation.setUnitPrice(unitPrice);


                    BigDecimal materialCost = GeneralUtils.getBigDecimalVal(quotation.getMaterialCost());
                    materialCost = materialCost.multiply(fquantity);
                    quotation.setMaterialCost(materialCost);

                    BigDecimal laborCost = GeneralUtils.getBigDecimalVal(quotation.getLaborCost());
                    laborCost = laborCost.multiply(fquantity);
                    quotation.setLaborCost(laborCost);

                    BigDecimal manufactureCost = GeneralUtils.getBigDecimalVal(quotation.getManufactureCost());
                    manufactureCost = manufactureCost.multiply(fquantity);
                    quotation.setManufactureCost(manufactureCost);

                    BigDecimal exportingCost = GeneralUtils.getBigDecimalVal(quotation.getExportingCost());
                    exportingCost = exportingCost.multiply(fquantity);
                    quotation.setExportingCost(exportingCost);

                    BigDecimal mgrCost = GeneralUtils.getBigDecimalVal(quotation.getMgrCost());
                    mgrCost = mgrCost.multiply(fquantity);
                    quotation.setMgrCost(mgrCost);
                    for (CompanyInfo comp : companyInfoList) {
                        List<Map<String, Object>> mapList = new ArrayList<>();
                        String ds = comp.getDatasource();
                        String companyCode = comp.getCompanyCode();
                        String companyName = comp.getCompanyName();
                        DynamicDataSourceContextHolder.push(ds);
                        //List<Map<String, Object>> ons1 = orderCostDS2Mapper.getOuterSaleOrderNumber(csocode, cinvcode);
                        List<Map<String, Object>> ons1 = orderCostDS2Mapper.getOuterSaleOrderNumber0001(csocode, cinvcode);
                        if (ons1 != null && ons1.size() > 0) {
                            mapList.addAll(ons1);
                        }
                        //List<Map<String, Object>> ons2 = orderCostDS2Mapper.getInnerSaleOrderNumber(csocode, cinvcode);
                        List<Map<String, Object>> ons2 = orderCostDS2Mapper.getInnerSaleOrderNumber0001(csocode, cinvcode);
                        if (ons2 != null && ons2.size() > 0) {
                            mapList.addAll(ons2);
                        }
                        if (mapList.size() > 0) {

                            for (Map<String, Object> map : mapList) {
                                Quotation quotation1 = new Quotation();
                                BeanCopierUtils.copyProperties(quotation, quotation1);
                                String irowno = String.valueOf(map.get("irowno"));
                                String cinvname = String.valueOf(map.get("cinvname"));
                                String autoid = String.valueOf(map.get("autoid"));
                                String ddate = String.valueOf(map.get("ddate")).substring(0, 10) + " 00:00:00";
                                LocalDateTime ldt = LocalDateTime.parse(ddate, Constants.time_format);
                                quotation1.setIrowno(Integer.valueOf(irowno));
                                quotation1.setCompanyCode(companyCode);
                                quotation1.setCompanyName(companyName);
                                quotation1.setAutoid(autoid);
                                quotation1.setDdate(ldt);
                                quotation1.setCinvname(cinvname);
                                DynamicDataSourceContextHolder.push(OrderProcessConstants.MASTER);

                                quotationMapper.delOld(csocode, irowno, cinvcode, companyCode, ddate);

                                resultList.add(quotation1);
                            }
                        }

                    }
                }
            }
            DynamicDataSourceContextHolder.push(OrderProcessConstants.MASTER);
            saveBatch(resultList);


        } catch (Exception e) {
            e.printStackTrace();
            log.error("导入报价表失败", e);
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return resultList;
    }

    private static ByteArrayOutputStream cloneInputStream(InputStream input) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len;
            while ((len = input.read(buffer)) > -1) {
                baos.write(buffer, 0, len);
            }
            baos.flush();
            return baos;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
