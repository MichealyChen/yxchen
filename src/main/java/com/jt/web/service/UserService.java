package com.jt.web.service;

import com.jt.web.pojo.User;

public interface UserService {
	//新增用户
	String saveUser(User user);

	String findUserByUP(User user);

}
