package com.wd.ASFlowerWeb.controller.home;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wd.ASFlowerWeb.entity.User;
import com.wd.ASFlowerWeb.service.UserService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author 若尘
 *
 * 2019年1月10日
 *
 */
@Controller
@Slf4j
public class HomeUserInfoController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/home/user/userinfo")
	public String userinfo(){
		
		return "/home/userinfo";
	}
	
	@GetMapping("/home/user/useraccount")
	public String useraccount(){
		return "/home/useraccount";
	}
	
	@GetMapping("/home/user/userAddress")
	public String address(){
		return "/home/address";
	}
	
	@GetMapping("/home/login")
	public String login(){
		return "/home/login";
	}
	
	@GetMapping("/home/register")
	public String register(){
		return "/home/register";
	}
	
	@GetMapping("/home/repassword")
	public String rePassWord(){
		return "/home/repassword";
	}
	
	@GetMapping("/home/findPass")
	public String findPass(){
		//return "/home/find-pass";
		return "/home/find-pass";
	}
	
	@PostMapping("/home/validateLogin")
	@ResponseBody
	public Map<String,Object> validateLogin(HttpServletRequest req){
		Map<String,Object> map = new HashMap<>();
		
		String username = "";
		String password = "";
		boolean checkStatus = true;
		if(req.getParameter("username")!=null){
			username = req.getParameter("username").trim();
			if(!this.validateUsername(username)){
				checkStatus = false;
			}
		}
		if(req.getParameter("userpass")!=null){
			password = req.getParameter("userpass");
			if(!this.validatePassword(password)){
				checkStatus = false;
			}
		}
		User member = userService.getUserByMName(username);
		if(member==null || !member.getPassword().equals(password)){
			checkStatus = false;
		}
		if(!checkStatus){
			map.put("code", 0);
			map.put("msg", "validate fail");
		}else{
			HttpSession sessoin=req.getSession();
			sessoin.setAttribute("member",member);
			map.put("code", 200);
		}
		return map;
	}
	
	
	@PostMapping("/home/validateRegister")
	@ResponseBody
	public Map<String,Object> validateRegister(HttpServletRequest req){
		
		Map<String,Object> map = new HashMap<>();
		
		String username = "";
		String password = "";
		String repassword = "";
		String email = "";
		String captcha = "";
		
		boolean checkStatus = true;
		if(req.getParameter("email")!=null){
			email = req.getParameter("email");
			if(!this.validateEmail(email)){
				checkStatus = false;
			}else{
				if(req.getParameter("captcha")!=null){
					captcha = req.getParameter("captcha");
					if(!this.validateRegCaptcha(req, email, captcha)){
						checkStatus = false;
					}
				}else{
					checkStatus = false;
				}
			}
		}
		
		
		if(req.getParameter("username")!=null){
			username = req.getParameter("username").trim();
			if(!this.validateUsername(username) || isExistUserByMName(username)){
				checkStatus = false;
			}else{
				User member = userService.getUserByMName(username);
				if(member!=null){
					checkStatus = false;
				}
			}
		}
		
		if(req.getParameter("password")!=null){
			password = req.getParameter("password").trim();
			if(!this.validatePassword(password)){
				checkStatus = false;
			}
		}
		if(req.getParameter("repassword")!=null){
			repassword = req.getParameter("repassword").trim();
			if(!(this.validatePassword(repassword) && this.validateRePassword(password, repassword))){
				checkStatus = false;
			}
		}
		
		if(!checkStatus){
			map.put("code",0);
			map.put("msg","register fail");
		}else{
			User u = new User();
			u.setMemberName(username);
			u.setPassword(password);
			u.setEmail(email);
			u.setJoinTime(new Timestamp(System.currentTimeMillis()));
			if(userService.addUser(u)){
				map.put("code",200);
				map.put("msg","register success");
			}else{
				map.put("code",0);
				map.put("msg","register fail");
			}
		}
		return map;
	}
	
	private boolean validateUsername(String username){
		if(username!=null && username.trim().length()>=5 && username.trim().length()<=10){
			return true;
		}
		return false;
	}
	
	private boolean validatePassword(String password){
		if(password == null || password.trim().length()<6 || password.trim().length()>12){
			return false;
		}
		return true;
	}

	private boolean validateRePassword(String password,String repassword){
		return password.equals(repassword);
	}
	
	private boolean validateEmail(String email){
		String REGEX="^\\w+((-\\w+)|(\\.\\w+))*@\\w+(\\.\\w{2,3}){1,3}$";
		Pattern p = Pattern.compile(REGEX);    
		Matcher matcher=p.matcher(email);    
		if(matcher.matches() && userService.getUserByEmail(email)==null){
			return true;
		}
		return false;
	}
	
	private boolean validateRegCaptcha(HttpServletRequest request,String email,String captcha){
		HttpSession session = request.getSession();
		return (session.getAttribute("reg"+email)!=null && session.getAttribute("reg"+email).equals(captcha))?true:false;
	}
	
	private boolean isExistUserByMName(String memberName){
		return userService.getUserByMName(memberName)!=null?true:false;
	}
}
