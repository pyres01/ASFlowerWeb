package com.wd.ASFlowerWeb.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author 韦丹
 *
 * 2019年1月9日
 * 
 * @desc 后台Index控制器
 *
 */
@Controller
public class IndexController {

	//首页
	@GetMapping("/admin/index")
	public String index(){
		return "admin/index";
	}
	
	//欢迎页面
	@GetMapping("/admin/welcome")
	public String welcome(){
		return "admin/welcome";
	}
	
	//
	@GetMapping("/admin/unicode")
	public String unicode(){
		return "admin/unicode";
	}
}
