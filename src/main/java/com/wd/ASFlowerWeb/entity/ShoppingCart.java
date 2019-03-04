package com.wd.ASFlowerWeb.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 若尘
 *
 * 2019年3月2日
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingCart {
	private Integer id;
	private Integer uid;
	private Integer sid;
	private Integer count;
}
