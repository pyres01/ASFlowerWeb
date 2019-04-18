package com.wd.ASFlowerWeb.controller.admin;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wd.ASFlowerWeb.entity.Manager;
import com.wd.ASFlowerWeb.service.ManagerService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author 韦丹
 *
 * 2019年1月7日
 * 
 * @desc 后台登录控制器
 *
 */
@Controller
@Slf4j
public class LoginController {
	
	@Autowired
	private ManagerService managerService;	//管理员service
	
	
	/**
	 * @desc 显示登陆页面
	 * @return
	 */
	
	@GetMapping("/admin/login")
	public String login(){
		return "admin/login";
	}
	
	
	/**
	 * @desc  验证登录，post登陆，前台拿数据，验证是否成功，此处未用token
	 * @param request
	 * @return 登陆状态
	 */
	@PostMapping("/admin/toLogin")
	@ResponseBody
	public Map<String,Object> toLogin(HttpServletRequest request){
		
		Map<String,Object> map = new HashMap<>();
		map.put("code", 200);
		map.put("msg", "登录成功!");
		
		/*
		 * 从前台获得数据，用户名和密码，此处未用token和验证码
		 */
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		//根据用户名获取该用户信息
		Manager manager = managerService.getManagerByName(username);
		
		//如果用户存在且密码正确，则保存用户状态到session
		if(manager!=null && manager.getPassword().equals(DigestUtils.md5DigestAsHex(password.getBytes()))){
			HttpSession sessoin=request.getSession();
			sessoin.setAttribute("manager",manager);
		}else{
			map.put("code", 0);
			map.put("msg", "登陆失败，用户名或密码错误！");
		}
		
		return map;
	}
	
	/**
	 * @desc 后台退出
	 * @param request
	 * @return 退出后跳到登录页
	 */
	@GetMapping("/admin/logout")
	public String logout(HttpServletRequest request){
		
		HttpSession sessoin=request.getSession();
		sessoin.removeAttribute("manager");
		return "admin/login";
	}
}
