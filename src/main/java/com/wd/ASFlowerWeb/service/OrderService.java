package com.wd.ASFlowerWeb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author 若尘
 *
 * 2019年3月2日
 *
 */
@Transactional
@Service
public class OrderService {

	@Autowired
	private OrderService service;
	
	public void createOrder(){
		
	}
	
	public void updateOrder(){
		
	}
	
	public boolean delOrder(){
		return false;
	}
	public void createOrderItem(){
		
	}
	
	public boolean delOrderItem(){
		return false;
	}
}
