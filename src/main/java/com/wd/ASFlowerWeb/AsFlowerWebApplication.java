package com.wd.ASFlowerWeb;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan({"com.wd.ASFlowerWeb.mapper"})
public class AsFlowerWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(AsFlowerWebApplication.class, args);
	}

}

