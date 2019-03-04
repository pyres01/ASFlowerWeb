package com.wd.ASFlowerWeb.controller.home;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.junit.runners.Parameterized.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.wd.ASFlowerWeb.entity.NmShopping;
import com.wd.ASFlowerWeb.entity.ShoppingCart;
import com.wd.ASFlowerWeb.entity.User;
import com.wd.ASFlowerWeb.service.NmShoppingService;
import com.wd.ASFlowerWeb.service.ShoppingCartService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author 若尘
 *
 * 2019年1月10日
 *
 */
@Controller
@Slf4j
public class HomeShoppingCartController {
	
	
	@Autowired
	private ShoppingCartService scartService;
	@Autowired
	private NmShoppingService nmsService;
	
	@GetMapping("/home/shoppingCart")
	
	public ModelAndView index(HttpServletRequest req){
		ModelAndView mav = new ModelAndView("/home/cart");
		if(!checkLogin(req)){
			mav.addObject("needLogin", true);
		}else{
			mav.addObject("needLogin", false);
			User member = (User) req.getSession().getAttribute("member");
			List<ShoppingCart> cartList = scartService.getByUid(member.getId());
			Map<Integer,Object> shopInfo = new HashMap<>();
			for (ShoppingCart shoppingCart : cartList) {
				NmShopping S = nmsService.getById(shoppingCart.getSid());
				S.setShoppingImg(S.getShoppingImg().split("\\|")[0]);
				shopInfo.put(shoppingCart.getId(), S);
			}
			mav.addObject("cartList", cartList);
			mav.addObject("shopInfo", shopInfo);
		}
		return mav;
	}
	
	@PostMapping("/home/addCart")
	@ResponseBody
	public Map<String,Object> addCart(HttpServletRequest req,Integer sid,Integer count){
		 boolean isLogin = this.checkLogin(req);
		 Map<String,Object> map = new HashMap<>();
		if(!isLogin){
			map.put("code", 0);
			map.put("msg", "请先登陆账户!");
		}else{
			if(sid>0 && count>0){
				User member = (User) req.getSession().getAttribute("member");
				ShoppingCart cart = new ShoppingCart();
				cart.setUid(member.getId());
				cart.setSid(sid);
				cart.setCount(count);
				if(scartService.insert(cart)){
					map.put("code", 200);
					map.put("msg", "亲，在购物车等您哦^_^");
				}else{
					map.put("code", 0);
					map.put("msg", "添加失败了=_=!");
				}
				log.info(cart.toString());
			}else{
				map.put("code", 0);
				map.put("msg", "添加失败了=_=!");
			}
		}
		
		return map;
	}
	
	@PostMapping("/home/updateCart")
	@ResponseBody
	public Map<String,Object> updateCart(HttpServletRequest request){
		if(this.checkLogin(request)){
			String[] infos;
			if(request.getParameterValues("content")!=null){
				infos = request.getParameterValues("content");
				int id;int count;
				for (String i : infos) {
					id = Integer.valueOf(i.split(",")[0]);
					count = Integer.valueOf(i.split(",")[1]);
					scartService.update(id, count);
				}
			}
		}
		return null;
	}
	
	@RequestMapping("/home/delCarti")
	@ResponseBody
	public Map<String,Object> delCarti(HttpServletRequest req,Integer id){
		if(this.checkLogin(req)){
			Map<String,Object> map = new HashMap<>();
			if(id == null || id<0){
				map.put("code",0);
				map.put("msg", "param error");
				
			}else{
				if(scartService.delete(id)){
					map.put("code",200);
					map.put("msg", "删除成功");
				}else{
					map.put("code",0);
					map.put("msg", "删除失败");
				}
			}
			return map;
		}
		return null;
	}
	
	@RequestMapping("/home/delCart")
	@ResponseBody
	public Map<String,Object> delCart(HttpServletRequest req){
		if(this.checkLogin(req)){
			Map<String,Object> map = new HashMap<>();
			User member = (User) req.getSession().getAttribute("member");
			if(scartService.deleteUserAll(member.getId())){
				map.put("code",200);
				map.put("msg", "清空成功");
			}else{
				map.put("code",0);
				map.put("msg", "清空失败");
			}
			return map;
		}
		return null;
	}
	
	private boolean checkLogin(HttpServletRequest req){
		HttpSession session = req.getSession();
		User member = (User) session.getAttribute("member");
		return member == null?false:true;
	}
}
