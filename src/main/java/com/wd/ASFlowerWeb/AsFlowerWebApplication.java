package com.wd.ASFlowerWeb;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@SpringBootApplication

//扫描mapper包
@MapperScan({"com.wd.ASFlowerWeb.mapper"})
public class AsFlowerWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(AsFlowerWebApplication.class, args);
	}

}

