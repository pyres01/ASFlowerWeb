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
 * 2019年2月19日
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NmShopping {
	private Integer id;
	private Integer typeId;
	private String shoppingName;
	private String introduction;
	private String shoppingImg;
	private BigDecimal asPrice;
	private BigDecimal nmPrice;
	private Integer store;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Timestamp onShelveTime;
	private Boolean isSale;
	private String shoppingDetail;
}
