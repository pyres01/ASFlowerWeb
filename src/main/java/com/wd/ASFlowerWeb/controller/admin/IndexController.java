package com.wd.ASFlowerWeb.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author 韦丹
 *
 * 2019年1月9日
 *
 */
@Controller
public class IndexController {

	@GetMapping("/admin/index")
	public String index(){
		return "admin/index";
	}
	
	@GetMapping("/admin/welcome")
	public String welcome(){
		return "admin/welcome";
	}
	
	@GetMapping("/admin/unicode")
	public String unicode(){
		return "admin/unicode";
	}
}
