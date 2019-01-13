package com.wd.ASFlowerWeb.controller;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wd.ASFlowerWeb.entity.Manager;
import com.wd.ASFlowerWeb.mapper.ManagerMapper;

/**
 * @author 风微轻
 *
 * 2019年1月7日
 *
 */
@Controller
public class Test {
	@Autowired
	private ManagerMapper managerMapper;
	@GetMapping("/admin/test")
	@ResponseBody
	public String test(){
		List<Manager> managerList = managerMapper.findNormalAll(0,1);
		return managerList.toString();
	}
	
	@GetMapping("/admin/test2")
	@ResponseBody
	public String test2(){
		String name = "pyres";
		List<Manager> managerList = managerMapper.findDelManagerByName(name,0,1);
		return managerList.toString();
	}
	
	@GetMapping("/admin/test3")
	@ResponseBody
	public Integer test3(){
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
