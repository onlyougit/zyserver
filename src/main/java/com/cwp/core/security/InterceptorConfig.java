package com.cwp.core.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.cwp.interceptor.NeedSignInInterceptor;
import com.cwp.interceptor.TokenInterceptor;

@Configuration
public class InterceptorConfig extends WebMvcConfigurerAdapter {
	@Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new TokenInterceptor()).addPathPatterns("/**");
        registry.addInterceptor(new NeedSignInInterceptor()).addPathPatterns("/**");
        super.addInterceptors(registry);
    }
}
