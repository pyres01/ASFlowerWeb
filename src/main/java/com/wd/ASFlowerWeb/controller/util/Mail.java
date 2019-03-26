package com.wd.ASFlowerWeb.controller.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wd.ASFlowerWeb.entity.User;
import com.wd.ASFlowerWeb.service.UserService;
import com.wd.ASFlowerWeb.service.util.MailService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author 韦丹
 *
 * 2019年2月23日
 *
 */
@Controller
@Slf4j
public class Mail {

	@Autowired
    private MailService mailService;
	@Autowired UserService userService;
	
	
    @PostMapping("/sendCheckCode")
    @ResponseBody
    public Map<String,Object> getCheckCode(HttpServletRequest request,String type,String email){
    	Map<String,Object> map = new HashMap<String, Object>();
    	
    	String[] typeArr = {"register","findPass"};
    	Arrays.sort(typeArr);
    	if(Arrays.binarySearch(typeArr, type)<0 || email == null || email.trim().length()==0){
    		//参数无效
    		map.put("code",10004);
    		map.put("msg", "PARAMS_IS_INVALID");
    		return map;
    	}
    	
    	if(this.validate(email)){
    		if(type.equals("register")){
    			User user = userService.getUserByEmail(email);
    			if(user == null){
    				String checkCode = String.valueOf(new Random().nextInt(899999) + 100000);
        			String message = "欢迎您加入爱尚，您的注册验证码为："+checkCode+"，有效时间30分钟！请勿告诉他人，谨防受骗。";
        			try {
        			    mailService.sendSimpleMail(email, "注册验证码", message);
        			    HttpSession session = request.getSession();
        			    session.setAttribute("reg"+email, checkCode);
        			    map.put("code", 200);
        	        	map.put("msg", "send success");
        			}catch (Exception e){
        				//内部接口调用错误
        				map.put("code", 60001);
        				map.put("msg","NTERFACE_INNER_INVOKE_ERROR");
        			}
    			}else{
    				//用户已存在
    				map.put("code", 20005);
    				map.put("msg", "member exist");
    			}
    		}else{
    			User user = userService.getUserByEmail(email);
    			if(user!= null){
    				String checkCode = String.valueOf(new Random().nextInt(899999) + 100000);
        			String message = "您的验证码为："+checkCode+"，有效时间30分钟！请勿告诉他人，谨防受骗。";
        			try {
        			    mailService.sendSimpleMail(email, "找回密码", message);
        			    HttpSession session = request.getSession();
        			    session.setAttribute("fpass"+email, checkCode);
        			    map.put("code", 200);
        	        	map.put("msg", "send success");
        			}catch (Exception e){
        				//内部接口调用错误
        				map.put("code", 60001);
        				map.put("msg","NTERFACE_INNER_INVOKE_ERROR");
        			}
    			}
    		}
    	}else{
    		//参数无效
    		map.put("code",10004);
    		map.put("code", "PARAMS_IS_INVALID");
    	}
        return map;
    }
    
    private boolean validate(String email){
		String REGEX="^\\w+((-\\w+)|(\\.\\w+))*@\\w+(\\.\\w{2,3}){1,3}$";
		Pattern p = Pattern.compile(REGEX);    
		Matcher matcher=p.matcher(email);    
		return matcher.matches();
    }

}
