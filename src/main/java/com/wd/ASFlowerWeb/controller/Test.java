package com.wd.ASFlowerWeb.controller;

import java.io.FileNotFoundException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wd.ASFlowerWeb.entity.Manager;
import com.wd.ASFlowerWeb.entity.User;
import com.wd.ASFlowerWeb.mapper.ManagerMapper;
import com.wd.ASFlowerWeb.service.UserService;
import com.wd.ASFlowerWeb.service.util.MailService;

import lombok.extern.slf4j.Slf4j;



/**
 * @author 韦丹
 *
 * 2019年1月7日
 *
 */
@Controller
@Slf4j
public class Test {
	
	@Autowired
    private MailService mailService;
	@Autowired
    private UserService userService;
	@Autowired
	private ManagerMapper managerMapper;
	@RequestMapping("/test")
	@ResponseBody
	private Map<String,Object> test(HttpServletRequest request,String email) throws FileNotFoundException{
		
		//String path = ClassUtils.getDefaultClassLoader().getResource("").getPath()+"static/";
		/*List<Integer> idList =  new ArrayList<Integer>();
		idList.add(1);
		idList.add(2);
		idList.add(3);
		System.out.println(StringUtils.strip(idList.toString(),"[]"));*/
		
		Map<String,Object> map = new HashMap<>();
		map.put("code", 200);
		map.put("msg", "");
		User user = userService.getUserByEmail(email);
		map.put("data", user);
		return map;
		
		
		
	}
	
	@GetMapping("/admin/test2")
	@ResponseBody
	private String test2(){
		String name = "pyres";
		List<Manager> managerList = managerMapper.findDelManagerByName(name,0,1);
		return managerList.toString();
	}
	
	@GetMapping("/admin/test3")
	@ResponseBody
	private Integer test3(){
		Manager manager = new Manager();
		manager.setName("test1");
		manager.setPassword("123456");
		manager.setBirthday(new Timestamp(System.currentTimeMillis()));
		return managerMapper.insert(manager);
	}
	
	@GetMapping("/admin/test4")
	@ResponseBody
	public Timestamp test4(){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		String date = df.format(new Date());// new Date()为获取当前系统时间，也可使用当前时间戳


		Timestamp time= new Timestamp(System.currentTimeMillis());//获取系统当前时间 
		return  time;
	}
	
}
