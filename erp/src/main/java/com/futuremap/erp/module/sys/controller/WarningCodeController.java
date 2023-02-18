package com.futuremap.erp.module.sys.controller;

import cn.hutool.json.JSONObject;
import com.futuremap.erp.common.exception.FuturemapBaseException;
import com.futuremap.erp.common.warning.WarningBean;
import com.futuremap.erp.common.warning.WarningEnum;
import com.futuremap.erp.module.saleorder.entity.SaleOrder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/warning")
@Slf4j
@Api(tags = {"获取警示信息"})
public class WarningCodeController {
    @PostMapping("/list")
    @ApiOperation("获取警示信息定义列表数据，不分页")
    public List<WarningBean> getCodeList() {
        WarningEnum[] warningEnums = WarningEnum.values();
        List<WarningBean> list = new ArrayList<>();
        for (WarningEnum w :warningEnums) {
            list.add(w.getWarningBean());
        }
        return list;
    }
}
