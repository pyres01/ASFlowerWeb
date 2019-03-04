package com.wd.ASFlowerWeb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wd.ASFlowerWeb.entity.NmOrderItem;
import com.wd.ASFlowerWeb.mapper.NmOrderItemMapper;

/**
 * @author 若尘
 *
 * 2019年3月4日
 *
 */
@Service
public class NmOrderItemService {

	@Autowired
	private NmOrderItemMapper mapper;
	
	public Integer save(NmOrderItem item){
		return mapper.insert(item);
	}
}
