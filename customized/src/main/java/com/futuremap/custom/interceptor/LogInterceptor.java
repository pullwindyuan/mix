package com.futuremap.custom.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import com.futuremap.custom.entity.Function;
import com.futuremap.custom.entity.Role;
import com.futuremap.custom.entity.RoleFunction;
import com.futuremap.custom.entity.UserRole;
import com.futuremap.custom.exception.UnAuthorizedException;
import com.futuremap.custom.mapper.FunctionMapper;
import com.futuremap.custom.mapper.UserRoleMapper;
import com.futuremap.custom.service.impl.FunctionServiceImpl;
import com.futuremap.custom.service.impl.RoleFunctionServiceImpl;
import com.futuremap.custom.service.impl.RoleServiceImpl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*@Component
@Order(Ordered.LOWEST_PRECEDENCE)*/
public class LogInterceptor implements HandlerInterceptor {
	protected final Logger logger = LoggerFactory.getLogger(LogInterceptor.class);
	
	@Autowired
    UserRoleMapper userRoleRepository;
    
    @Autowired
    RoleServiceImpl roleService;
    
    @Autowired
    FunctionServiceImpl functionService;
    
    @Autowired
    RoleFunctionServiceImpl roleFunctionService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		request.setAttribute("receiveTimestamp", System.currentTimeMillis());
		if (request.getQueryString() != null) {
			logger.info("Receive a request from {} : {} {} {}", request.getRemoteAddr(), request.getMethod(),
					request.getRequestURI(), request.getQueryString());
		} else {
			logger.info("Receive a request from {}: {} {}", request.getRemoteAddr(), request.getMethod(),
					request.getRequestURI());
		}

		// 防止抛出异常后重复制判断

		if (handler instanceof ResourceHttpRequestHandler) {
			logger.info("---------ResourceHttpRequestHandler-------" + handler.toString() + "------------");
		} else if (handler instanceof HandlerMethod) {
			logger.info("--------HandlerMethod--------" + handler.toString() + "------------");
			if ("error".equals(((HandlerMethod) handler).getMethod().getName())) {
				//logger.info("--------HandlerMethod-------- error "+ JSON.toJSONString(handler));
				return true;
			}

			if (!authCheck(handler)) {
				throw new UnAuthorizedException("无权限操作");
			}
		}

		return true;
	}
	
	public Boolean authCheck(Object handler) {
		
		
		String userId = (String) RequestContextHolder.getRequestAttributes().getAttribute("userId", 0);
		String companyId = (String) RequestContextHolder.getRequestAttributes().getAttribute("companyId", 0);

		
		Map<String, String> functionMap = functionService.selectByMethodTypeAndMethodNameIsNotNull("1").stream()
				.collect(Collectors.toMap(Function::getMethodName, e -> e.getId()));
		String methodName = ((HandlerMethod) handler).getMethod().getName(); // 不验证未配置的权限
		logger.info("methodName " + methodName);
		if (functionMap.get(methodName) == null) {
			return true;
		} // 获取用户角色
		List<UserRole> urs = userRoleRepository.selectByUserId(userId);
		// 没有角色
		if(urs.size() == 0) {
			logger.info("校验不通过" + methodName);
			return false;
		}
		
		List<String> ids = urs.stream().map(UserRole::getRoleId).collect(Collectors.toList());
		List<Role> roles = roleService.findByIdIn(ids);
		//admin 角色不校验权限
		int admin = roles.stream().filter(e -> "admin".equals(e.getRoleName()))
				.collect(Collectors.toList()).size();
		if (admin > 0) {
			return true;
		}
		//获取角色拥有权限
		List<RoleFunction> rolefunction = roleFunctionService.selectByRoleIdIn(ids);
		if(rolefunction.size() == 0) {
			logger.info("校验不通过" + methodName);
			return false;
		}

		List<Function> functions = functionService.selectByIdInAndMethodTypeAndMethodNameIsNotNull(
				rolefunction.stream().map(RoleFunction::getFunctionId).collect(Collectors.toList()), "1");
		Map<String, String> funcMap = functions.stream()
				.collect(Collectors.toMap(Function::getMethodName, e -> e.getId()));
		if (funcMap.get(methodName) == null) {
			logger.info("校验不通过" + methodName);
			return false;
		} else {
			logger.info("校验通过");
			return true;
		}
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		logger.info("Request completed in {} ms, status {}",
				System.currentTimeMillis() - (Long) request.getAttribute("receiveTimestamp"), response.getStatus());
	}
}
