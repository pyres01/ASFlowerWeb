package com.wd.ASFlowerWeb.controller.home;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.wd.ASFlowerWeb.entity.NmOrder;
import com.wd.ASFlowerWeb.entity.NmOrderItem;
import com.wd.ASFlowerWeb.entity.NmShopping;
import com.wd.ASFlowerWeb.entity.ShoppingCart;
import com.wd.ASFlowerWeb.entity.User;
import com.wd.ASFlowerWeb.mapper.OrderAndItemViewMapper;
import com.wd.ASFlowerWeb.service.NmOrderItemService;
import com.wd.ASFlowerWeb.service.NmOrderService;
import com.wd.ASFlowerWeb.service.NmShoppingService;
import com.wd.ASFlowerWeb.service.ShoppingCartService;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author 韦丹
 * 
 * @desc 前台订单
 *
 */

@Controller
@Slf4j
public class HomeNmOrderController {

	@Autowired
	private NmShoppingService nmsService;
	@Autowired
	private NmOrderService oService;
	@Autowired
	private NmOrderItemService oiService;
	@Autowired
	private ShoppingCartService cartService;
	@Autowired
	private OrderAndItemViewMapper oaivMapper;
	
	/**
	 * 
	 * @Desc 确认订单
	 * @param req
	 * @param response
	 * @return
	 */
	@GetMapping("/home/order/orderConfirm")
	public ModelAndView orderConfirm(HttpServletRequest req,HttpServletResponse response){
		ModelAndView mav = new ModelAndView("home/sureorder");
		
		String type = "";
		if(req.getParameter("type")!=null){
			type = req.getParameter("type");
			if(type.equals("cart")){//从购物车过来的
				log.info(req.getParameter("items"));
				String[] cartIds = req.getParameter("items").split(",");
				Integer[] ids = new Integer[cartIds.length];
				int i=0;
				for (String id : cartIds) {
					ids[i++] = Integer.valueOf(id);
				}
				List<Object> carts = new ArrayList<>();
				HttpSession session = req.getSession();
				Integer uid = ((User)session.getAttribute("member")).getId();
				List<Integer>uCartIds = cartService.getIdsByUid(uid);
				BigDecimal total = new BigDecimal("0");
				for (Integer id : ids) {
					if(uCartIds.contains(id)){
						Map<String,Object> item = new HashMap<>();
						ShoppingCart cart = cartService.getByid(uid, id);
						NmShopping shop = nmsService.getById(cart.getSid());
						shop.setShoppingImg(shop.getShoppingImg().split("\\|")[0]);
						item.put("cart", cart);
						item.put("shop", shop);
						carts.add(item);
						total = total.add(shop.getAsPrice().multiply(new BigDecimal(cart.getCount())));
					}
				}
				if(carts!=null){
					log.info(carts.toString());
					mav.addObject("result", carts);
					mav.addObject("code", 200);
					mav.addObject("msg", "");
					mav.addObject("total", total);
				}else{
					try {
						response.sendError(HttpServletResponse.SC_NOT_FOUND);
					} catch (IOException e){}
				}
				
			}else if(type.equals("buy")){//直接购买的
				
			}
		}else{
			try {
				response.sendError(HttpServletResponse.SC_NOT_FOUND);
			} catch (IOException e){}
		}
		return mav;
	}
	
	@PostMapping("/home/order/orderCreate")
	public ModelAndView orderCrete(HttpServletRequest req,HttpServletResponse response){
		ModelAndView mav = new ModelAndView("home/sureorder");
		return mav;
	}
}
