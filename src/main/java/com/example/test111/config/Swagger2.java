package com.example.test111.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@Configuration
@EnableSwagger2
@EnableSwaggerBootstrapUI
public class Swagger2 {
  //    http://localhost:8088/swagger-ui.html     原路径（swagger自带的ui）
  //    http://localhost:8088/doc.html     原路径


  // 配置swagger2核心配置 docket
  @Bean
  public Docket createRestApi(){
    return  new Docket(DocumentationType.SWAGGER_2)//制定api类型为swagger2类型
        .apiInfo(apiInfo()) //定义api文档汇总信息
        .select().apis(RequestHandlerSelectors.basePackage("com.example.test111.controller"))//扫描哪个包下面 //RequestHandlerSelectors.withClassAnnotation(Api.class)存在注解的都都会拿取到
        .paths(PathSelectors.any())//所有接口
        .build();
  }


  private ApiInfo apiInfo() {


    return new ApiInfoBuilder()
        .title("企业侧边栏接口平台Api")  //标题
        .contact(new Contact("reptilian","https//www.mantis.com","mantis@qq.com"))//联系方式
        .description("企业侧边栏接口文档")//描述
        .version("0.0.1")//版本
        .termsOfServiceUrl("https//www.mantis.com")//网址
        .build();


  }


}