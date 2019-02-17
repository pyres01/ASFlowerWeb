package com.wd.ASFlowerWeb.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author 若尘
 *
 * 2019年2月14日
 *
 */
@Controller
public class ShoppingController {

	@GetMapping("/admin/normalShopping")
	public String normalShoppingIndex(){
		return "/admin/normal-shopping";
	}
	
	@GetMapping("/admin/normalShoppingEdit")
	public String normalShoppingEditIndex(){
		return "/admin/normal-shopping-edit";
	}
	
	@GetMapping("/admin/skShopping")
	public String skShopping(){
		return "/admin/sk-shopping";
	}
	
	@GetMapping("/admin/skShoppingEdit")
	public String skShoppingEditIndex(){
		return "/admin/sk-shopping-edit";
	}
	
	@GetMapping("/home/shoppingDetail")
	public String shoppingDetail(){
		return "/home/detail";
	}
}
