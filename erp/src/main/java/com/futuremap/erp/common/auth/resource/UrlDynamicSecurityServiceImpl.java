package com.futuremap.erp.common.auth.resource;

import com.futuremap.erp.module.auth.entity.Resource;
import com.futuremap.erp.module.auth.service.impl.ResourceServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 调试的时候需要注释掉@Component，登录鉴权无法通过
 */
//@Component
public class UrlDynamicSecurityServiceImpl implements DynamicSecurityService {

    @Autowired
    private ResourceServiceImpl resourceService;
    @Override
    public Map<String, ConfigAttribute> loadDataSource() {
        Map<String, ConfigAttribute> map = new ConcurrentHashMap<>();
        List<Resource> resourceList = resourceService.list();
        for (Resource resource : resourceList) {
            //存放url与resourceId 映射 MetadataSource中会用到
            map.put(resource.getUrl(), new SecurityConfig(String.valueOf(resource.getId())));
        }
        return map;
    }
}
