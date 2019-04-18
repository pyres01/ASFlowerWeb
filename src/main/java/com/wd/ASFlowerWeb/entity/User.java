package com.wd.ASFlowerWeb.entity;

import java.sql.Timestamp;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 韦丹
 *
 * 2019年1月5日
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
	
	private Integer id;
	private String nickName;
	private String memberName;
	private String password;
	@JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date birthday;
	private Boolean sex = false;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Timestamp joinTime;
	private String phone;
	private String email;
	private String qq;
	private String wechat;
	private String avatar;
	private Integer rankId = 0;
	private Integer status=1;
	private Boolean isDelete=false;
}
