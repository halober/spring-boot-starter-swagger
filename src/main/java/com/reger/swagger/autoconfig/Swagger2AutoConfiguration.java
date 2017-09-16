package com.reger.swagger.autoconfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.web.bind.annotation.RestController;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author lei
 * @version 1.0.0
 */

@Configuration
public class Swagger2AutoConfiguration {

	private static final Logger log = LoggerFactory.getLogger(Swagger2AutoConfiguration.class);

	@Profile("api")
	@EnableSwagger2
	public static class  swagger2Docket extends Swagger2Docket {
		{
			log.debug("启用了swagger文档");
		}
	}

	static class ConditionApi implements Condition{

		@Override
		public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
			if (context.getEnvironment() != null) {
				if (context.getEnvironment().acceptsProfiles(("api"))) {
					return false;
				}
			}
			return true;
		}
	}
	
	@RestController
	@Conditional(ConditionApi.class)
	public static class PreventSwaggerUi extends PreventSwaggerResourcesController{
	 
	}
}
