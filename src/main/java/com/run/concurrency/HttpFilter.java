package com.run.concurrency;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.run.concurrency.example.threadlocal.RequestHolder;

/**
 * @author user
 * 本例中通过filter拦截后，在ThreadLocal中添加了对应的ThreadLocal对象
 * Filter拦截器
 */
public class HttpFilter implements Filter{
	
	private static Logger logger = LoggerFactory.getLogger(HttpFilter.class);

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		//一般情况下我们要将ServletRequest的request转换为HttpServletRequest的request
		//通过转换为HttpServletRequest，可以获取到session中的值
		HttpServletRequest request = (HttpServletRequest) servletRequest;
//		request.getSession().getAttribute("user");
		
		logger.info("线程id:"+Thread.currentThread().getId()+"   事务当前的请求："+request.getServletPath()); //request.getServletPath()输出请求路径，去除项目名称的，本例中为/threadLocal/test
		RequestHolder.add(Thread.currentThread().getId());//将当前thread的id储存到ThreadLocal中，此时key 和 value都是线程id
		filterChain.doFilter(servletRequest, servletResponse);//让通过该filter继续处理
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}

}
