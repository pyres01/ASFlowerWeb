package com.wd.ASFlowerWeb.controller.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.wd.ASFlowerWeb.entity.OrderAndItemView;
import com.wd.ASFlowerWeb.mapper.OrderAndItemViewMapper;
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
	
	@Autowired
	private OrderAndItemViewMapper oaivMapper;
	
	@Autowired
	private NmOrderItemService noiServer;
	
	/**
	 * 后台订单列表
	 * @param req
	 * @return
	 */
	@RequestMapping("/admin/order/readyPost")
	public ModelAndView rePostlist(HttpServletRequest req){
		ModelAndView mav = new ModelAndView();
		mav.setViewName("admin/nm-order");
		List<OrderAndItemView> oais = oaivMapper.selectAllReayPost();
		mav.addObject("list", oais);
		return mav;
	}
	
	@PostMapping("/admin/order/Post")
	public ModelAndView post(HttpServletRequest req){
		ModelAndView mav = new ModelAndView(new MappingJackson2JsonView());
		
		Integer order_item_id = null;
		boolean update = true;
		try{
			order_item_id = Integer.valueOf(req.getParameter("oid"));
			if(!noiServer.updateStatus(order_item_id, 2)){
				update = false;
			}
		}catch(Exception e){
			update = false;
		}
		
		if(update){
			mav.addObject("code", 200);
		}else{
			mav.addObject("code", 0);
		}
		
		return mav;
	}

}
