package com.wd.ASFlowerWeb.entity;

import java.sql.Timestamp;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 风微轻
 *
 * 2019年1月5日
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class User {
	
	private Integer id;
	private String nickName;
	private String memberName;
	private String password;
	private Boolean sex;
	private String realName;
	private String idCard;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Timestamp birthday;
	private String phone;
	private String email;
	private String qq;
	private String wechat;
	private String avatar;
	private Integer rankId;
	private String qrcode;
	private Integer inviter;
	private Integer status;
	private Boolean isDelete;
}
