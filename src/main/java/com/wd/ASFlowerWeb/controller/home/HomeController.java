package com.wd.ASFlowerWeb.controller.home;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 前台首页控制器
 * @author 韦丹
 *
 * 2019年1月10日
 *
 */
@Controller
public class HomeController {

	//显示首页
	@GetMapping("/")
	public String webRoot(){
		return "home/index";
	}
	
	//显示首页
	@GetMapping("/home")
	public String home(){
		return "home/index";
	}
	//显示首页
	@GetMapping("/home/index")
	public String index(){
		return "home/index";
	}
}
