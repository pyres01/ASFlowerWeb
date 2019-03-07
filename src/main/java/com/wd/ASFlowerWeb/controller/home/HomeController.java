package com.wd.ASFlowerWeb.controller.home;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author 韦丹
 *
 * 2019年1月10日
 *
 */
@Controller
public class HomeController {

	@GetMapping("/")
	public String webRoot(){
		return "home/index";
	}
	
	@GetMapping("/home/index")
	public String index(){
		return "home/index";
	}
}
