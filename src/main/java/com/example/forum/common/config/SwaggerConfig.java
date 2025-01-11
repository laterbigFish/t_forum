package com.example.forum.common.config;
// 搞不懂就先不写了   不影响后续功能的实现    这个功能这只是便于我们访问摸个url

//import org.springframework.boot.actuate.autoconfigure.endpoint.web.CorsEndpointProperties;
//import org.springframework.boot.actuate.autoconfigure.endpoint.web.WebEndpointProperties;
//import org.springframework.boot.actuate.autoconfigure.web.server.ManagementPortType;
//import org.springframework.boot.actuate.endpoint.ExposableEndpoint;
//import org.springframework.boot.actuate.endpoint.web.*;
//import org.springframework.boot.actuate.endpoint.web.annotation.ControllerEndpointsSupplier;
//import org.springframework.boot.actuate.endpoint.web.annotation.ServletEndpointsSupplier;
//import org.springframework.boot.actuate.endpoint.web.servlet.WebMvcEndpointHandlerMapping;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.env.Environment;
//import org.springframework.util.StringUtils;
//import springfox.documentation.builders.ApiInfoBuilder;
//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.oas.annotations.EnableOpenApi;
//import springfox.documentation.service.ApiInfo;
//import springfox.documentation.service.Contact;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;
//
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.List;
///**
// * Swagger配置类
// *
// * @Author ⽐特就业课
// */
//// 配置类
//@Configuration
//// 开启Springfox-Swagger
//@EnableOpenApi
//public class SwaggerConfig {
//    /**
//     * Springfox-Swagger基本配置
//     * @return
//     */
//    @Bean
//    public Docket createApi() {
//        //扫描路径
//        return new Docket(DocumentationType.OAS_30)
//                .apiInfo(apiInfo())
//                .select()
//                .apis(RequestHandlerSelectors.basePackage("com.example.forum.controller")) //扫描路径
//                .paths(PathSelectors.any())
//                .build();
//    }
//    // 配置API基本信息
//    private ApiInfo apiInfo() {
//        ApiInfo apiInfo = new ApiInfoBuilder()
//                .title("论坛系统API")
//                .description("论坛系统前后端分离API测试")
//                .contact(new Contact("Tech",
//                        "https://edu.jiuyeke.com", "3347219161@qq.com"))
//                .version("1.0")
//                .build();
//        return apiInfo;
//    }
//    /**
//     * 解决SpringBoot 6.0以上与Swagger 3.0.0 不兼容的问题
//     * 复制即可
//     **/
//    @Bean
//    public WebMvcEndpointHandlerMapping
//    webEndpointServletHandlerMapping( WebEndpointsSupplier webEndpointsSupplier,
//                                      ServletEndpointsSupplier servletEndpointsSupplier,
//                                      ControllerEndpointsSupplier controllerEndpointsSupplier,
//                                      EndpointMediaTypes endpointMediaTypes, CorsEndpointProperties corsProperties,
//                                      WebEndpointProperties webEndpointProperties, Environment environment) {
//        List<ExposableEndpoint<?>> allEndpoints = new ArrayList();
//        Collection<ExposableWebEndpoint> webEndpoints =
//                webEndpointsSupplier.getEndpoints();
//        allEndpoints.addAll(webEndpoints);
//        allEndpoints.addAll(servletEndpointsSupplier.getEndpoints());
//        allEndpoints.addAll(controllerEndpointsSupplier.getEndpoints());
//        String basePath = webEndpointProperties.getBasePath();
//        EndpointMapping endpointMapping = new EndpointMapping(basePath);
//        boolean shouldRegisterLinksMapping =
//                this.shouldRegisterLinksMapping(webEndpointProperties, environment,
//                        basePath);
//        return new WebMvcEndpointHandlerMapping(endpointMapping, webEndpoints,
//                endpointMediaTypes,
//                corsProperties.toCorsConfiguration(), new
//                EndpointLinksResolver(allEndpoints, basePath),
//                shouldRegisterLinksMapping, null);
//    }
//    private boolean shouldRegisterLinksMapping(WebEndpointProperties
//                                                       webEndpointProperties, Environment environment,
//                                               String basePath) {
//        return webEndpointProperties.getDiscovery().isEnabled() &&
//                (StringUtils.hasText(basePath)
//                        ||
//                        ManagementPortType.get(environment).equals(ManagementPortType.DIFFERENT));
//    }
//}