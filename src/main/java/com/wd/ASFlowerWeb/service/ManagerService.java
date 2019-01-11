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
	
	public Manager getManagerByName(String name){
		return managerMapper.getNormalManagerByName(name);
	}
	
	public List<Manager> findDelManagerByName(String name,Integer limitStart,Integer limitSize){	
		return managerMapper.findDelManagerByName(name,limitStart,limitSize);
	}
}
