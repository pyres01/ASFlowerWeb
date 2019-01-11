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
public class HomeUserInfoController {
	
	@GetMapping("/home/userinfo")
	public String userinfo(){
		return "/home/userinfo";
	}
	
	@GetMapping("/home/useraccount")
	public String useraccount(){
		return "/home/useraccount";
	}
	
	@GetMapping("/home/userAddress")
	public String address(){
		return "/home/address";
	}
	
	@GetMapping("/home/login")
	public String login(){
		return "/home/login";
	}
	
	@GetMapping("/home/register")
	public String register(){
		return "/home/register";
	}
	
	@GetMapping("/home/repassword")
	public String rePassWord(){
		return "/home/repassword";
	}
}
