package com.futuremap.erp.module.comp.controller;


import com.futuremap.erp.common.exception.FuturemapBaseException;
import com.futuremap.erp.module.comp.entity.CompanyInfo;
import com.futuremap.erp.module.comp.service.impl.CompanyInfoServiceImpl;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author futuremap
 * @since 2021-05-28
 */
@RestController
@RequestMapping("/comp/companyInfo")
@Slf4j
public class CompanyInfoController {

    @Autowired
    CompanyInfoServiceImpl companyInfoService;

    @PostMapping("/list")
    @ApiOperation("获取公司列表")
    public List<CompanyInfo> list(@RequestBody CompanyInfo CompanyInfo) {
        try {
            return companyInfoService.list();
        } catch (Exception e) {
            log.error("获取公司列表数据失败", e);
            throw new FuturemapBaseException("获取公司列表数据失败");
        }
    }

}
