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
 * 购物车控制器
 * @author 韦丹
 *
 * 2019年1月10日
 *
 */
@Controller
@Slf4j
public class HomeShoppingCartController {
	
	
	@Autowired
	private ShoppingCartService scartService;	//购物车service
	@Autowired
	private NmShoppingService nmsService;		//商品service
	
	//购物车页面
	@GetMapping("/home/shoppingCart")
	public ModelAndView index(HttpServletRequest req){
		ModelAndView mav = new ModelAndView("home/cart");
		//如果还没登录
		if(!checkLogin(req)){
			mav.addObject("needLogin", true);//告诉页面用户需要登录
		}else{
			mav.addObject("needLogin", false);
			User member = (User) req.getSession().getAttribute("member");
			//根据登录会员的id获取购物车内容
			List<ShoppingCart> cartList = scartService.getByUid(member.getId());
			Map<Integer,Object> shopInfo = new HashMap<>();
			for (ShoppingCart shoppingCart : cartList) {
				NmShopping S = nmsService.getById(shoppingCart.getSid());
				//商品图片仅取第一张，将多张图片src拼接成的字符串拆分切割
				S.setShoppingImg(S.getShoppingImg().split("\\|")[0]);
				shopInfo.put(shoppingCart.getId(), S);
			}
			mav.addObject("cartList", cartList);
			mav.addObject("shopInfo", shopInfo);
		}
		return mav;
	}
	
	//加入购物车
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
				ShoppingCart tmplCart = scartService.getByUidASid(member.getId(),sid);
				
				boolean addStatus = false;
				if(tmplCart!=null){
					if(scartService.update(tmplCart.getId(), tmplCart.getCount()+count)){
						addStatus = true;
					}
				}else{
					ShoppingCart cart = new ShoppingCart();
					cart.setUid(member.getId());
					cart.setSid(sid);
					cart.setCount(count);
					if(scartService.insert(cart)){
						addStatus = true;
					}
				}
				
				if(addStatus){
					map.put("code", 200);
					map.put("msg", "亲，在购物车等您哦^_^");
				}else{
					map.put("code", 0);
					map.put("msg", "添加失败了=_=!");
				}
			}else{
				map.put("code", 0);
				map.put("msg", "添加失败了=_=!");
			}
		}
		
		return map;
	}
	
	//更新购物车，如点击了数量加减按钮
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
					log.info("id:"+id+",count:"+count);
					scartService.update(id, count);
				}
			}
		}
		return null;
	}
	
	//删除购物车商品
	@RequestMapping("/home/delCarti")
	@ResponseBody
	public Map<String,Object> delCarti(HttpServletRequest req){
		if(this.checkLogin(req)){
			Map<String,Object> map = new HashMap<>();
			
			String[] idsStrArr = req.getParameterValues("ks");
			if(idsStrArr==null || idsStrArr.length==0){
				map.put("code",0);
				map.put("msg", "参数有误");
			}else{
				
				for (String item : idsStrArr) {
					try{
						if(scartService.delete(Integer.valueOf(item))){
							continue;
						}else{
							map.put("code",0);
							map.put("msg", "部分删除失败，请联系管理员");
						}
					}catch(Exception e){
						map.put("code",0);
						map.put("msg", "检测到非法参数");
					}
					
				}
				map.put("code",200);
				map.put("msg", "删除成功");
			}
			return map;
		}
		return null;
	}
	
	//清空购物车
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
	
	//判断会员是否登录
	private boolean checkLogin(HttpServletRequest req){
		HttpSession session = req.getSession();
		User member = (User) session.getAttribute("member");
		return member == null?false:true;
	}
}
