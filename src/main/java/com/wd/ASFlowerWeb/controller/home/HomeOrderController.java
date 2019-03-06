package com.wd.ASFlowerWeb.controller.home;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

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
import com.wd.ASFlowerWeb.entity.User;
import com.wd.ASFlowerWeb.service.NmShoppingService;
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
	
	@GetMapping("/home/order")
	public String order(){
		return "/home/order";
	}
	
	@PostMapping("/home/orderConfirm")
	@ResponseBody
	@Transactional(rollbackFor = Exception.class)
	public ModelAndView orderConfirm(HttpServletRequest req){
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
					int orderId = oService.save(order);
					//保存订单项
					if(orderId>0){
						for (NmShopping nmShopping : shops) {
							NmOrderItem item = new NmOrderItem();
							item.setSid(nmShopping.getId());
							item.setShoppingName(nmShopping.getShoppingName());
							item.setIntroduction(nmShopping.getIntroduction());
							item.setShoppingImg(nmShopping.getShoppingImg().split("|")[0]);
							item.setPrice(nmShopping.getAsPrice());
							item.setCount(buyCountMap.get(nmShopping.getId()));
							item.setSubTotal(item.getPrice().multiply(new BigDecimal(String.valueOf(item.getCount()))));
							item.setStatus(0);
							item.setOid(orderId);
							oiService.save(item);
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
		ModelAndView mav = new ModelAndView();
		//创建成功
		if(beforeConfrimStatus){
			mav.setViewName("/home/sureorder");
		}else{//创建失败
			mav.setViewName("/home/order-fail");
		}
		return mav;
	}
}
