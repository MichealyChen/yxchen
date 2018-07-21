package com.jt.web.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.service.HttpClientService;
import com.jt.common.vo.SysResult;
import com.jt.web.pojo.User;

//jt-web
@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private HttpClientService clietService;
	
	private static ObjectMapper objectMapper = new ObjectMapper();
	
	
	/**
	 * 1.定义uri
	 * 2.封装数据
	 * 3.通过httpClient发送请求
	 * 4.处理返回值结果
	 */
	@Override
	public String saveUser(User user) {
		String uri = "http://sso.jt.com/user/register";
		Map<String, String> params = new HashMap<String,String>();
		params.put("username", user.getUsername());
		params.put("password", user.getPassword());
		params.put("phone", user.getPhone());
		params.put("email", user.getEmail());
		
		try {
			String jsonData = clietService.doPost(uri, params);
			SysResult sysResult = 
			objectMapper.readValue(jsonData, SysResult.class);
			
			//保证数据是有效的
			if(sysResult.getStatus() == 200){
				return (String) sysResult.getData();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	
	/**
	 * 用户登陆操作
	 * 1.定义url:http://sso.jt.com/user/login
	 * 2.封装参数  u 用户名   p 密码 目的为了节省传输的字节
	 * 3.发起httpClient请求,获取远程的数据
	 * 4.解析远程返回的数据进行校验
	 */
	@Override
	public String findUserByUP(User user) {
		//1.定义uri
		String uri = "http://sso.jt.com/user/login";
		//2.封装参数
		Map<String,String> params = new HashMap<String,String>();
		params.put("u", user.getUsername());
		params.put("p", user.getPassword());
		
		//3.发起请求
		try {
			String jsonData = clietService.doPost(uri, params);
			//将json数据转化为SysResult对象
			SysResult sysResult = 
					objectMapper.readValue(jsonData,SysResult.class);
			String ticket = (String) sysResult.getData();
			//判断数据不为null
			if(!StringUtils.isEmpty(ticket)){
				return ticket;
			}
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return null;
	}
}
