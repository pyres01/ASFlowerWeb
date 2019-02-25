package com.wd.ASFlowerWeb.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wd.ASFlowerWeb.entity.ReceAddress;
import com.wd.ASFlowerWeb.mapper.ReceAddressMapper;

/**
 * @author 若尘
 *
 * 2019年2月25日
 *
 */
@Service
public class ReceAddressService {
	
	@Autowired
	private ReceAddressMapper addressMapper;
	
	public List<ReceAddress> getReceAddresses(int uid){
		return addressMapper.receAddresses(uid);
	}
	
	public ReceAddress getDefReceAddress(int uid){
		return addressMapper.DefReceAddress(uid);
	}
	
	public boolean updateById(ReceAddress receAddress){
		return addressMapper.updateById(receAddress) == 1? true:false;
	}
	
	public Integer delete(int id){
		return addressMapper.delete(id);
	}
	
	public boolean insert(ReceAddress receAddress){
		return addressMapper.insert(receAddress) == 1?true:false;
	}
}
