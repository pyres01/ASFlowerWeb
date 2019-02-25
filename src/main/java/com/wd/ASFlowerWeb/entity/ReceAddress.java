package com.wd.ASFlowerWeb.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 若尘
 *
 * 2019年2月25日
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReceAddress {

	private Integer id;
	private Integer uid;
	private String receiver;
	private String phone;
	private String address;
	private Boolean def = false;
}
