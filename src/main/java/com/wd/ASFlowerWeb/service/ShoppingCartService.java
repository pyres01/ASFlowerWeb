package com.wd.ASFlowerWeb.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wd.ASFlowerWeb.entity.ShoppingCart;
import com.wd.ASFlowerWeb.mapper.ShoppingCartMapper;

/**
 * @author 若尘
 *
 * 2019年3月2日
 *
 */
@Service
public class ShoppingCartService {

	@Autowired
	private ShoppingCartMapper mapper;
	
	//查询用户购物车
	public List<ShoppingCart> getByUid(Integer uid){
		return mapper.getByUid(uid);
	}
	
	//加入购物车
	public boolean insert(ShoppingCart cart){
		return mapper.insert(cart) == 1?true:false;
	}
	
	//修改购物车购买数
	public boolean update(Integer id,Integer count){
		return mapper.update(id,count) == 1?true:false;
	}
	
	//删除购物车项
	public boolean delete(Integer id){
		return mapper.delete(id) == 1?true:false;
	}
	
	//清空个人购物车
	public boolean deleteUserAll(Integer uid){
		return mapper.deleteUserAll(uid) >0?true:false;
	}
}
