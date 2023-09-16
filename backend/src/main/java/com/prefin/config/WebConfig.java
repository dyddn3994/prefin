package com.prefin.config;

import com.prefin.component.TransactionInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public TransactionInterceptor customTransactionInterceptor() {
        return new TransactionInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(customTransactionInterceptor())
                .addPathPatterns("/**"); // 모든 URL에 대해 인터셉트하도록 설정
    }
}