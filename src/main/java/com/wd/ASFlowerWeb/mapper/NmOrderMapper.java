package com.wd.ASFlowerWeb.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.mapping.StatementType;

import com.wd.ASFlowerWeb.entity.NmOrder;

/**
 * @author 若尘
 *
 * 2019年3月2日
 *
 */
public interface NmOrderMapper {

	@Insert("INSERT INTO nmorder(uid,createTime,serialNo,total,receiver,phone,address) VALUES(#{uid},#{createTime},#{serialNo},#{total},#{receiver},#{phone},#{address})")
	@SelectKey(keyColumn="id",keyProperty="id",resultType=Integer.class,before=false,statement="select last_insert_id()")
	Integer insert(NmOrder nmOrder);
	
	@Select("SELECT * FROM nmorder WHERE uid = ${uid}")
	List<NmOrder> getByUid(@Param("uid")Integer uid);
	
	@Select("SELECT * FROM nmorder WHRE uid = ${uid} AND status = 0 ORDER BY id LIMIT 1")
	NmOrder getULastOrder(@Param("uid")Integer uid);
}
