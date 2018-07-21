package com.jt.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.service.HttpClientService;
import com.jt.web.pojo.Item;

@Service
public class ItemServiceImpl implements ItemService{
	
	@Autowired
	private HttpClientService clientService;
	
	private static ObjectMapper objectMapper = new ObjectMapper();
	
	//获取Item数据
	/**
	 * 通过serivce实现的跨域请求
	 * 1.注入httpClient对象
	 * 2.定义uri
	 * 3.发起请求
	 * 4.检测响应是否正确
	 * 5.整理数据将数据返回
	 * 
	 * 回顾:
	 * 	get请求:localhost:8091/web/addUser?id=1&name=tom
	 * RestFul:
	 * 	参数与参数之间采用"/"分割,而且参数的位置固定
	 *  restFul:localhost:8091/web/addUser/1/tom
	 * 
	 * 易错错误:
	 * 	通过httpClient请求返回的数据是JSON数据可能会出现null的现象
	 * 则转化为JSON数据时变成 "null".
	 */
	@Override
	public Item findItemById(Long itemId) {
		String uri = 
	"http://manage.jt.com/web/item/findItemById/"+itemId;
		try {
			String jsonData = clientService.doGet(uri);
			//判断是否是否有效
			if(StringUtils.isEmpty(jsonData) 
					|| "null".equals(jsonData)){
				//表示返回的数据有误
				return null;
			}
			//如果程序执行到这行,表示返回值结果正确
			Item item = 
			objectMapper.readValue(jsonData,Item.class);
			//System.out.println("远程调用成功!!!!!!");
			return item;
			
		} catch (Exception e) {
			e.printStackTrace();
		}	
		return null;
	}
}
