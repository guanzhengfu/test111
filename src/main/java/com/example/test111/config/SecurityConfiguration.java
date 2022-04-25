package com.example.test111.config;

import com.example.test111.component.AuthFilterCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

/**
 * <pre>
 *      Spring Security配置类
 * </pre>
 *
 * <pre>
 * @author mazq
 * 修改记录
 *    修改后版本:     修改人：  修改日期: 2020/06/15 10:39  修改内容:
 * </pre>
 */
@Configuration
@EnableWebSecurity
@Order(1)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

  @Autowired
  private AuthFilterCustom authFilterCustom;

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

//  @Override
//  protected void configure(AuthenticationManagerBuilder auth) throws Exception {    //auth.inMemoryAuthentication()
//    auth.inMemoryAuthentication()
//        .withUser("nicky")
//        .password("{noop}123")
//        .roles("admin");
//  }

  @Override
  public void configure(WebSecurity web) throws Exception {
    //解决静态资源被拦截的问题
    web.ignoring().antMatchers("/asserts/**");
    web.ignoring().antMatchers("/favicon.ico");
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
//    http.csrf()
//        .disable()
//        .authorizeRequests()
//        .mvcMatchers(HttpMethod.GET, "/test1/2").hasRole("USER")
//        .antMatchers("/test1/2").authenticated()//所有的请求必须认证通过
//        .anyRequest().permitAll()//其它所有请求都可以随意访问
//        .and()
//        .addFilterAfter(authFilterCustom, BasicAuthenticationFilter.class)//添加过滤器
//        .sessionManagement()
//        .sessionCreationPolicy(SessionCreationPolicy.STATELESS);//禁用session
    http.authorizeRequests().mvcMatchers(HttpMethod.GET, "/test1/2").hasRole("USER")
        .antMatchers("/test1/2").authenticated()//所有的请求必须认证通过
        .anyRequest().permitAll()
        .and().cors();
  }

}
