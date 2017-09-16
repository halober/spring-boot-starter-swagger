package com.test.gitee.reger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;

@Api("操作的api")
@RestController
@SpringBootApplication(scanBasePackages="com.test.gitee")
public class SpringBootStarterSwaggerApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(SpringBootStarterSwaggerApplication.class, args);
	}

}
