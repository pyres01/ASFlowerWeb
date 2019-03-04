package com.wd.ASFlowerWeb.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.wd.ASFlowerWeb.entity.NmOrderItem;

/**
 * @author 若尘
 *
 * 2019年3月2日
 *
 */

public interface NmOrderItemMapper {

	@Select("SELECT * FROM nmorderitem WHERE oid = ${oid}")
	List<NmOrderItem> getByOid(@Param("oid")Integer oid);
	
	@Insert("INSERT INTO nmorderitem(oid,sid,shoppingName,introduction,shoppingImg,price,count,subTotal) VALUES(#{oid},#{sid},#{shoppingName},#{introduction},#{shoppingImg},#{price},#{count},#{subTotal})")
	Integer insert(NmOrderItem nmOrderItem);
	
	@Update("UPDATE nmorderitem SET remark = #{remark}")
	Integer setReamrk(@Param("remark")String remark);
}
