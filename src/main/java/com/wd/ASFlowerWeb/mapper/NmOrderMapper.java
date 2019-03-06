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

	@Insert("INSERT INTO nmorder(uid,createTime,serialNo,total) VALUES(#{uid},#{createTime},#{serialNo},#{total})")
	@SelectKey(keyColumn="id",keyProperty="id",resultType=Integer.class,before=false,statement="select last_insert_id()")
	Integer insert(NmOrder nmOrder);
	
	@Select("SELECT * FROM nmorder WHERE uid = ${uid}")
	List<NmOrder> getByUid(@Param("uid")Integer uid);
	
	@Insert("UPDATE nmorder SET receaddress_id = ${receaddress_id}")
	Integer setReceiver(@Param("receaddress_id")Integer receaddress_id);
}
