package com.wd.ASFlowerWeb.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.wd.ASFlowerWeb.service.ManagerService;

/**
 * @author 若尘
 *
 * 2019年1月9日
 *
 */
@Controller
public class ManagerController {
	
	@Autowired
	private ManagerService managerService;
	
	@GetMapping("managerNormalIndex")
	public String normalIndex(){
		return "/admin/manager-normal-list";
	}
	
	@GetMapping("managerDelIndex")
	public String delIndex(){
		return "/admin/manager-del-list";
	}
	
}
