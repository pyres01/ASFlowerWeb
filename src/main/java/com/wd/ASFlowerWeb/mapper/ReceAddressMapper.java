package com.wd.ASFlowerWeb.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.wd.ASFlowerWeb.entity.ReceAddress;

/**
 * @author 韦丹
 *
 * 2019年2月25日
 *
 */
public interface ReceAddressMapper {
	
	@Select("SELECT * FROM receAddress where uid = ${uid}")
	List<ReceAddress> receAddresses(@Param("uid")int uid);
	
	@Select("SELECT * FROM receAddress where uid = ${uid} AND def = true LIMIT 1")
	ReceAddress DefReceAddress(@Param("uid")int uid);
	
	@Update("UPDATE receAddress SET receiver = #{receiver},phone=#{phone},address=#{address} WHERE id = #{id}")
	Integer updateById(ReceAddress receAddress);
	
	@Delete("DELETE FROM receAddress where id=${id}")
	Integer delete(@Param("id")int id);
	
	@Insert("INSERT INTO receAddress(uid,receiver,phone,address,def) VALUES(#{uid},#{receiver},#{phone},#{address},#{def})")
	Integer insert(ReceAddress receAddress);
}
