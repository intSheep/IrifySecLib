package org.example.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/druid/**").permitAll()  // 允许访问Druid监控页面
                .antMatchers("/swagger-ui/**", "/swagger-resources/**", "/v3/api-docs/**").permitAll()  // 允许访问Swagger
                .anyRequest().permitAll()  // 允许访问其他所有路径
                .and()
                .csrf().disable();  // 禁用CSRF保护
    }
} 