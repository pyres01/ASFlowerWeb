package com.wd.ASFlowerWeb.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.wd.ASFlowerWeb.entity.User;


/**
 * @author 韦丹
 *
 * 2019年1月6日
 *
 */
public interface UserMapper {
	
	@Select("SELECT * FROM user WHERE id =${id}")
	User getUserById(@Param("id") Integer id);
	
	@Select("SELECT * FROM user WHERE memberName = #{memberName}")
	User getUserByMName(@Param("memberName")String memberName);
	
	@Select("SELECT * FROM user WHERE email = #{email} limit 1")
	User getUserByEmail(@Param("email")String email);
	
	@Select("SELECT * FROM user WHERE DATE(joinTime) >= DATE(#{startTime}) AND DATE(joinTime) <= DATE(#{endTime}) order by joinTime desc limit ${start},${num}")
	List<User> searchUsersByJT(@Param("startTime") String startTime,@Param("endTime") String endTime,@Param("start") int start,@Param("num") int num);
	
	@Select("SELECT * FROM user WHERE DATE(joinTime) >= DATE(#{startTime}) AND DATE(joinTime) <= DATE(#{endTime}) AND memberName LIKE CONCAT('%',#{name},'%') order by joinTime desc limit ${start},${num}")
	List<User> searchUsersByJTN(@Param("startTime") String startTime,@Param("endTime") String endTime,@Param("name") String memberName,@Param("start") int start,@Param("num") int num);
	
	@Insert("INSERT INTO user(nickName,memberName,password,sex,birthday,joinTime,phone,email,qq,wechat,avatar,rankId,status,isDelete) VALUES (#{nickName},#{memberName},#{password},#{sex},#{birthday},#{joinTime},#{phone},#{email},#{qq},#{wechat},#{avatar},#{rankId},#{status},#{isDelete})")
	Integer insert(User user);
	
	@Update("UPDATE user SET nickName = #{nickName},memberName=#{memberName},password=#{password},sex=#{sex},birthday=#{birthday},joinTime=#{joinTime},phone=#{phone},email=#{email},qq=#{qq},wechat=#{wechat},avatar=#{avatar},rankId=#{rankId},status=#{status},isDelete=#{isDelete} WHERE id = #{id}")
	Integer update(User user);
	
	@Delete("DELETE FROM user where id = ${id}")
	Integer delete();
	
	@Select("SELECT COUNT(*) FROM user")
	Integer count();
	
	
	@Select("SELECT COUNT(*) FROM user where DATE(joinTime) >= DATE(#{startTime}) AND DATE(joinTime) <= DATE(#{endTime})")
	Integer countSearchUsersByJT(@Param("startTime")String startTime,@Param("endTime")String endTime);
	
	@Select("SELECT COUNT(*) FROM user WHERE DATE(joinTime) >= DATE(#{startTime}) AND DATE(joinTime) <= DATE(#{endTime}) AND memberName LIKE CONCAT('%',#{name},'%')")
	Integer countSearchUsersByJTN(@Param("startTime")String startTime,@Param("endTime")String endTime,@Param("name") String memberName);
	
	@Update("UPDATE user SET isDelete = 1 where id = ${id}")
	Integer setDelete(@Param("id") Integer id);
}
