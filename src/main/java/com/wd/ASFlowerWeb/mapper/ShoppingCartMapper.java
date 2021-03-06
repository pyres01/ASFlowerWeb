package com.wd.ASFlowerWeb.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.wd.ASFlowerWeb.entity.ShoppingCart;
/**
 * @author 韦丹
 *
 * 2019年3月2日
 *
 */
public interface ShoppingCartMapper {

	//查询用户购物车
	@Select("SELECT * FROM shoppingcart where uid = ${uid} ORDER BY id DESC")
	List<ShoppingCart> getByUid(@Param("uid")Integer uid);
	
	//查询用户购物车
	@Select("SELECT * FROM shoppingcart where uid = ${uid} AND id =${id}")
	ShoppingCart getByid(@Param("uid")Integer uid,@Param("id")Integer id);
	
	//查询用户某商品的购物车
	@Select("SELECT * FROM shoppingcart where uid = ${uid} AND sid =${sid} limit 1")
	ShoppingCart getByUidASid(@Param("uid")Integer uid,@Param("sid")Integer sid);
		
	//查询用户购物车id列表
	@Select("SELECT id FROM shoppingcart where uid = ${uid} ORDER BY id DESC")
	List<Integer> getIdsByUid(@Param("uid")Integer uid);
		
	//加入购物车
	@Insert("INSERT INTO shoppingcart(uid,sid,count) values(#{uid},#{sid},#{count})")
	Integer insert(ShoppingCart cart);
	
	//修改购物车购买数
	@Update("UPDATE shoppingcart SET count=${count} where id = ${id}")
	Integer update(@Param("id") Integer id,@Param("count")Integer count);
	
	//根据id删除购物车项
	@Delete("DELETE FROM shoppingcart WHERE id = ${id}")
	Integer delete(@Param("id")Integer id);
	
	//根据商品id删除购物车项
	@Delete("DELETE FROM shoppingcart WHERE uid = ${uid} AND sid=${sid}")
	Integer deleteBySid(@Param("uid")Integer uid,@Param("sid")Integer sid);
	
	//清空个人购物车
	@Delete("DELETE FROM shoppingcart WHERE uid = ${uid}")
	Integer deleteUserAll(@Param("uid")Integer uid);
	
}
