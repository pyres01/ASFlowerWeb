package com.wd.ASFlowerWeb.controller.alipay;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class Return {

	@RequestMapping("order/alipayReturn")
	public String index(){
		return "";
	}
}
