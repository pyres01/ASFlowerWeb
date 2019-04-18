package com.wd.ASFlowerWeb.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 韦丹
 *
 * 2019年3月9日
 *
 */
@Data
@NoArgsConstructor
public class OrderAndItemView {
	private String buyer;
	private Integer order_id;
	private Integer order_uid;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone="GMT+8")
	private Timestamp order_createTime;
	private String order_serialNo;
	private BigDecimal order_total;
	private String order_receiver;
	private String order_phone;
	private String order_address;
	private Integer order_status;
	private Integer item_id;
	private Integer item_oid;
	private Integer item_sid;
	private String item_serialNo;
	private String item_shoppingName;
	private String item_introduction;
	private String item_shoppingImg;
	private BigDecimal item_price;
	private Integer item_count;
	private BigDecimal item_subTotal;
	private String item_remark;
	private Integer item_status;
	private Integer item_isDelete;
	
}
