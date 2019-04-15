package com.wd.ASFlowerWeb.controller.home;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.wd.ASFlowerWeb.entity.NmOrder;
import com.wd.ASFlowerWeb.entity.NmOrderItem;
import com.wd.ASFlowerWeb.entity.NmShopping;
import com.wd.ASFlowerWeb.entity.ReceAddress;
import com.wd.ASFlowerWeb.entity.ShoppingCart;
import com.wd.ASFlowerWeb.entity.User;
import com.wd.ASFlowerWeb.mapper.OrderAndItemViewMapper;
import com.wd.ASFlowerWeb.service.NmOrderItemService;
import com.wd.ASFlowerWeb.service.NmOrderService;
import com.wd.ASFlowerWeb.service.NmShoppingService;
import com.wd.ASFlowerWeb.service.ShoppingCartService;
import com.wd.ASFlowerWeb.util.MyUtil;

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
						item.put("cid", cart.getId());
						item.put("buynum", cart.getCount());
						item.put("shop", shop);
						carts.add(item);
						total = total.add(shop.getAsPrice().multiply(new BigDecimal(cart.getCount())));
					}
				}
				if(carts!=null){
					mav.addObject("result", carts);
					mav.addObject("total", total);
				}else{
					try {
						response.sendError(HttpServletResponse.SC_NOT_FOUND);
					} catch (IOException e){}
				}
				
			}else if(type.equals("buy")){//直接购买的
				if(!checkLogin(req)){
					try {
						response.sendRedirect("/home/login");
					} catch (IOException e) {
						
					}
				}else{
					try{
						Integer sid = Integer.valueOf(req.getParameter("sid"));
						Integer num = Integer.valueOf(req.getParameter("n"));
						if(sid>0 && num>0){
							NmShopping shop = nmsService.getById(sid);
							shop.setShoppingImg(shop.getShoppingImg().split("\\|")[0]);
							
							if(shop.getStore()<num){
								mav = new ModelAndView("home/order-confirm-fail");
								mav.addObject("error", "库存不足");
								return mav;
							}
							List<Object> all = new ArrayList<>();
							Map<String,Object> item = new HashMap<>();
							item.put("shop", shop);
							item.put("buynum", num);
							item.put("cid", 0);
							all.add(item);
							mav.addObject("result", all);
							mav.addObject("total", shop.getAsPrice().multiply(new BigDecimal(num.toString())));
						}
					}catch(Exception e){
						try {
							response.sendError(response.SC_NOT_ACCEPTABLE);
						} catch (Exception e1) {
							
						}
					}
					
				}
				
			}
		}else{
			try {
				response.sendError(HttpServletResponse.SC_NOT_FOUND);
			} catch (IOException e){}
		}
		return mav;
	}
	
	
	private Map<String,Object> orderCrete(HttpServletRequest req,HttpServletResponse response){
		Map<String,Object> map = new HashMap<>();
		return map;
	}
	
	
	@PostMapping("/home/order/orderPay")
	@Transactional
	public ModelAndView orderPay(HttpServletRequest req,HttpServletResponse resp){
		ModelAndView mav = new ModelAndView("home/payresult");
		boolean can_pay = true;
		User buyer = (User) req.getSession().getAttribute("member");
		try{
			Integer order_count = Integer.valueOf(req.getParameter("order_count"));
			if(order_count>0){
				BigDecimal total = new BigDecimal("0");
				for(int i=1;i<=order_count;i++){
					NmShopping nmshop = nmsService.getById(Integer.valueOf(req.getParameter("i_id"+i)));
					if(nmshop.getStore()<Integer.valueOf(req.getParameter("i_id"+i))){
						can_pay = false;
						break;
					}
					String buy_num = req.getParameter("i_num"+i);
					if(Integer.valueOf(req.getParameter("i_cid"+i))!=0){
						ShoppingCart cart = cartService.getByid(buyer.getId(), Integer.valueOf(req.getParameter("i_cid"+i)));
						if(cart.getCount() != Integer.valueOf(buy_num)){
							can_pay = false;
							break;
						}
					}
					total = total.add(nmshop.getAsPrice().multiply(new BigDecimal(buy_num)));
				}
				if(can_pay){
					
					NmOrder order = new NmOrder();
					order.setUid(buyer.getId());
					order.setCreateTime(MyUtil.getCurrentTimestamp());
					SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
					String serialNo_pre = sdf.format(MyUtil.getCurrentTimestamp());
					String serialNo_ext = String.valueOf(Math.abs(new Random().nextLong()));
					serialNo_ext = serialNo_ext.substring(0, 2);
					String serialNo = serialNo_pre + serialNo_ext;
					order.setSerialNo(serialNo);
					order.setTotal(total);
					ReceAddress receAddr = (ReceAddress) req.getSession().getAttribute("defReceAddress");
					order.setReceiver(receAddr.getReceiver());
					order.setPhone(receAddr.getPhone());
					order.setAddress(receAddr.getAddress());
					int orderId = oService.save(order);
					if(orderId>0){
						for(int i=1;i<=order_count;i++){
							NmOrderItem item = new NmOrderItem();
							NmShopping nmshop = nmsService.getById(Integer.valueOf(req.getParameter("i_id"+i)));
							item.setSid(nmshop.getId());
							item.setShoppingName(nmshop.getShoppingName());
							item.setIntroduction(nmshop.getIntroduction());
							item.setShoppingImg(nmshop.getShoppingImg().split("\\|")[0]);
							item.setPrice(nmshop.getAsPrice());
							item.setCount(Integer.valueOf(req.getParameter("i_num"+i)));
							item.setSubTotal(item.getPrice().multiply(new BigDecimal(String.valueOf(item.getCount()))));
							item.setStatus(0);
							item.setOid(orderId);
							serialNo_pre = sdf.format(MyUtil.getCurrentTimestamp());
							serialNo_ext = String.valueOf(Math.abs(new Random().nextLong()));
							serialNo_ext = serialNo_ext.substring(0, 2);
							serialNo = serialNo_pre + serialNo_ext;
							item.setSerialNo(serialNo);
							oiService.save(item);
							nmshop.setStore(nmshop.getStore()-Integer.valueOf(req.getParameter("i_num"+i)));
							nmsService.update(nmshop);
							//删除对应购物车
							cartService.delete(Integer.valueOf(req.getParameter("i_cid"+i)));
							
						}
					}
				}
				
			}
			
		}catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		
		
		
		return mav;
	}
	
	private boolean checkLogin(HttpServletRequest req){
		HttpSession session = req.getSession();
		User member = (User) session.getAttribute("member");
		return member == null?false:true;
	}
}
