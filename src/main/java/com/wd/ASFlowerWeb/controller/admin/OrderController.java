package com.wd.ASFlowerWeb.controller.admin;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.wd.ASFlowerWeb.service.NmOrderItemService;
import com.wd.ASFlowerWeb.service.NmOrderService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class OrderController {
	
	@Autowired 
	private NmOrderService nmOrderService;
	
	@Autowired 
	private NmOrderItemService nmOrderItemService;
	
	/**
	 * 后台订单列表
	 * @param req
	 * @return
	 */
	@RequestMapping("/admin/order")
	public ModelAndView list(HttpServletRequest req){
		ModelAndView mav = null;
		
		mav = new ModelAndView();
		mav.setViewName("admin/nm-order");
		return mav;
	}

}
