package com.jt.web.thread;

import com.jt.web.pojo.User;

public class UserThreadLocal {
	
	//创建本地线程变量
	private static ThreadLocal<User> userThread = new ThreadLocal<User>();
	public static void set(User user){
		userThread.set(user);
	}
	
	public static User get(){
		
		return userThread.get();
	}
	
	public static void remove(){
		
		userThread.remove();
	}
}
