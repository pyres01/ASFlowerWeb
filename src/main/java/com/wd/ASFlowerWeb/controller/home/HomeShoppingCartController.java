package com.wd.ASFlowerWeb.controller.home;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author 若尘
 *
 * 2019年1月10日
 *
 */
@Controller
public class HomeShoppingCartController {
	
	@GetMapping("/home/shoppingCart")
	public String index(){
		return "/home/cart";
	}
}