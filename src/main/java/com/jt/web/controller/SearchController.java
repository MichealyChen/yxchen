package com.jt.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jt.dubbo.pojo.Item;
import com.jt.dubbo.service.DubboSearchService;

@Controller
public class SearchController {
	
	@Autowired
	private DubboSearchService searchService;
	
	@RequestMapping("/search")
	public String queryKey(@RequestParam("q")String keyWord,
			Model model){
		//处理keyWord乱码问题
		try {
			keyWord = new String(keyWord.getBytes("ISO-8859-1"),"UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<Item>  itemList = 
				searchService.findItemByKey(keyWord);
		model.addAttribute("query", keyWord);
		model.addAttribute("itemList", itemList);
		//页面转向
		return "search";
	}
}
