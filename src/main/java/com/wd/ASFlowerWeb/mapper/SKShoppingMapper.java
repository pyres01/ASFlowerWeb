package com.wd.ASFlowerWeb.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.wd.ASFlowerWeb.entity.SKShopping;


/**
 * @author 韦丹
 *
 * 2019年2月20日
 *
 */
public interface SKShoppingMapper {

	@Select("SELECT * FROM skshopping WHERE id =${id}")
	SKShopping getById(@Param("id") Integer id);
	
	@Insert("INSERT INTO skshopping(shoppingName,introduction,shoppingImg,asPrice,nmPrice,store,onShelveTime,skTime,skTimeEnd,skPrice,isSale,shoppingDetail) VALUES(#{shoppingName},#{introduction},#{shoppingImg},#{asPrice},#{nmPrice},#{store},#{onShelveTime},#{skTime},#{skTimeEnd},#{skPrice},#{isSale},#{shoppingDetail})")
	Integer insert(SKShopping shopping);
	
	@Update("UPDATE skshopping SET shoppingName = #{shoppingName},introduction=#{introduction},shoppingImg=#{shoppingImg},asPrice=#{asPrice},nmPrice=#{nmPrice},store=#{store},onShelveTime=#{onShelveTime},skTime=#{skTime},skTimeEnd=#{skTimeEnd},skPrice=#{skPrice},isSale=#{isSale},shoppingDetail=#{shoppingDetail} WHERE id = #{id}")
	Integer update(SKShopping shopping);
	
	@Delete("DELETE FROM skshopping where id = ${id}")
	Integer delete();
	
	@Select("SELECT COUNT(*) FROM skshopping")
	Integer count();
	
	@Select("SELECT * FROM skshopping WHERE skTime >= #{skTime} AND skTimeEnd<= #{skTimeEnd} order by skTime limit ${start},${num}")
	List<SKShopping> searchBySKT(@Param("skTime") String skTime,@Param("skTimeEnd") String skTimeEnd,@Param("start") int start,@Param("num") int num);
	
	@Select("SELECT * FROM skshopping WHERE skTime >= #{skTime} AND skTimeEnd <= #{skTimeEnd} AND shoppingName LIKE CONCAT('%',#{shoppingName},'%') order by skTime limit ${start},${num}")
	List<SKShopping> searchBySKTN(@Param("skTime") String skTime,@Param("skTimeEnd") String skTimeEnd,@Param("shoppingName") String shoppingName,@Param("start") int start,@Param("num") int num);
	
	@Select("SELECT COUNT(*) FROM skshopping where skTime >= #{skTime} AND skTimeEnd <= #{skTimeEnd}")
	Integer countSearchBySKT(@Param("skTime")String skTime,@Param("skTimeEnd")String skTimeEnd);
	
	@Select("SELECT COUNT(*) FROM skshopping WHERE skTime >= #{skTime} AND skTimeEnd <= #{skTimeEnd} AND shoppingName LIKE CONCAT('%',#{shoppingName},'%')")
	Integer countSearchBySKTN(@Param("skTime")String skTime,@Param("skTimeEnd")String skTimeEnd,@Param("shoppingName") String shoppingName);
	
	@Select("SELECT * FROM skshopping WHERE skTime >= #{skTime} AND skTimeEnd <= #{skTimeEnd} AND isSale=true AND onShelveTime >= #{onShelveTime} order by skTime limit ${start},${num}")
	List<SKShopping> getSaleShopping(@Param("skTime") String skTime,@Param("skTimeEnd") String skTimeEnd,@Param("onShelveTime")String onShelveTime,@Param("start") int start,@Param("num") int num);
	
	@Select("SELECT COUNT(*) FROM skshopping WHERE skTime >= #{skTime} AND skTimeEnd <= #{skTimeEnd} AND isSale=true AND onShelveTime >= #{onShelveTime}")
	Integer countSaleShopping(@Param("skTime") String skTime,@Param("skTimeEnd")String skTimeEnd,@Param("onShelveTime")String onShelveTime);
}
