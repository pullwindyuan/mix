package com.futuremap.erp.module.auth.controller;


import com.futuremap.erp.common.exception.FuturemapBaseException;
import com.futuremap.erp.module.auth.dto.ColumnDto;
import com.futuremap.erp.module.auth.dto.UserDto;
import com.futuremap.erp.module.auth.entity.Column;
import com.futuremap.erp.module.auth.entity.Resource;
import com.futuremap.erp.module.auth.entity.User;
import com.futuremap.erp.module.auth.service.impl.ColumnServiceImpl;
import com.futuremap.erp.module.auth.service.impl.ResourceServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 功能表 前端控制器
 * </p>
 *
 * @author futuremap
 * @since 2021-06-22
 */
@RestController
@RequestMapping("/auth/column")
@Api(tags = "<角色与权限>：获取数据列控制信息")
public class ColumnController {
    @Autowired
    private ColumnServiceImpl columnService;

    @PostMapping("/list")
    @ApiOperation("获取数据列控制信息列表")
    public List<ColumnDto> findList(@RequestBody @Valid Column column) {
        return columnService.findList(column);
    }
}
