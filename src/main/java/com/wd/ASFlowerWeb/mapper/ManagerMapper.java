package com.wd.ASFlowerWeb.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.wd.ASFlowerWeb.entity.Manager;

/**
 * @author 风微轻
 *
 * 2019年1月6日
 *
 */
public interface ManagerMapper {

	//添加管理员
	@Insert("INSERT INTO MANAGER(name,relname,password,sex,birthday,address,phone,avatar,email,signature,qq,wechat,status,isDelete,deleteTime) VALUES(#{name},#{relname},#{password},#{sex},#{birthday},#{address},#{phone},#{avatar},#{email},#{signature},#{qq},#{wechat},#{status},#{isDelete},#{deleteTime})")
	Integer insert(Manager manager);
	
	//查询未软删除的管理员个数
	@Select("SELECT COUNT(*) FROM MANAGER WHERE iSDelete = 0")
	Integer countNormalAll();
	//查询所有未软删除的管理员并分页
	@Select("SELECT * FROM MANAGER WHERE iSDelete = 0 LIMIT ${limitStart},${limitSize}")
	List<Manager> findNormalAll(@Param("limitStart") Integer limitStart,@Param("limitSize") Integer limitSize);
	
	
	//查询已软删除的管理员个数
	@Select("SELECT * FROM MANAGER WHERE iSDelete = 1 LIMIT ${limitStart},${limitSize}")
	List<Manager> countDellAll(@Param("limitStart") Integer limitStart,@Param("limitSize") Integer limitSize);
	//查询所有已软删除的管理员并分页
	@Select("SELECT * FROM MANAGER WHERE iSDelete = 1 LIMIT ${limitStart},${limitSize}")
	List<Manager> findDellAll(@Param("limitStart") Integer limitStart,@Param("limitSize") Integer limitSize);
	
	
	//根据用户名查询单个用户，用于登陆查询
	@Select("SELECT * FROM MANAGER WHERE NAME = #{name} AND iSDelete = 0 LIMIT 1")
	Manager getNormalManagerByName(@Param("name") String name);
	
	
	//根据用户名模糊查询并分页
	@Select("SELECT * FROM MANAGER WHERE NAME like CONCAT('%',#{name},'%') AND iSDelete = 0 LIMIT ${limitStart},${limitSize}")
	List<Manager> findDelManagerByName(@Param("name") String name,@Param("limitStart") Integer limitStart,@Param("limitSize") Integer limitSize);
	
	
	//根据管理员id更新管理员密码
	@Update("UPDATE MANAGER SET password = #{password} where id = ${id}")
	Integer updateManagerPwd(@Param("password") String password,@Param("id") Integer id);
	
	//根据id更新管理员信息
	@Update("UPDATE MANAGER SET name = #{name},relname = #{relname},password = #{password},sex = #{sex},birthday = #{birthday},address = #{address},phone = #{phone},avatar = #{avatar},email = #{email},signature = #{signature},qq = #{qq},wechat = #{wechat},status = #{status},isDelete = #{isDelete},deleteTime = #{deleteTime} WHERE id = #{id}")
	Integer updateManagerById(Manager manager);
	
	//根据管理员id删除管理员
	@Delete("DELETE FROM MANAGER WHERE id = ${id}")
	Integer deleteManager(@Param("id") Integer id);
	
	
}
