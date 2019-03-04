package com.wd.ASFlowerWeb.controller.home;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wd.ASFlowerWeb.entity.NmOrder;
import com.wd.ASFlowerWeb.entity.NmOrderItem;
import com.wd.ASFlowerWeb.entity.NmShopping;
import com.wd.ASFlowerWeb.entity.User;
import com.wd.ASFlowerWeb.service.NmShoppingService;
import com.wd.ASFlowerWeb.service.OrderItemService;
import com.wd.ASFlowerWeb.service.OrderService;

/**
 * @author 若尘
 *
 * 2019年1月10日
 *
 */
@Controller
public class HomeOrderController {

	@Autowired
	private NmShoppingService nmsService;
	@Autowired
	private OrderService oService;
	@Autowired
	private OrderItemService oiService;
	
	@GetMapping("/home/order")
	public String order(){
		return "/home/order";
	}
	
	@PostMapping("/home/orderConfirm")
	@ResponseBody
	public Map<String,Object> orderConfirm(HttpServletRequest req){
		String[] shops = null;
		if(req.getParameterValues("shops")!=null){
			
			if(shops.length>0){
				shops = req.getParameterValues("shops");
				NmOrder order = new NmOrder();
				List<NmOrderItem> ois = new ArrayList<>();
				order.setCreateTime(new Timestamp(System.currentTimeMillis()));
				User member = (User) req.getSession().getAttribute("member");
				order.setUid(member.getId());
				for (String it : shops) {
					Integer sid = Integer.valueOf(it.split(",")[0]);
					Integer count = Integer.valueOf(it.split(",")[1]);
					if(sid>0 && count>0){
						NmShopping ns =  nmsService.getById(sid);
						NmOrderItem oi = new NmOrderItem();
						
					}else{
						
					}
				}
			}
			
		}
		
		return null;
	}
}
