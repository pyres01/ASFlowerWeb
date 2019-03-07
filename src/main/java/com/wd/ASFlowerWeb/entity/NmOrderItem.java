package com.wd.ASFlowerWeb.entity;

import java.math.BigDecimal;

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
public class NmOrderItem {

	private Integer id;
	private Integer oid;
	private Integer sid;
	private String shoppingName;
	private String introduction;
	private String shoppingImg;
	private BigDecimal price;
	private Integer count;
	private BigDecimal subTotal;
	private String ramark;
	private Integer status = 0;
	private Boolean isDelete = false;
	private String serialNo;
}
