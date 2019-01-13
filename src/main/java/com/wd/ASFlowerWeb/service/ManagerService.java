package com.wd.ASFlowerWeb.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wd.ASFlowerWeb.entity.Manager;
import com.wd.ASFlowerWeb.mapper.ManagerMapper;

import io.lettuce.core.dynamic.annotation.Param;

/**
 * @author 风微轻
 *
 * 2019年1月6日
 *
 */
@Service
public class ManagerService {
	@Autowired
	private ManagerMapper managerMapper;
	
	
	public Manager getManagerById(Integer id){
		return managerMapper.getManagerById(id);
	}
	
	public Manager getManagerByName(String name){
		return managerMapper.getNormalManagerByName(name);
	}
	
	public List<Manager> findDelManagerByName(String name,Integer limitStart,Integer limitSize){	
		return managerMapper.findDelManagerByName(name,limitStart,limitSize);
	}
	
	public Boolean addManager(Manager manager){
		return managerMapper.insert(manager) == 1?true:false;
	}
	
	public Integer countNormalAll(){
		return managerMapper.countNormalAll();
	}
	public List<Manager> findNormalManagerAll(Integer limitStart, Integer limitSize){
		return managerMapper.findNormalAll(limitStart, limitSize);
	}
	
	public Boolean delManager(Integer managerId){
		return managerMapper.DeleteManager(managerId,1) == 1?true:false;
	}
	
	public Boolean recoverDelManager(Integer managerId){
		return managerMapper.DeleteManager(managerId,0) == 1?true:false;
	}
	
	public Boolean relDelManager(Integer managerId){
		return managerMapper.relDeleteManager(managerId) == 1?true:false;
	}
}
