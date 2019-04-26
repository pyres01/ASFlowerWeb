package com.wd.ASFlowerWeb.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wd.ASFlowerWeb.entity.User;
import com.wd.ASFlowerWeb.mapper.UserMapper;

/**
 * @author 韦丹
 *
 * 2019年1月6日
 *
 */
@Service
public class UserService {
	@Autowired
	private UserMapper userMapper;
	
	public List<User> getSearchListByJT(String startTime,String endTime,Integer start,Integer pageSize){
		return userMapper.searchUsersByJT(startTime, endTime, start, pageSize);
	}
	
	public Integer getSearchListCountByJT(String startTime,String endTime){
		return userMapper.countSearchUsersByJT(startTime, endTime);
	}
	public List<User> getSearchListByJTN(String startTime,String endTime,String username,Integer start,Integer pageSize){
		return userMapper.searchUsersByJTN(startTime, endTime,username,start, pageSize);
	}
	
	public Integer getSearchListCountByJTN(String startTime,String endTime,String memberName){
		return userMapper.countSearchUsersByJTN(startTime, endTime,memberName);
	}
	
	public boolean setDelete(Integer id){
		return userMapper.setDelete(id) == 1?true:false;
	}
	
	public boolean delete(Integer id){
		return userMapper.delete(id) == 1?true:false;
	}
	
	public boolean addUser(User user){
		return userMapper.insert(user)==1?true:false;
	}
	
	public User getUserById(Integer id){
		return userMapper.getUserById(id);
	}
	
	public Boolean updateUser(User user){
		return userMapper.update(user)==1?true:false;
	}
	
	public User getUserByMName(String memberName){
		return userMapper.getUserByMName(memberName);
	}
	
	public User getUserByEmail(String email){
		return userMapper.getUserByEmail(email);
	}
	
	public boolean stopUser(Integer id){
		return userMapper.stopUser(id)==1?true:false;
	}

	public boolean useUser(Integer id) {
		return userMapper.useUser(id)==1?true:false;
	}
}
