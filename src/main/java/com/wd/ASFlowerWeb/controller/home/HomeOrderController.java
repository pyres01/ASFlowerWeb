package com.wd.ASFlowerWeb.controller.home;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.wd.ASFlowerWeb.entity.NmOrder;
import com.wd.ASFlowerWeb.entity.NmOrderItem;
import com.wd.ASFlowerWeb.entity.NmShopping;
import com.wd.ASFlowerWeb.entity.ReceAddress;
import com.wd.ASFlowerWeb.entity.User;
import com.wd.ASFlowerWeb.service.NmShoppingService;
import com.wd.ASFlowerWeb.service.ShoppingCartService;
import com.wd.ASFlowerWeb.service.NmOrderItemService;
import com.wd.ASFlowerWeb.service.NmOrderService;
import com.wd.ASFlowerWeb.util.MyUtil;

import lombok.extern.slf4j.Slf4j;
/**
 * @author 若尘
 *
 * 2019年1月10日
 *
 */
@Controller
@Slf4j
public class HomeOrderController {

	@Autowired
	private NmShoppingService nmsService;
	@Autowired
	private NmOrderService oService;
	@Autowired
	private NmOrderItemService oiService;
	@Autowired
	private ShoppingCartService cartService;
	
	@GetMapping("/home/order")
	public String order(){
		return "/home/order";
	}
	
	
	
	/*
	 * 
	 * 1.验证提交商品，将合法商品添加到一个容器
	 * 2.创建订单
	 * 3.创建订单项
	 * 4.删除对应购物车
	 * 
	 */
	
	@PostMapping("/home/orderCreate")
	@ResponseBody
	@Transactional(rollbackFor = Exception.class)
	public Map<String,Object> orderCreate(HttpServletRequest req){
		String[] _shops = null;
		List<NmShopping> shops = new ArrayList<>();
		Map<Integer,Integer> buyCountMap = new HashMap<>();
		
		boolean beforeConfrimStatus = true;
		if(req.getParameterValues("shops")!=null){
			/*
			 * 将合法存在的商品添加进 shops容器
			 * _shops:前台商品数据
			 * sid:商品id count:购买数量
			 * total 订单合计
			 */
			_shops = req.getParameterValues("shops");
			if(_shops.length>0){
				try{
					int sid=0,count=0;
					BigDecimal total = new BigDecimal("0");
					total.setScale(2, RoundingMode.DOWN);
					for (String it : _shops) {
						sid = Integer.valueOf(it.split(",")[0]);
						count = Integer.valueOf(it.split(",")[1]);
						if(sid>0 && count>0){
							NmShopping iShop = nmsService.getById(sid);
							if(iShop!=null && iShop.getStore()>0){
								shops.add(iShop);
								buyCountMap.put(iShop.getId(), count);
								total = total.add(iShop.getAsPrice());
							}
						}
					}
					//创建订单
					NmOrder order = new NmOrder();
					order.setCreateTime(MyUtil.getCurrentTimestamp());
					User member = (User) req.getSession().getAttribute("member");
					order.setUid(member.getId());
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
					//保存订单项
					if(orderId>0){
						for (NmShopping nmShopping : shops) {
							NmOrderItem item = new NmOrderItem();
							item.setSid(nmShopping.getId());
							item.setShoppingName(nmShopping.getShoppingName());
							item.setIntroduction(nmShopping.getIntroduction());
							item.setShoppingImg(nmShopping.getShoppingImg().split("\\|")[0]);
							item.setPrice(nmShopping.getAsPrice());
							item.setCount(buyCountMap.get(nmShopping.getId()));
							item.setSubTotal(item.getPrice().multiply(new BigDecimal(String.valueOf(item.getCount()))));
							item.setStatus(0);
							item.setOid(orderId);
							serialNo_pre = sdf.format(MyUtil.getCurrentTimestamp());
							serialNo_ext = String.valueOf(Math.abs(new Random().nextLong()));
							serialNo_ext = serialNo_ext.substring(0, 2);
							serialNo = serialNo_pre + serialNo_ext;
							item.setSerialNo(serialNo);
							oiService.save(item);
							//删除对应购物车
							cartService.deleteBySid(member.getId(), nmShopping.getId());
						}
					}else{
						beforeConfrimStatus = false;
					}
				}catch(Exception e){
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					beforeConfrimStatus = false;
				}
			}else{
				beforeConfrimStatus = false;
			}
			
		}else{
			beforeConfrimStatus = false;
		}
		Map<String,Object> map = new HashMap<>();
		//创建成功
		if(beforeConfrimStatus){
			map.put("code", 200);
			map.put("msg", "success");
			map.put("href", "/home/sureorder");
		}else{//创建失败
			map.put("code", 200);
			map.put("msg", "success");
			map.put("href", "/home/order-fail");
		}
		return map;
	}
	
	@PostMapping("/home/orderConfirm")
	public ModelAndView orderConfirm(HttpServletRequest req,HttpServletResponse response){
		ModelAndView mav = new ModelAndView("/home/sureorder");
		User member = (User) req.getSession().getAttribute("member");
		NmOrder order = oService.getULastOrder(member.getId());
		if(order == null){
			try {
				response.sendError(HttpServletResponse.SC_NOT_FOUND);
			} catch (IOException e) {
			}
		}
		List<NmOrderItem> oitems = oiService.getByOid(order.getId());
		mav.addObject("order", order);
		mav.addObject("orderitems",oitems);
		return mav;
	}
	
	@PostMapping("/home/orderfail")
	public String orderFail(HttpServletRequest req){
		return "/home/order-fail";
	}
}
