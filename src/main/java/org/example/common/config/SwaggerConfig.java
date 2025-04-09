package org.example.common.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @description Swagger Config配置文件
 * @author: zpy
 * @Date: 2025/4/6 20:42
 */
@EnableOpenApi
@ComponentScan(basePackages = {"org.example"})
@Configuration
public class SwaggerConfig {
    @Autowired
    private SwaggerProperties swaggerProperties;

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.OAS_30)
                .pathMapping("/")  // 所有API路径的基础路径
                .enable(swaggerProperties.getEnable()) // 通过配置动态启用/禁用
                .apiInfo(apiInfo()) // 文档元数据
                .select()
                .apis(RequestHandlerSelectors.basePackage("org.example")) // 包扫描规则
                .paths(PathSelectors.any()) // 路径过滤规则
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(swaggerProperties.getTitle())//标题
                .description(swaggerProperties.getDescription())//描述
                .contact(new Contact(swaggerProperties.getAuthor(), swaggerProperties.getUrl(), swaggerProperties.getEmail()))//作者信息
                .version(swaggerProperties.getVersion())//版本号
                .build();
    }

}
