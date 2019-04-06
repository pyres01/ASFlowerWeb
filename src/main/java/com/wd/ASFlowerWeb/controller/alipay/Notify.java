package com.wd.ASFlowerWeb.controller.alipay;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class Notify {
	
	@RequestMapping("order/alipayNotify")
	public String index(){
		return "";
	}
}
