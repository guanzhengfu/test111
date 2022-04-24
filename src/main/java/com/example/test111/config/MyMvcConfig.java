package com.example.test111.config;

import com.example.test111.interceptor.signinHandlerIntercepter;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
//@EnableWebMvc
public class MyMvcConfig implements WebMvcConfigurer {

  @Override
  //通过此处加载拦截器
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(new signinHandlerIntercepter())
        .addPathPatterns("/**")
        .excludePathPatterns("/signin",  "/assets/**", "/error/**", "/signup",
            "/register");
  }
}
