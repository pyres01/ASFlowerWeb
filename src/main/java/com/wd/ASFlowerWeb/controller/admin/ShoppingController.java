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
 * 商品控制器
 * @author 韦丹
 *
 * 2019年2月14日
 *
 */
@Controller
@Slf4j
public class ShoppingController {

	@Autowired
	private NmShoppingService nmShoppingServer;	//商品service
	
	//商品列表
	@GetMapping("/admin/normalShopping")
	public String normalShoppingIndex(){
		return "admin/normal-shopping";
	}
	
	//商品编辑
	@RequestMapping("/admin/normalShoppingEdit")
	public ModelAndView normalShoppingEdit(HttpServletRequest request,String op,Integer sid){
		
		ModelAndView mav = null;
		
		/**
		 * type 商品分类
		 * title 商品标题
		 * introduction 商品介绍
		 * asPrice 爱尚价格
		 * nmPrice 市场价格
		 * store 库存
		 * saleTime 上架时间
		 * isSale 是否售卖
		 * content 商品详情
		 * imgs 商品图片
		 */
		
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
		
		//获取商品分类
		if(request.getParameter("type")!=null && request.getParameter("type").trim().length()==1){
			try{
				type = Integer.valueOf(request.getParameter("type").trim());
			}catch(NumberFormatException e){
				type = null;
			}
		}
		//获取商品标题
		if(request.getParameter("title")!=null && request.getParameter("title").trim().length()>0){
			title = request.getParameter("title").trim();
		}
		//获取商品介绍
		if(request.getParameter("introduction")!=null && request.getParameter("introduction").trim().length()>0){
			introduction = request.getParameter("introduction").trim();
		}
		//获取爱尚价格
		if(request.getParameter("asPrice")!=null && request.getParameter("asPrice").trim().length()>0){
			String m_asPrice = request.getParameter("asPrice").trim();
			try{
				asPrice = new BigDecimal(m_asPrice);
				asPrice = asPrice.setScale(2, RoundingMode.DOWN);
			}catch(Exception e){
				
			}
		}
		//获取市场价格
		if(request.getParameter("nmPrice")!=null && request.getParameter("nmPrice").trim().length()>0){
			String m_nmPrice = request.getParameter("nmPrice").trim().toString();
			try{
				nmPrice = new BigDecimal(m_nmPrice);
				nmPrice = nmPrice.setScale(2, RoundingMode.DOWN);
			}catch(Exception e){
				
			}
		}
		//获取库存
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
		//获取上架时间
		if(request.getParameter("saleTime")!=null && request.getParameter("saleTime").trim().length()>0){
			try{
				saleTime = Timestamp.valueOf(request.getParameter("saleTime").trim());
			}catch(Exception e){
			}
		}
		//获取是否售卖
		if(request.getParameter("isSale")!=null && request.getParameter("isSale").trim().length()>0){
			String m_isSale = request.getParameter("isSale").trim();
			try{
				isSale = m_isSale.equals("1")?true:false;
			}catch(Exception e){
			}finally{
			}
		}
		//获取商品详情
		if(request.getParameter("content")!=null && request.getParameter("content").trim().length()>0){
			content = request.getParameter("content").trim();
		}
		//获取商品图片，前台用|分隔多个图片进行拼接
		if(request.getParameter("imgs")!=null && request.getParameter("imgs").trim().length()>0){
			imgs = request.getParameter("imgs").trim();
		}
		
		//判断操作
		if(op!=null&&op.trim().length()>0){
			mav = new ModelAndView(new MappingJackson2JsonView());
			//添加商品
			if(op.equals("add")){
				if(type == null || title.equals("") || asPrice == null || nmPrice==null || store==null || saleTime==null || imgs.equals("")){
					mav.addObject("code",0);
					mav.addObject("msg","参数不合法");
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
						mav.addObject("msg","添加成功");
					}else{
						mav.addObject("code",0);
						mav.addObject("msg","添加失败");
					}
				}
			}else if(op.equals("update")){	//更新商品
				if(sid==null || sid<=0){
					mav.addObject("code",0);
					mav.addObject("msg","非法操作");
				}else{
					//根据id获取商品信息，如果有更新就设置保存
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
						mav.addObject("msg", "更新成功");
					}else{
						mav.addObject("code", 0);
						mav.addObject("msg", "更新失败");
					}
				}
			}else{
				//加载添加窗口，设置op=add，前台判断如果op=add表示即将进行添加操作，设置sid=0表示没有商品，是添加操作的
				mav = new ModelAndView();
				mav.setViewName("admin/normal-shopping-edit");
				mav.addObject("op", "add");
				mav.addObject("sid", 0);
			}
		}else{
			//加载更新窗口，设置op=update，sid,前台判断op为update,根据商品id（sid）加载要更新的商品数据填充到修改窗口
			mav = new ModelAndView();
			mav.setViewName("admin/normal-shopping-edit");
			if(sid != null){
				mav.addObject("editNmShopping", nmShoppingServer.getById(sid));
				mav.addObject("op", "update");
				mav.addObject("sid", sid);
			}else{//如果没有商品id，那就是添加的，显示添加窗口
				mav.addObject("op", "add");
				mav.addObject("sid", 0);
			}
		}
		return mav;
	}
	
	//删除商品
	@GetMapping("/admin/nmShoppingDel")
	@ResponseBody
	public Map<String,Object> nmShoppingDel(int sid){
		Map<String,Object> map = new HashMap<>();
		map.put("code", 0);
		map.put("msg","删除失败");
		
		//如果删除成功，更新返回信息code,msg
		if(sid>0 && nmShoppingServer.delete(sid)){
			map.put("code", 200);
			map.put("msg","删除成功");
		}
		return map;
		
	}
	
	//秒杀部分，毕设选择不要此部分
	@GetMapping("/admin/skShopping")
	public String skShopping(){
		return "admin/sk-shopping";
	}
	
	//秒杀部分，毕设选择不要此部分
	@GetMapping("/admin/skShoppingEdit")
	public String skShoppingEditIndex(){
		return "admin/sk-shopping-edit";
	}
	
	//根据条件查询商品信息
	@GetMapping("/admin/searchNmShoppings")
	@ResponseBody
	public Map<String,Object> searchNmShoppings(HttpServletRequest request){
		//分页数
		int pageSize = 15;
		
		//先临时设好返回信息
		Map<String,Object> map = new HashMap<>();
		map.put("code", 200);
		map.put("msg", "查询成功");
		
		String sname = "";
		String[] typeIds = null;
		List<Integer> typeIdList =new ArrayList<Integer>();
		
		int page = 1;
		
		//如果商品分类不为空，勾选了多个商品分类
		if(request.getParameterValues("typeIds[]")!=null){
			typeIds = request.getParameterValues("typeIds[]");
			for (String tp : typeIds) {
				typeIdList.add(Integer.valueOf(tp));
			}
		}else{//否则不分商品分类，表示全部
			typeIdList.add(1);
			typeIdList.add(2);
			typeIdList.add(3);
			typeIdList.add(4);
		}
		//获取第几页
		if(request.getParameter("page")!=null){
			page = Integer.valueOf(request.getParameter("page").trim());
		}
		
		//获取搜索商品名
		if(request.getParameter("sname")!=null){
			sname = request.getParameter("sname").trim();
		}
		
		//如果商品名不为空，调用分类下的商品搜索
		if(sname.length()>0){
			int infoCount = nmShoppingServer.countForSearchByTypeAndName(typeIdList,sname);
			//搜索结果有匹配结果
			if(infoCount>0){
				int pageCount = (int) Math.ceil((double)infoCount/pageSize);
				page = Math.min(pageCount, Math.max(1,page));
				List<NmShopping> shoppings = nmShoppingServer.searchByTypeAndName(typeIdList,sname,(page-1)*pageSize, pageSize);
				map.put("code", 200);
				map.put("msg", "查询成功");
				map.put("pageCount", pageCount);
				map.put("shopCount", infoCount);
				map.put("shoppings", shoppings);
			}else{
				map.put("code", 0);
				map.put("msg", "无相关数据");
			}
		}else{//否则只是调用分类下的全部商品
			int infoCount = nmShoppingServer.countForSearchByTypes(typeIdList);
			//匹配结果大于0，有数据
			if(infoCount>0){
				int pageCount = (int) Math.ceil((double)infoCount/pageSize);
				page = Math.min(pageCount, Math.max(1,page));
				List<NmShopping> shoppings = nmShoppingServer.serarchByTypes(typeIdList,(page-1)*pageSize, pageSize);
				map.put("code", 200);
				map.put("msg", "查询成功");
				map.put("pageCount", pageCount);
				map.put("shopCount", infoCount);
				map.put("shoppings", shoppings);
			}else{
				map.put("code", 0);
				map.put("msg", "无相关数据");
			}
		}
		return map;
	}
}
