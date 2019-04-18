package com.wd.ASFlowerWeb.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wd.ASFlowerWeb.entity.NmOrderItem;
import com.wd.ASFlowerWeb.mapper.NmOrderItemMapper;

/**
 * @author 韦丹
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
	
	public List<NmOrderItem> getByOid(Integer oid){
		return mapper.getByOid(oid);
	}
	
	public boolean setReamrk(Integer id,String remark){
		return mapper.setReamrk(id, remark) == 1?true:false;
	}
	
	public boolean updateStatus(Integer id,Integer status){
		return mapper.updateStatus(id, status) == 1?true:false;
	}
	
}
