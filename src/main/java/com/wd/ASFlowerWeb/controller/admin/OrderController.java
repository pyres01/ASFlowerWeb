package com.wd.ASFlowerWeb.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.wd.ASFlowerWeb.entity.NmOrderItem;
import com.wd.ASFlowerWeb.entity.OrderAndItemView;
import com.wd.ASFlowerWeb.mapper.OrderAndItemViewMapper;
import com.wd.ASFlowerWeb.service.NmOrderItemService;
import com.wd.ASFlowerWeb.service.NmOrderService;

import lombok.extern.slf4j.Slf4j;

/**
 * 后台订单控制器
 * @author 韦丹
 *
 */

@Controller
@Slf4j
public class OrderController {
	
	@Autowired 
	private NmOrderService nmOrderService;	//订单service
	
	@Autowired 
	private NmOrderItemService nmOrderItemService; //订单项service
	
	@Autowired
	private OrderAndItemViewMapper oaivMapper;	//订单-订单项视图
	
	
	/**
	 * 待发货
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
	
	//获取已发货，只写了接口，暂无用到
	@PostMapping("/admin/order/Post")
	public ModelAndView post(HttpServletRequest req){
		ModelAndView mav = new ModelAndView(new MappingJackson2JsonView());
		
		Integer order_item_id = null;
		boolean update = true;
		try{
			order_item_id = Integer.valueOf(req.getParameter("oid"));
			if(!nmOrderItemService.updateStatus(order_item_id, 2)){
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
	
	//后台发货
	@PostMapping("/admin/order/toPost")
	@ResponseBody
	public Map<String,Object> toPost(HttpServletRequest req){
		Map<String,Object> map = new HashMap<String, Object>();
		
		try{
			Integer id = Integer.valueOf(req.getParameter("id"));
			if(id>0){
				NmOrderItem item = nmOrderItemService.getByid(id);
				if(item.getStatus()==1){
					nmOrderItemService.updateStatus(item.getId(), 2);
					map.put("code", 200);
					map.put("msg", "发货成功");
				}
				
			}else{
				map.put("code", 0);
				map.put("msg", "参数异常");
			}
		}catch(Exception e){
			map.put("code", 0);
			map.put("msg", "发货失败");
			log.info(e.getMessage());
		}
		
		return map;
	}

}
