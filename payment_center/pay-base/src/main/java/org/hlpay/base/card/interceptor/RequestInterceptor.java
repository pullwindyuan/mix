package org.hlpay.base.card.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hlpay.common.util.MyLog;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class RequestInterceptor
  implements HandlerInterceptor
{
  private static final MyLog _log = MyLog.getLog(RequestInterceptor.class);




  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    _log.info("拦截器启动{}", new Object[] { "......" });
    return true;
  }




  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    _log.info("postHandle{}", new Object[] { "......" });
  }





  public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    _log.info("afterCompletion{}", new Object[] { "......" });
  }
}

