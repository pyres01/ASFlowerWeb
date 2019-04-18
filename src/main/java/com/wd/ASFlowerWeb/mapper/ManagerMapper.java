package com.wd.ASFlowerWeb.mapper;

import java.sql.Timestamp;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.wd.ASFlowerWeb.entity.Manager;

/**
 * @author 韦丹
 *
 * 2019年1月6日
 *
 */
public interface ManagerMapper {

	//添加管理员
	@Insert("INSERT INTO MANAGER(name,password,sex,birthday,address,phone,email,qq,wechat,isDelete,deleteTime,rank) VALUES(#{name},#{password},#{sex},#{birthday},#{address},#{phone},#{email},#{qq},#{wechat},#{isDelete},#{deleteTime},#{rank})")
	Integer insert(Manager manager);
	
	//查询未软删除的管理员个数
	@Select("SELECT COUNT(*) FROM MANAGER WHERE iSDelete = 0")
	Integer countNormalAll();
	//查询所有未软删除的管理员并分页
	@Select("SELECT * FROM MANAGER WHERE iSDelete = 0 LIMIT ${limitStart},${limitSize}")
	List<Manager> findNormalAllPaged(@Param("limitStart") Integer limitStart,@Param("limitSize") Integer limitSize);
	//查询所有未软删除的管理员不分页
	@Select("SELECT * FROM MANAGER WHERE iSDelete = 0")
	List<Manager> findNormalAll();
	
	//查询已软删除的管理员个数
	@Select("SELECT COUNT(*) FROM MANAGER WHERE iSDelete = 1")
	Integer countDelAll();
	//查询所有已软删除的管理员并分页
	@Select("SELECT * FROM MANAGER WHERE iSDelete = 1 LIMIT ${limitStart},${limitSize}")
	List<Manager> findDelAllPaged(@Param("limitStart") Integer limitStart,@Param("limitSize") Integer limitSize);
	//查询所有已软删除的管理员不分页
	@Select("SELECT * FROM MANAGER WHERE iSDelete = 1")
	List<Manager> findDelAll();
	
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
	@Update("UPDATE MANAGER SET name = #{name},password = #{password},sex = #{sex},birthday = #{birthday},address = #{address},phone = #{phone},email = #{email},qq = #{qq},wechat = #{wechat},isDelete = #{isDelete},deleteTime = #{deleteTime},rank=#{rank} WHERE id = #{id}")
	Integer updateManagerById(Manager manager);
	
	//根据管理员id删除管理员
	@Delete("DELETE FROM MANAGER WHERE id = ${id}")
	Integer relDeleteManager(@Param("id") Integer id);
	
	//更新管理员软删除状态
	@Update("UPDATE MANAGER SET isDelete = 1,deleteTime = #{deleteTime} WHERE id = ${id}")
	Integer DeleteManager(@Param("id") Integer id,@Param("deleteTime") Timestamp deleteTime);
	@Update("UPDATE MANAGER SET isDelete = 0 WHERE id = ${id}")
	Integer RecoverManager(@Param("id") Integer id);
	
	@Select("SELECT * FROM MANAGER WHERE id = ${id}")
	Manager getManagerById(@Param("id") Integer id);
}
