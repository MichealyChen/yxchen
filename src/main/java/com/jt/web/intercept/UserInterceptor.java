package com.jt.web.intercept;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.util.CookieUtils;
import com.jt.web.pojo.User;
import com.jt.web.thread.UserThreadLocal;

import redis.clients.jedis.JedisCluster;

public class UserInterceptor implements HandlerInterceptor{
	
	@Autowired
	private JedisCluster jedisCluster;
	private static ObjectMapper objectMapper = new ObjectMapper();
	
	//表示处理器执行之前
	/**
	 * 1.检测Cookie中是否有值 ticket
	 * 2.检测redis中是否含有ticket
	 * 3.如果用户已经存在则将用户信息进行包装,方便购物车获取用户
	 * 4.如果redis和cookie中没有ticket则转向用户登陆页面
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//通过拦截器实现用户信息的拦截
		String ticket = CookieUtils.getCookieValue(request, "JT_TICKET");
		if(!StringUtils.isEmpty(ticket)){
			String userJSON = jedisCluster.get(ticket);
			if(!StringUtils.isEmpty(userJSON)){
				User user = 
						objectMapper.readValue(userJSON,User.class);
				//如何将user数据封装 方便购物车获取???
				//request.setAttribute("user", user);
				//session不可以 
				UserThreadLocal.set(user);
				return true;  //拦截器放行
			}
		}
		//如果程序执行到这里表示用户没有登陆则跳转到登陆页面
		response.sendRedirect("/user/login.html");
		return false;
	}
	
	//处理器执行之后
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	//请求完成之后
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		//程序执行 表示请求完成 线程的工作完成 将ThreadLocal移除
		UserThreadLocal.remove();
	}
}
