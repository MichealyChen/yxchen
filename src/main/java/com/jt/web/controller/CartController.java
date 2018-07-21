package com.jt.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.common.vo.SysResult;
import com.jt.dubbo.pojo.Cart;
import com.jt.dubbo.service.DubboCartService;
import com.jt.web.thread.UserThreadLocal;

@Controller
@RequestMapping("/cart")
public class CartController {
	
	@Autowired
	private DubboCartService cartService;
	
	@RequestMapping("/show")
	public String show(Model model){
		//根据用户信息查询购物车
		Long userId = UserThreadLocal.get().getId();
		List<Cart> cartList = 
				cartService.findCartListByUserId(userId);
		model.addAttribute("cartList", cartList);
		System.out.println(cartService.getClass());
		//转向到购物车页面
		return "cart";
	}
	
	///update/num/1474391962/18
	@RequestMapping("/update/num/{itemId}/{num}")
	@ResponseBody
	public SysResult updateCartNum(@PathVariable Long itemId,
			@PathVariable Integer num){
		try {
			Long userId = UserThreadLocal.get().getId();
			Cart cart = new Cart();
			cart.setUserId(userId);
			cart.setItemId(itemId);
			cart.setNum(num);
			cartService.updateCartNum(cart);
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201,"商品数量修改失败");
	}
	
	//购物车的删除操作
	/**
	 * @param itemId
	 * @return
	 */
	@RequestMapping("/delete/{itemId}")
	public String deleteCart(@PathVariable Long itemId){
		
		//获取用户id
		Long userId = UserThreadLocal.get().getId();
		Cart cart = new Cart();
		cart.setUserId(userId);
		cart.setItemId(itemId);
		cartService.deleteCart(cart);
		//返回购物车页面
		return "redirect:/cart/show.html";
	}
	
	//购物车新增  http://www.jt.com/cart/add/562379.html
	@RequestMapping("/add/{itemId}")
	public String saveCart(@PathVariable Long itemId,Cart cart){
		
		Long userId = UserThreadLocal.get().getId();
		cart.setUserId(userId);
		cart.setItemId(itemId);
		cartService.saveCart(cart);
		return "redirect:/cart/show.html";
	}
	
	
	
	
	
	
	
}
