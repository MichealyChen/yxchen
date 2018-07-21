package com.jt.web.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.common.util.CookieUtils;
import com.jt.common.vo.SysResult;
import com.jt.web.pojo.User;
import com.jt.web.service.UserService;

import redis.clients.jedis.JedisCluster;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private JedisCluster jedisCluster;

	///user/	login/register.html
	@RequestMapping("/{module}")
	public String index(@PathVariable String module){
		
		return module;
	}
	
	//用户注册http://www.jt.com/service/user/doRegister
	@RequestMapping("/doRegister")
	@ResponseBody
	public SysResult saveUser(User user){
		try {
			String username = userService.saveUser(user);
			if(username !=null){
				return SysResult.oK(username);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201,"用户注册失败");
	}
	
	//用户登陆操作
	//http://www.jt.com/service/user/doLogin?r=0.7282205107984818
	@RequestMapping("/doLogin")
	@ResponseBody
	public SysResult doLogin(User user,HttpServletRequest request,HttpServletResponse response){
		try {
			//调用程序获取加密后的秘钥
			String ticket = userService.findUserByUP(user);
			//判断ticket是否为null
			if(!StringUtils.isEmpty(ticket)){
				//将ticket保存到cookie中
				CookieUtils.setCookie(request, response,"JT_TICKET", ticket);
				//将数据正确返回
				return SysResult.oK(ticket);
			}	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201, "登陆失败");
	}
	
	/**
	 * 1.先获取cookie中的ticket
	 * 2.将redis中的数据删除
	 * 3.将cookie中的数据删除
	 * 4.页面重定向到系统首页
	 * @return
	 */
	@RequestMapping("/logout")
	public String logout(HttpServletRequest request,HttpServletResponse response){
		String ticket = 
				CookieUtils.getCookieValue(request,"JT_TICKET");
		jedisCluster.del(ticket);
		CookieUtils.deleteCookie(request, response, "JT_TICKET");
		return "redirect:/index.html";//重定向页面
		
		/**
		 * 关于redis和cookie中的超时时间问题
		 * 1.redis超时
		 * @param key   存入redis的key值
		 * @param value 存入redis的value值
		 * @param nxxx NX|XX, 一般写NX 
		 * 		NX 代表redis中没有改key,则新增key后设定超时时间 
				XX 代表redis中已经存在该key,为这个key设定超时时间
		 * @param expx EX|PX, 
		 * expire time units: EX = seconds; 
		 * PX = milliseconds
		 * jedisCluster.set(key, value, nxxx, expx, time);
		CookieUtils.setCookie(request, response, cookieName, cookieValue, cookieMaxage);
		 */
		
	}
	
	
	
	
	
	
	
	
	
}
