package com.futuremap.custom.service.impl;

import com.futuremap.custom.dto.resource.ResourceObjCreate;
import com.futuremap.custom.entity.ResourceRef;
import com.futuremap.custom.mapper.ResourceRefMapper;
import com.futuremap.custom.service.ResourceRefService;
import com.futuremap.custom.service.ResourceTemplateService;
import com.futuremap.custom.util.UserExecutors;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author futuremap
 * @since 2021-08-20
 */
@Service
public class ResourceRefServiceImpl extends ServiceImpl<ResourceRefMapper, ResourceRef> implements ResourceRefService {

	@Autowired
	ResourceTemplateService templateService;
	
	
	private List<ResourceRef> list = new CopyOnWriteArrayList<>();

	public void record(ResourceObjCreate resourceObjCreate) {
		// 多线程初始化
		ExecutorService pool = UserExecutors.getThreadPool("resourceRef");
		RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
		RequestContextHolder.setRequestAttributes(requestAttributes, true);//子线程继承主线程的上下文
		// 线程1
		pool.execute(() -> {
			list.addAll(resource2Ref(resourceObjCreate));
			if (list.size() > 0) {
				//this.saveBatch(list);
				templateService.addRefCount(list);
				list.clear();
			}
		});
	}

	private List<ResourceRef> resource2Ref(ResourceObjCreate create) {
		String[] templateIds = create.getTemplateId().split(",");
		List<ResourceRef> refs = new ArrayList<>();
		for (int i = 0; i < templateIds.length; i++) {
			ResourceRef ref = new ResourceRef();
			ref.setCreatedBy(create.getCreatedBy());
			ref.setUpdatedBy(create.getCreatedBy());
			ref.setExternalId(create.getId());
			ref.setTemplateId(templateIds[i]);
			ref.setType(create.getType());
			refs.add(ref);
		}
		return refs;
	}

}
