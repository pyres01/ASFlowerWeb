package com.wd.ASFlowerWeb.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wd.ASFlowerWeb.entity.ShoppingCart;
import com.wd.ASFlowerWeb.mapper.ShoppingCartMapper;

/**
 * @author 韦丹
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
	
	//根据用户Id查询购物车
	public List<Integer> getIdsByUid(Integer uid){
		return mapper.getIdsByUid(uid);
	}
	//查询某用户商品已存在购物车
	public ShoppingCart getByUidASid(Integer uid,Integer sid){
		return mapper.getByUidASid(uid, sid);
	}
	
	public ShoppingCart getByid(Integer uid,Integer id){
		return mapper.getByid(uid,id);
	}
	
	//加入购物车
	public boolean insert(ShoppingCart cart){
		return mapper.insert(cart) == 1?true:false;
	}
	
	//修改购物车购买数
	public boolean update(Integer id,Integer count){
		return mapper.update(id,count) == 1?true:false;
	}
	
	//根据id删除购物车项
	public boolean delete(Integer id){
		return mapper.delete(id) == 1?true:false;
	}
	
	
	public boolean deleteBySid(Integer uid,Integer sid){
		return mapper.deleteBySid(uid,sid) == 1?true:false;
	}
	
	//清空个人购物车
	public boolean deleteUserAll(Integer uid){
		return mapper.deleteUserAll(uid) >0?true:false;
	}
}
