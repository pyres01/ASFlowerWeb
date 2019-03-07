package com.wd.ASFlowerWeb.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wd.ASFlowerWeb.entity.NmShopping;
import com.wd.ASFlowerWeb.mapper.NmShoppingMapper;

/**
 * @author 韦丹
 *
 * 2019年2月20日
 *
 */
@Service
public class NmShoppingService {
	@Autowired
	private NmShoppingMapper nmShoppingMapper;
	
	/*
	 * 
	 */
	public List<NmShopping> serarchByTypes(List<Integer> typeIdList,int start,int num){
		return nmShoppingMapper.searchByTypes(StringUtils.strip(typeIdList.toString(),"[]"), start, num);
	}
	
	public int countForSearchByTypes(List<Integer> typeIdList){
		return nmShoppingMapper.countSearchByTypes(StringUtils.strip(typeIdList.toString(),"[]"));
	}
	
	public List<NmShopping> searchByTypeAndName(List<Integer> typeIdList,String shoppingName,int start,int num){
		return nmShoppingMapper.searchByTypeAndName(StringUtils.strip(typeIdList.toString(),"[]"), shoppingName, start, num);
	}
	
	public int countForSearchByTypeAndName(List<Integer> typeIdList,String shoppingName){
		return nmShoppingMapper.countSearchByTypeAndName(StringUtils.strip(typeIdList.toString(),"[]"), shoppingName);
	}
	
	public List<NmShopping> searchByType(int typeId,int start,int num){
		return nmShoppingMapper.searchByType(typeId, start, num);
	}
	
	public int countForSearchByType(int typeId){
		return nmShoppingMapper.countSearchByType(typeId);
	}
	
	public NmShopping getByTypeAndId(int typeId,int id){
		return nmShoppingMapper.getByTypeAndId(typeId,id);
	}
	
	public List<NmShopping> getByType(int typeId,int start,int num){
		return nmShoppingMapper.searchByType(typeId, start, num);
	}
	
	public NmShopping getById(Integer id){
		return nmShoppingMapper.getById(id);
	}
	
	public boolean insert(NmShopping shopping){
		return nmShoppingMapper.insert(shopping)==1?true:false;
	}
	
	public boolean update(NmShopping shopping){
		return nmShoppingMapper.update(shopping) == 1 ?true:false;
	}
	
	public boolean delete(int sid){
		return nmShoppingMapper.delete(sid) == 1?true:false;
	}
	
}
