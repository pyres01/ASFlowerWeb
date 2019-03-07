package com.wd.ASFlowerWeb.controller.admin;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.ArrayList;
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
import com.wd.ASFlowerWeb.service.NmShoppingService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author 韦丹
 *
 * 2019年2月14日
 *
 */
@Controller
@Slf4j
public class ShoppingController {

	@Autowired
	private NmShoppingService nmShoppingServer;
	
	@GetMapping("/admin/normalShopping")
	public String normalShoppingIndex(){
		return "admin/normal-shopping";
	}
	
	@RequestMapping("/admin/normalShoppingEdit")
	public ModelAndView normalShoppingEdit(HttpServletRequest request,String op,Integer sid){
		
		ModelAndView mav = null;
		
		Integer type = null;
		String title = "";
		String introduction = "";
		BigDecimal asPrice = null;
		BigDecimal nmPrice = null;
		Integer store = 0;
		Timestamp saleTime = null;
		Boolean isSale = true;
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
		if(request.getParameter("introduction")!=null && request.getParameter("introduction").trim().length()>0){
			introduction = request.getParameter("introduction").trim();
		}
		if(request.getParameter("asPrice")!=null && request.getParameter("asPrice").trim().length()>0){
			String m_asPrice = request.getParameter("asPrice").trim();
			try{
				asPrice = new BigDecimal(m_asPrice);
				asPrice = asPrice.setScale(2, RoundingMode.DOWN);
			}catch(Exception e){
				
			}
		}
		if(request.getParameter("nmPrice")!=null && request.getParameter("nmPrice").trim().length()>0){
			String m_nmPrice = request.getParameter("nmPrice").trim().toString();
			try{
				nmPrice = new BigDecimal(m_nmPrice);
				nmPrice = nmPrice.setScale(2, RoundingMode.DOWN);
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
		if(request.getParameter("saleTime")!=null && request.getParameter("saleTime").trim().length()>0){
			try{
				saleTime = Timestamp.valueOf(request.getParameter("saleTime").trim());
			}catch(Exception e){
			}
		}
		if(request.getParameter("isSale")!=null && request.getParameter("isSale").trim().length()>0){
			String m_isSale = request.getParameter("isSale").trim();
			try{
				isSale = m_isSale.equals("1")?true:false;
			}catch(Exception e){
			}finally{
				log.info(isSale.toString());
			}
		}
		if(request.getParameter("content")!=null && request.getParameter("content").trim().length()>0){
			content = request.getParameter("content").trim();
		}
		if(request.getParameter("imgs")!=null && request.getParameter("imgs").trim().length()>0){
			imgs = request.getParameter("imgs").trim();
		}
		
		if(op!=null&&op.trim().length()>0){
			mav = new ModelAndView(new MappingJackson2JsonView());
			if(op.equals("add")){
				if(type == null || title.equals("") || asPrice == null || nmPrice==null || store==null || saleTime==null || imgs.equals("")){
					mav.addObject("code",0);
					mav.addObject("msg","param error");
				}else{
					NmShopping S = new NmShopping();
					if(!introduction.equals("")){
						S.setIntroduction(introduction);
					}
					if(content.equals("")){
						content = "暂无内容";
					}
					
					S.setTypeId(type);
					S.setShoppingName(title);
					S.setAsPrice(asPrice);
					S.setNmPrice(nmPrice);
					S.setStore(store);
					S.setOnShelveTime(saleTime);
					S.setIsSale(isSale);
					S.setShoppingDetail(content);
					S.setShoppingImg(imgs);
					if(nmShoppingServer.insert(S)){
						mav.addObject("code",200);
						mav.addObject("msg","add success");
					}else{
						mav.addObject("code",0);
						mav.addObject("msg","add fail");
					}
				}
			}else if(op.equals("update")){
				if(sid==null || sid<=0){
					mav.addObject("code",0);
					mav.addObject("msg","param error");
				}else{
					NmShopping editS = nmShoppingServer.getById(sid);
					if(type != null){
						editS.setTypeId(type);
					}
					if(!title.equals("")){
						editS.setShoppingName(title);
					}
					editS.setIntroduction(introduction);
					if(asPrice != null){
						editS.setAsPrice(asPrice);					
					}
					if(nmPrice!=null){
						editS.setNmPrice(nmPrice);
					}
					if(store!=null){
						editS.setStore(store);
					}
					if(saleTime!=null){
						editS.setOnShelveTime(saleTime);
					}
					if(!imgs.equals("")){
						editS.setShoppingImg(imgs);
					}
					editS.setIsSale(isSale);
					if(content.equals("")){
						content = "暂无内容";
					}
					editS.setShoppingDetail(content);
					if(nmShoppingServer.update(editS)){
						mav.addObject("code", 200);
						mav.addObject("msg", "update success");
					}else{
						mav.addObject("code", 0);
						mav.addObject("msg", "update fail");
					}
				}
			}else{
				mav = new ModelAndView();
				mav.setViewName("admin/normal-shopping-edit");
				mav.addObject("op", "add");
				mav.addObject("sid", 0);
			}
		}else{
			mav = new ModelAndView();
			mav.setViewName("admin/normal-shopping-edit");
			if(sid != null){
				mav.addObject("editNmShopping", nmShoppingServer.getById(sid));
				mav.addObject("op", "update");
				mav.addObject("sid", sid);
				log.info(nmShoppingServer.getById(sid).getOnShelveTime().toString());
			}else{
				mav.addObject("op", "add");
				mav.addObject("sid", 0);
			}
		}
		return mav;
	}
	
	
	@GetMapping("/admin/nmShoppingDel")
	@ResponseBody
	public Map<String,Object> nmShoppingDel(int sid){
		Map<String,Object> map = new HashMap<>();
		map.put("code", 0);
		map.put("msg","delete fail");
		if(sid>0 && nmShoppingServer.delete(sid)){
			map.put("code", 200);
			map.put("msg","delete success");
		}
		return map;
		
	}
	
	
	@GetMapping("/admin/skShopping")
	public String skShopping(){
		return "admin/sk-shopping";
	}
	
	@GetMapping("/admin/skShoppingEdit")
	public String skShoppingEditIndex(){
		return "admin/sk-shopping-edit";
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
