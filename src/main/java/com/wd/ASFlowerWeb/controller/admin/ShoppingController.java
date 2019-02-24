package com.wd.ASFlowerWeb.controller.admin;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wd.ASFlowerWeb.entity.NmShopping;
import com.wd.ASFlowerWeb.mapper.NmShoppingMapper;
import com.wd.ASFlowerWeb.service.NmShoppingServer;

import lombok.extern.slf4j.Slf4j;

/**
 * @author 若尘
 *
 * 2019年2月14日
 *
 */
@Controller
@Slf4j
public class ShoppingController {

	@Autowired
	private NmShoppingServer nmShoppingServer;
	
	@GetMapping("/admin/normalShopping")
	public String normalShoppingIndex(){
		return "/admin/normal-shopping";
	}
	
	@GetMapping("/admin/normalShoppingEdit")
	public String normalShoppingEditIndex(){
		return "/admin/normal-shopping-edit";
	}
	
	@GetMapping("/admin/skShopping")
	public String skShopping(){
		return "/admin/sk-shopping";
	}
	
	@GetMapping("/admin/skShoppingEdit")
	public String skShoppingEditIndex(){
		return "/admin/sk-shopping-edit";
	}
	
	
	@GetMapping("/admin/searchNmShoppings")
	@ResponseBody
	public Map<String,Object> searchNmShoppings(HttpServletRequest request){
		int pageSize = 15;
		
		Map<String,Object> map = new HashMap<>();
		map.put("code", 200);
		map.put("msg", "search success");
		
		String sname = "";
		String[] typeIds = null;
		List<Integer> typeIdList =new ArrayList<Integer>();
		
		int page = 1;
		if(request.getParameterValues("typeIds[]")!=null){
			typeIds = request.getParameterValues("typeIds[]");
			for (String tp : typeIds) {
				typeIdList.add(Integer.valueOf(tp));
			}
		}else{
			log.info("typeIds null");
			typeIdList.add(1);
			typeIdList.add(2);
			typeIdList.add(3);
			typeIdList.add(4);
		}
		if(request.getParameter("page")!=null){
			page = Integer.valueOf(request.getParameter("page").trim());
		}
		
		if(request.getParameter("sname")!=null){
			sname = request.getParameter("sname").trim();
		}
		
		if(sname.length()>0){
			int infoCount = nmShoppingServer.countForSearchByTypeAndName(typeIdList,sname);
			if(infoCount>0){
				int pageCount = (int) Math.ceil((double)infoCount/pageSize);
				page = Math.min(pageCount, Math.max(1,page));
				List<NmShopping> shoppings = nmShoppingServer.searchByTypeAndName(typeIdList,sname,(page-1)*pageSize, pageSize);
				map.put("code", 200);
				map.put("msg", "search success");
				map.put("pageCount", pageCount);
				map.put("shopCount", infoCount);
				map.put("shoppings", shoppings);
			}else{
				map.put("code", 0);
				map.put("msg", "null");
			}
		}else{
			int infoCount = nmShoppingServer.countForSearchByTypes(typeIdList);
			if(infoCount>0){
				int pageCount = (int) Math.ceil((double)infoCount/pageSize);
				page = Math.min(pageCount, Math.max(1,page));
				List<NmShopping> shoppings = nmShoppingServer.serarchByTypes(typeIdList,(page-1)*pageSize, pageSize);
				map.put("code", 200);
				map.put("msg", "search success");
				map.put("pageCount", pageCount);
				map.put("shopCount", infoCount);
				map.put("shoppings", shoppings);
			}else{
				map.put("code", 0);
				map.put("msg", "null");
			}
		}
		return map;
	}
}
