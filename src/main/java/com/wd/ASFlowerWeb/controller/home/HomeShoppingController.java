package com.wd.ASFlowerWeb.controller.home;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.wd.ASFlowerWeb.service.NmShoppingServer;

import lombok.extern.slf4j.Slf4j;

import com.wd.ASFlowerWeb.entity.NmShopping;;
/**
 * @author 若尘
 *
 * 2019年2月21日
 *
 */
@Controller
@Slf4j
public class HomeShoppingController {
	
	@Autowired
	private NmShoppingServer nmShoppingServer;
	
	@GetMapping("/home/getShoppings")
	@ResponseBody
	public Map<String,Object> getShoppings(HttpServletRequest request){
		Map<String,Object> map = new HashMap<>();
		
		Integer typeId = null;
		Map<Integer,Integer> typePageSizeMap = new HashMap<>();
		typePageSizeMap.put(1, 4);//订阅惊喜
		typePageSizeMap.put(2, 6);//极速送花
		typePageSizeMap.put(3, 6);//永生花礼
		typePageSizeMap.put(4, 4);//花边小物
		
		if(request.getParameter("tid")!=null && request.getParameter("tid").trim().length()==1){
			typeId = Integer.valueOf(request.getParameter("tid").trim());
			if(typePageSizeMap.get(typeId)==null){
				map.put("code", 500);
				map.put("msg", "param error");
			}else{
				int infoCount = nmShoppingServer.countForSearchByType(typeId);
				if(infoCount>0){
					
					List<NmShopping> shoppings = nmShoppingServer.searchByType(typeId, 0, typePageSizeMap.get(typeId));
					
					map.put("code", 200);
					map.put("msg", "success");
					map.put("shoppings", shoppings);
				}else{
					map.put("code", 204);
					map.put("msg", "null");
				}
			}
		}
		
		return map;
	}
	
	@RequestMapping("/home/shoppingDetail")
	@ResponseBody
	public ModelAndView shoppingDetail(HttpServletRequest request,HttpServletResponse response) throws IOException{
		
		Integer sType = null;
		Integer sid = null;
		if(request.getParameter("topic")!=null && request.getParameter("topic").trim().length()>0){
			try {
				sType =  Integer.valueOf(request.getParameter("topic").trim());
			} catch (NumberFormatException e) {
				sType = null;
				log.info("sType null");
			}
		}
		if(request.getParameter("id")!=null && request.getParameter("id").trim().length()>0){
			try {
				sid = Integer.valueOf(request.getParameter("id").trim());
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				sid = null;
				log.info("sid null");
			}
		}
		if(sType==null || sid == null){
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
		}
		ModelAndView mav = null;
		if(request.getMethod().equals("GET")){
			mav = new ModelAndView("/home/detail");
			mav.addObject("t",sType);
			mav.addObject("sid",sid);
		}else{
			mav = new ModelAndView(new MappingJackson2JsonView());
			NmShopping m_Shopping = nmShoppingServer.getByTypeAndId(sType, sid);
			if(m_Shopping == null){
				mav.addObject("code",204);
				mav.addObject("msg","get fail");
			}else{
				mav.addObject("code",200);
				mav.addObject("msg","get success");
				mav.addObject("shopping", m_Shopping);
			}
			
		}
		return mav;
	}
	

}
