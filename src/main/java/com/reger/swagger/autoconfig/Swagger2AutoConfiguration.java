package com.reger.swagger.autoconfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author lei
 * @version 1.0.0
 */

@Configuration
public class Swagger2AutoConfiguration {

	private static final Logger log = LoggerFactory.getLogger(Swagger2AutoConfiguration.class);

	@EnableSwagger2
	@Conditional(ConditionApi.class)
	public static class  swagger2Docket extends Swagger2DocketConfiguration {
		{
			log.debug("启用了swagger文档");
		}
	}
	
	@RestController
	@Conditional(ConditionNotApi.class)
	public static class PreventSwaggerUi extends PreventSwaggerResourcesController{
	 
	}
}
