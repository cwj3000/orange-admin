package com.orange.demo.common.datafilter.config;

import com.orange.demo.common.datafilter.interceptor.DataFilterInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 添加数据过滤相关的拦截器。
 *
 * @author Jerry
 * @date 2020-09-24
 */
@Configuration
public class DataFilterWebMvcConfigurer implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new DataFilterInterceptor()).addPathPatterns("/**");
    }
}
