package com.lpf.www.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @auther 飞
 * @create
 */
@Configuration   // 相当于Spring配置中的<beans>
public class Swagger3Config {
    @Bean
    public OpenAPI docsOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("员工管理接口")
                        .description("使用springboot+vue-element_admin开发")
                        .version("v0.0.1")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")))
                .externalDocs(new ExternalDocumentation()
                        .description("哈哈 Wiki Documentation")
                        .url("https://springshop.wiki.github.org/docs"));
    }

}
