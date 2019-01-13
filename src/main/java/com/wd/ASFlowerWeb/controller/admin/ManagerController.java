package com.wd.ASFlowerWeb.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.wd.ASFlowerWeb.entity.Manager;
import com.wd.ASFlowerWeb.service.ManagerService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author 若尘
 *
 * 2019年1月9日
 *
 */
@Controller
@Slf4j
public class ManagerController {
	
	@Autowired
	private ManagerService managerService;
	
	@GetMapping("/admin/managerNormalIndex")
	public String normalIndex(){
		return "/admin/manager-normal-list";
	}
	
	@GetMapping("/admin/managerDelIndex")
	public String delIndex(){
		return "/admin/manager-del-list";
	}
	
	@RequestMapping("/admin/editManager")
	public ModelAndView addManager(HttpServletRequest request){
		
		/**
		 * op=null或者未设置，表示加载新增管理员页面，并置op为0，实现新增通知，ajax传入op为2实现新增
		 * op=1表示加载修改管理员页面，并置op为1，通知前端获取需修改管理员数据，实现修改通知，ajax中传入op为3实现修改
		 * op=2  实现新增
		 * op=3实现修改
		 */
		ModelAndView modelAndView = null;
		if(request.getParameter("op")==null){
			//加载新增管理员页面
			modelAndView = new ModelAndView("/admin/manager-normal-edit");
			modelAndView.addObject("op",0);
		}else{
			
			Integer op = Integer.valueOf(request.getParameter("op"));
			
			if(op == 1){
				//加载修改管理员页面
				modelAndView = new ModelAndView("/admin/manager-normal-edit");
				
				Integer id = 0;
				if(request.getParameter("id")!=null){
					id = Math.max(id,Integer.valueOf(request.getParameter("id")));
				}
				modelAndView.addObject("op",1);
				modelAndView.addObject("id",id);
			}else if(op==2){
				//实现新增
				modelAndView = new ModelAndView(new MappingJackson2JsonView());
				Map<String,Object> map = new HashMap<>();
				map.put("code", 200);
				map.put("msg", "add success");
				modelAndView.addObject(map);
			}else if(op==3){
				//实现修改
				modelAndView = new ModelAndView(new MappingJackson2JsonView());
				Map<String,Object> map = new HashMap<>();
				map.put("code", 200);
				map.put("msg", "update success");
				modelAndView.addObject(map);
			}else{
				modelAndView = new ModelAndView("/admin/manager-normal-edit");
				modelAndView.addObject("op",0);
			}
			
		}
		return modelAndView;
	}
	
	@RequestMapping("/admin/loadManagerList")
	@ResponseBody
	public Map<String,Object> loadManagerList(HttpServletRequest request){
		Map<String,Object> map = new HashMap<>();
		map.put("code", 200);
		map.put("msg", "success");
		if(request.getParameter("op") !=null && request.getParameter("op").equals("search")){
			
		}else{
			Integer managerCount = managerService.countNormalAll();
			List<Manager> managerList = managerService.findNormalManagerAll(0, 10);
			map.put("managerCount", managerCount);
			map.put("managerList", managerList);
		}
		return map;
	}
	
	@RequestMapping("/admin/loadManager")
	@ResponseBody
	public Map<String,Object> loadManager(HttpServletRequest request){
		Map<String,Object> map = new HashMap<>();
		map.put("code", 200);
		map.put("msg", "load success");
		Integer id = 0;
		if(request.getParameter("id")!=null){
			id = Integer.valueOf(request.getParameter("id"));
		}
		if(id<=0){
			map.put("code", 0);
			map.put("msg", "load filed");
		}else{
			Manager updateManager = managerService.getManagerById(id);
			map.put("updateManager", updateManager);
		}
		return map;
	}
	
	@PostMapping("/admin/delManager")
	@ResponseBody
	public Map<String,Object> delManager(HttpServletRequest request){
		Integer id = Integer.valueOf(request.getParameter("id"));
		Map<String,Object> map = new HashMap<>();
		map.put("code", 200);
		map.put("msg", "delete success");
		log.info(id.toString());
		if(id==null ||!managerService.delManager(id)){
			map.put("code", 0);
			map.put("msg", "delete failed");
		}
		return map;
		
	}
	
	@PostMapping("/admin/recoverDelManager")
	@ResponseBody
	public Map<String,Object> recoverDelManager(HttpServletRequest request){
		Integer id = Integer.valueOf(request.getParameter("id"));
		Map<String,Object> map = new HashMap<>();
		map.put("code", 200);
		map.put("msg", "delete success");
		log.info(id.toString());
		if(id==null ||!managerService.recoverDelManager(id)){
			map.put("code", 0);
			map.put("msg", "recover failed");
		}
		return map;
		
	}
	
	@PostMapping("/admin/relDelManager")
	@ResponseBody
	public Map<String,Object> relDelManager(HttpServletRequest request){
		Integer id = Integer.valueOf(request.getParameter("id"));
		Map<String,Object> map = new HashMap<>();
		map.put("code", 200);
		map.put("msg", "delete success");
		log.info(id.toString());
		if(id==null ||!managerService.relDelManager(id)){
			map.put("code", 0);
			map.put("msg", "really delete failed");
		}
		return map;
		
	}
	
	@RequestMapping("/admin/repassword")
	public ModelAndView repassword(HttpServletRequest request){
		ModelAndView modelAndView = new ModelAndView("/admin/manager-repassword");
		
		String op = "s";
		if(request.getParameter("op")!=null){
			op = request.getParameter("op");
		}
		if(op.equals("s")){//s为显示页面，u为修改密码操作
			Integer id = 0;
			if(request.getParameter("id")!=null){
				id = Integer.valueOf(request.getParameter("id"));
			}
			modelAndView.addObject("op",op);
			modelAndView.addObject("id",id);
		}else if(op.equals("u")){
			
		}
		
		
		
		return modelAndView;
	}
}
