package com.wd.ASFlowerWeb.entity;

import java.sql.Timestamp;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
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
public class Manager {
	
	private Integer id;
	private String name;
	private String password;
	private Boolean sex = false;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Timestamp birthday;
	private String address;
	private String phone;
	private String email;
	private String qq;
	private String wechat;
	private Boolean isDelete = false;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Timestamp deleteTime;
	private Integer rank = 1;
}
