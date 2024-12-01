package com.hmall.common.interceptors;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.hmall.common.utils.UserContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * @author promise
 * @date 2024/6/21 - 22:19
 */
public class UserInfoInterceptor implements HandlerInterceptor {

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {
    String userInfo = request.getHeader("user-info");

    if (StringUtils.isNotBlank(userInfo)) {
      UserContext.setUser(Long.valueOf(userInfo));
    }

    return true;
  }

  @Override
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
      Object handler, Exception ex) throws Exception {
    UserContext.removeUser();
  }
}
