package com.wd.ASFlowerWeb.controller.admin;

import java.math.BigDecimal;
import java.sql.Array;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

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
	
	@RequestMapping("/admin/normalShoppingEdit")
	public ModelAndView normalShoppingEdit(HttpServletRequest request,String op,Integer sid){
		
		ModelAndView mav = null;
		
		DecimalFormat df = new DecimalFormat("0.00");
		Integer type = null;
		String title = "";
		String instruction = "";
		BigDecimal asPrice = null;
		BigDecimal nmPrice = null;
		Integer store = 0;
		String saleTime = "";
		String content = "";
		String imgs = "";
		
		if(request.getParameter("type")!=null && request.getParameter("type").trim().length()==1){
			try{
				type = Integer.valueOf(request.getParameter("type").trim());
			}catch(NumberFormatException e){
				type = null;
			}
		}
		if(request.getParameter("title")!=null && request.getParameter("title").trim().length()>0){
			title = request.getParameter("title").trim();
		}
		if(request.getParameter("instruction")!=null && request.getParameter("instruction").trim().length()>0){
			instruction = request.getParameter("instruction").trim();
		}
		if(request.getParameter("asPrice")!=null && request.getParameter("asPrice").trim().length()>0){
			String m_asPrice = request.getParameter("asPrice").trim();
			m_asPrice = df.format(m_asPrice);
			try{
				asPrice = new BigDecimal(m_asPrice);
			}catch(Exception e){
				
			}
		}
		if(request.getParameter("nmPrice")!=null && request.getParameter("nmPrice").trim().length()>0){
			String m_nmPrice = request.getParameter("nmPrice").trim();
			m_nmPrice = df.format(m_nmPrice);
			try{
				nmPrice = new BigDecimal(m_nmPrice);
			}catch(Exception e){
				
			}
		}
		if(request.getParameter("store")!=null && request.getParameter("store").trim().length()>0){
			try{
				store = Integer.valueOf(request.getParameter("store").trim());
				if(store<=0){
					store = null;
				}
			}catch(NumberFormatException e){
				store = null;
			}
		}
		
		if(op!=null&&op.trim().length()>0){
			if(op.equals("add")){
				
			}else if(op.equals("update")){
				mav = new ModelAndView(new MappingJackson2JsonView());
				if(sid==null || sid<=0){
					mav.addObject("code",0);
					mav.addObject("msg","param error");
				}else{
					
				}
			}
		}else{
			mav = new ModelAndView();
			mav.setViewName("/admin/normal-shopping-edit");
		}
		return mav;
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
