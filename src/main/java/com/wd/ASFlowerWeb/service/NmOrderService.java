package com.wd.ASFlowerWeb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wd.ASFlowerWeb.entity.NmOrder;
import com.wd.ASFlowerWeb.mapper.NmOrderMapper;

/**
 * @author 若尘
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
}
