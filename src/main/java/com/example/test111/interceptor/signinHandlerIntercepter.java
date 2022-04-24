package com.example.test111.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

public class signinHandlerIntercepter implements HandlerInterceptor {
  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

    //获取token，从这个拦截器判断权限以及单点登录
    String Authname =request.getHeader("Authorization");
    if(Authname == null){
      request.getRequestDispatcher("/oauth/token").forward(request, response);
      System.out.println("过滤器没有放行");
      return false;
    }
    else {
      System.out.println("过滤器放行了");
      return true;
    }
  }
}