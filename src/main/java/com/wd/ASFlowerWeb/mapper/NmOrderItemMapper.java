package com.wd.ASFlowerWeb.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.wd.ASFlowerWeb.entity.NmOrderItem;

/**
 * @author 韦丹
 *
 * 2019年3月2日
 *
 */

public interface NmOrderItemMapper {

	@Select("SELECT * FROM nmorderitem WHERE oid = ${oid}")
	List<NmOrderItem> getByOid(@Param("oid")Integer oid);
	
	@Insert("INSERT INTO nmorderitem(oid,sid,serialNo,shoppingName,introduction,shoppingImg,price,count,subTotal,remark,status) VALUES(#{oid},#{sid},#{serialNo},#{shoppingName},#{introduction},#{shoppingImg},#{price},#{count},#{subTotal},#{ramark},#{status})")
	Integer insert(NmOrderItem nmOrderItem);
	
	@Update("UPDATE nmorderitem SET remark = #{remark} WHERE id = ${id}")
	Integer setReamrk(@Param("id")Integer id,@Param("remark")String remark);
	
	@Update("UPDATE nmorderitem SET status = ${status} WHERE id = ${id}")
	Integer updateStatus(Integer id, Integer status);
}
