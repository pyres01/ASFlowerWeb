package com.wd.ASFlowerWeb.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wd.ASFlowerWeb.entity.NmOrder;
import com.wd.ASFlowerWeb.mapper.NmOrderMapper;

/**
 * @author 韦丹
 *
 * 2019年3月4日
 *
 */
@Service
public class NmOrderService {
	
	@Autowired
	private NmOrderMapper nmOrderMapper;

	public Integer save(NmOrder nmOrder){
		nmOrderMapper.insert(nmOrder);
		return nmOrder.getId();
	}
	
	public NmOrder getULastOrder(Integer uid){
		return nmOrderMapper.getULastOrder(uid);
	}
	
	public boolean updateStatus(Integer id,Integer status){
		return nmOrderMapper.updateStatus(id, status) == 1?true:false;
	}
	
	
}
