package com.run.concurrency;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.run.concurrency.example.threadlocal.RequestHolder;

/**
 * @author user
 * 通过继承HandlerInterceptorAdapter 实现拦截器功能
 */
public class HttpInterceptor extends HandlerInterceptorAdapter{
	
	private static Logger logger = LoggerFactory.getLogger(HttpFilter.class);

	/* (non-Javadoc)
	 * 方法执行后调用，即使没有正确执行也调用
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		RequestHolder.remove();
		logger.info("afterCompletion");
		return;
	}

	/* (non-Javadoc)
	 * 方法执行前调用
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		logger.info("preHandle");
		return true;
	}

	
	
}
