package org.example.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @description Swagger配置项
 * @author: zpy
 * @Date: 2025/4/6 21:18
 */
@Data
@Component
@ConfigurationProperties(value = "swagger")
public class SwaggerProperties {

    /**
     * 是否开启swagger，生产环境一般关闭，所以这里定义一个变量
     */
    private Boolean enable = true;

    /**
     * 项目信息
     */
    private String title = "";

    /**
     * 描述信息
     */
    private String description = "";

    /**
     * 版本信息
     */
    private String version = "";

    /**
     * 作者
     */
    private String author = "";

    /**
     * url
     */
    private String url = "";

    /**
     * email
     */
    private String email = "";

}