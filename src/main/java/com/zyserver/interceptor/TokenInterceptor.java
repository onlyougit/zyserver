package com.zyserver.interceptor;

import java.util.TimeZone;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import com.zyserver.common.Constant;
import com.zyserver.entity.Customer;
import com.zyserver.frontservice.service.impl.TokenService;

public class TokenInterceptor extends HandlerInterceptorAdapter {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		WebApplicationContext applicationContext=WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());
		TokenService tokenService = applicationContext.getBean("tokenService", TokenService.class);
		String token = request.getParameter(Constant.PARAM_TOKEN);
		if (token != null) {
			TimeZone timeZone = LocaleContextHolder.getTimeZone();
			Customer user = tokenService.getCustomerByToken(token, timeZone);
			if (user != null) {
				request.setAttribute(Constant.CURRENT_USER, user);
			}
		}
		return super.preHandle(request, response, handler);
	}
}