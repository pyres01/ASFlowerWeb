package com.wd.ASFlowerWeb.entity;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 风微轻
 *
 * 2019年1月5日
 * Siteinfo entity
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Siteinfo implements Serializable{
	
	@NotNull
	private String name;
	private String introduce;
	@NotNull
	private String author;
	@NotNull
	private String aboutAuthor;
	private String logoCol;
	private String logoDef;
	private String shortcutIcon;
	private String brandSlogan;
	private String keywork;
	@NotNull
	private String copyright;
	private String qrcode;
	private String privacyClause;
	
	
}
