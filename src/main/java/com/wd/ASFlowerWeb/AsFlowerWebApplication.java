package com.wd.ASFlowerWeb;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 
 * @author 韦丹
 * 
 * 项目启动程序
 *
 */

@EnableTransactionManagement //开启事务管理
@SpringBootApplication	//项目启动应用

@MapperScan({"com.wd.ASFlowerWeb.mapper"})	//扫描mapper包
public class AsFlowerWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(AsFlowerWebApplication.class, args);
	}

}

