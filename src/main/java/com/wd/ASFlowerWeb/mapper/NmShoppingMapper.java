package com.wd.ASFlowerWeb.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
 
import com.wd.ASFlowerWeb.entity.NmShopping;

/**
 * @author 若尘
 *
 * 2019年2月20日
 *
 */
public interface NmShoppingMapper {
	
	@Select("SELECT * FROM nmshopping WHERE id =${id} FOR UPDATE")
	NmShopping getById(@Param("id") Integer id);
	
	@Select("SELECT * FROM nmshopping WHERE typeId=${typeId} AND id =${id}")
	NmShopping getByTypeAndId(@Param("typeId") Integer typeId,@Param("id") Integer id);
	
	@Insert("INSERT INTO nmshopping(typeId,shoppingName,introduction,shoppingImg,asPrice,nmPrice,store,onShelveTime,isSale,shoppingDetail) VALUES(#{typeId},#{shoppingName},#{introduction},#{shoppingImg},#{asPrice},#{nmPrice},#{store},#{onShelveTime},#{isSale},#{shoppingDetail})")
	Integer insert(NmShopping shopping);
	
	@Update("UPDATE nmshopping SET typeId=#{typeId}, shoppingName = #{shoppingName},introduction=#{introduction},shoppingImg=#{shoppingImg},asPrice=#{asPrice},nmPrice=#{nmPrice},store=#{store},onShelveTime=#{onShelveTime},isSale=#{isSale},shoppingDetail=#{shoppingDetail} WHERE id = #{id}")
	Integer update(NmShopping shopping);
	
	@Delete("DELETE FROM nmshopping WHERE id = ${id}")
	Integer delete(@Param("id")int id);
	
	@Select("SELECT COUNT(*) FROM nmshopping")
	Integer count();
	
	@Select("SELECT COUNT(*) FROM nmshopping WHERE typeId = ${typeId}")
	Integer countSearchByType(@Param("typeId")Integer typeId);
	
	@Select("SELECT COUNT(*) FROM nmshopping WHERE typeId in (${typeIdList})")
	Integer countSearchByTypes(@Param("typeIdList")String typeIdList);
	
	@Select("SELECT COUNT(*) FROM nmshopping WHERE typeId in (${typeIdList}) AND shoppingName LIKE CONCAT('%',#{shoppingName},'%')")
	Integer countSearchByTypeAndName(@Param("typeIdList")String typeIdList,@Param("shoppingName") String shoppingName);
	
	@Select("SELECT * FROM nmshopping WHERE typeId = ${typeId} ORDER BY id desc limit ${start},${num}")
	List<NmShopping> searchByType(@Param("typeId") Integer typeId,@Param("start")int start,@Param("num")int num);
	
	@Select("SELECT * FROM nmshopping WHERE typeId in (${typeIdList}) AND shoppingName LIKE CONCAT('%',#{shoppingName},'%') ORDER BY id desc limit ${start},${num}")
	List<NmShopping> searchByTypeAndName(@Param("typeIdList") String typeIdList,@Param("shoppingName") String shoppingName,@Param("start")int start,@Param("num")int num);
	
	@Select("SELECT * FROM nmshopping WHERE typeId in (${typeIdList}) ORDER BY id desc limit ${start},${num}")
	List<NmShopping> searchByTypes(@Param("typeIdList") String typeIdList,@Param("start")int start,@Param("num")int num);
}
