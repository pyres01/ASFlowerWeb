package com.wd.ASFlowerWeb.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.wd.ASFlowerWeb.entity.ShoppingCart;
/**
 * @author 若尘
 *
 * 2019年3月2日
 *
 */
public interface ShoppingCartMapper {

	//查询用户购物车
	@Select("SELECT * FROM shoppingcart where uid = ${uid} ORDER BY id DESC")
	List<ShoppingCart> getByUid(@Param("uid")Integer uid);
	
	//加入购物车
	@Insert("INSERT INTO shoppingcart(uid,sid,count) values(#{uid},#{sid},#{count})")
	Integer insert(ShoppingCart cart);
	
	//修改购物车购买数
	@Update("UPDATE shoppingcart SET count=${count} where id = ${id}")
	Integer update(@Param("id") Integer id,@Param("count")Integer count);
	
	//删除购物车项
	@Delete("DELETE FROM shoppingcart WHERE id = ${id}")
	Integer delete(@Param("id")Integer id);
	
	//清空个人购物车
	@Delete("DELETE FROM shoppingcart WHERE uid = ${uid}")
	Integer deleteUserAll(@Param("uid")Integer uid);
	
}
