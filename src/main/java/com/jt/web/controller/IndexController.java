package com.jt.web.controller;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController implements BeanNameAware,BeanFactoryAware{
	
	//展现系统首页
	@RequestMapping("/index")
	public String index(){
		
		return "index";
	}
	
	//获取Bean的名称
	@Override
	public void setBeanName(String name) {
		System.out.println("获取bean的Id"+name);
		
	}
	
	//获取Spring的工厂对象
	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		
		
	}
}
