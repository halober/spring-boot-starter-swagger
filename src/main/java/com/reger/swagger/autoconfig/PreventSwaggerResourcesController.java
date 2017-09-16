package com.reger.swagger.autoconfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;

public class PreventSwaggerResourcesController {

	private static final Logger log = LoggerFactory.getLogger(PreventSwaggerResourcesController.class);

	public PreventSwaggerResourcesController() {
		log.info("禁用用了swagger文档html页面‘/swagger-ui.html’和其他资源的访问的访问");
	}

	@RequestMapping("/swagger-ui.html")
	public void swaggerIndex() {
		log.info("swagger api文档未开放,不允许访问");
	}

	@RequestMapping("/webjars/springfox-swagger-ui/**")
	public void swaggerResources() {
		log.info("swagger api文档未开放,不允许访问");
	}
}