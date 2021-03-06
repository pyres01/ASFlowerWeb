package com.wd.ASFlowerWeb.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 韦丹
 *
 * 2019年3月4日
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor

public class NmOrder {

	private Integer id;
	private Integer uid;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Timestamp createTime;
	private String serialNo;
	private BigDecimal total;
	private String receiver;
	private String phone;
	private String address;
	private Integer status = 0;
}
