package com.futuremap.erp.module.operation.service;

import com.futuremap.erp.module.operation.entity.OperatingStatement;
import com.baomidou.mybatisplus.extension.service.IService;

import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author futuremap
 * @since 2021-06-01
 */
public interface IOperatingStatementService extends IService<OperatingStatement> {

    List<OperatingStatement> countCompany(LocalDate currentDate);
}
