package com.futuremap.erp.module.quotation.service;

import com.futuremap.erp.module.quotation.entity.Quotation;
import com.baomidou.mybatisplus.extension.service.IService;

import java.io.InputStream;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author futuremap
 * @since 2021-06-10
 */
public interface IQuotationService extends IService<Quotation> {

    List<Quotation> exportQuotations(InputStream fileInputStream);
}
