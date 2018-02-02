package com.zyserver.core.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.zyserver.interceptor.NeedSignInInterceptor;
import com.zyserver.interceptor.TokenInterceptor;

@Configuration
public class InterceptorConfig extends WebMvcConfigurerAdapter {
	@Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new TokenInterceptor()).addPathPatterns("/**");
        registry.addInterceptor(new NeedSignInInterceptor()).addPathPatterns("/**");
        super.addInterceptors(registry);
    }
}
