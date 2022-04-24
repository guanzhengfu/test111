/*
package com.example.test111.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import gsa.base.datasources.Redisconfig.RedisTemplete;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;


*/
/**
 * 拦截器，如果return null 正常访问各个微服务的接口
 * 如果被拦截器拦截将会返回处理的信息，也将不会访问日志记录
 * 可以根据网关拦截➕redis做单点登录
 *//*

@Component
public class PreZuulFilter extends ZuulFilter {
  private static final Logger log = LoggerFactory.getLogger(ZuulFilter.class);
  @Bean
  private RedisTemplete redisTemplete(){
    return new RedisTemplete();
  }
  @Autowired
  private RedisTemplete redisTemplete;

  @Override
  public Object run() throws ZuulException {
    RequestContext ctx = RequestContext.getCurrentContext();
    HttpServletRequest request = ctx.getRequest();
    String url = request.getRequestURL().toString();
    //获取认证名称
    String Authname =request.getHeader("Authorization");
    String token=null;
    if(Authname!=null&&!Authname.equals("")){
      //用户请求时会在头部 Authorization 传给我之前存储的token, 我用来验证
      Authname= Authname.replace("Bearer ","");
      //获取redis存储的token
      if (redisTemplete.exists(Authname)){
        //查询redis是否有token
        token= (String) redisTemplete.get(Authname);
      }
    }
    //此处判断是否要拦截**************
    //过滤登录方法
    if(url.contains("/login/loginValidate")){
      return null;
    }
    //过滤datacenter微服务
    if(url.contains("/gsa/rest/")){
      if(!url.contains("/MenuSystemTree")){
        return null;
      }
    }
    //过滤es微服务
    if(url.contains("/gsa/tool/")) {
      return null;
//            if (redisTemplete.exists("INTERFACE_FILTER_ES")) {
//                if (redisTemplete.get("INTERFACE_FILTER_ES").equals("FALSE")) {
//                }
//            }
    }
    //*******************开始拦截****************************
    log.info(String.format("%s  拦截的url: %s",request.getMethod(),url));
    //没有加认证token 就没有访问权限
    if(StringUtils.isBlank(Authname)){
      ctx.setSendZuulResponse(false);
      ctx.setResponseStatusCode(401);
      ctx.setResponseBody("{\"code\":401,\"msg\":\"没有访问权限！\"}");
      ctx.getResponse().setContentType("text/html;charset=UTF-8");
    }else if(token==null){
      //token失效了
      //用户提供的token检测出和redis不一样
      ctx.setSendZuulResponse(false);
      ctx.setResponseStatusCode(401);
      ctx.setResponseBody("{\"code\":401,\"msg\":\"令牌失效,请重新登录！\"}");
      ctx.getResponse().setContentType("text/html;charset=UTF-8");
    }
    //*******************结束拦截****************************
    //ctx.addZuulRequestHeader("username", username);
    return null;
  }
  @Override
  public String filterType() {
    return "pre";
  }

  @Override
  public int filterOrder() {
    return 0;
  }

  @Override
  public boolean shouldFilter() {
    return true;
  }
}*/
