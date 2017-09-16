package com.reger.swagger.autoconfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.bind.PropertiesConfigurationFactory;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.reger.swagger.properties.Swagger2GroupProperties;
import com.reger.swagger.properties.Swagger2Properties;

import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

public class Swagger2Docket implements BeanFactoryPostProcessor, EnvironmentAware {

	private ConfigurableEnvironment environment;

	@Override
	public void setEnvironment(Environment environment) {
		this.environment = (ConfigurableEnvironment) environment;
	}

	private Swagger2Properties getSwagger2Properties() {
		PropertiesConfigurationFactory<Swagger2Properties> factory = new PropertiesConfigurationFactory<>(
				Swagger2Properties.class);
		factory.setPropertySources(environment.getPropertySources());
		factory.setConversionService(environment.getConversionService());
		factory.setIgnoreInvalidFields(false);
		factory.setIgnoreUnknownFields(true);
		factory.setIgnoreNestedProperties(false);
		factory.setTargetName(Swagger2Properties.prefix);
		try {
			factory.bindPropertiesToTarget();
			return factory.getObject();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private Docket getSwagger2Docket(Swagger2GroupProperties swaggerConfig) {
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(new ApiInfoBuilder().title(swaggerConfig.getTitle())
						.description(swaggerConfig.getDescription()).version(swaggerConfig.getVersion())
						.license(swaggerConfig.getLicense()).licenseUrl(swaggerConfig.getLicenseUrl())
						.termsOfServiceUrl(swaggerConfig.getTermsOfServiceUrl())
						.contact(swaggerConfig.getContact().toContact()).build())
				.groupName(swaggerConfig.getGroupName()).pathMapping(swaggerConfig.getPathMapping())// 最终调用接口后会和paths拼接在一起
				.select()
				.apis((RequestHandler input) -> null != input.findAnnotation(GetMapping.class)
						|| null != input.findAnnotation(PostMapping.class)
						|| null != input.findAnnotation(DeleteMapping.class)
						|| null != input.findAnnotation(PutMapping.class)
						|| null != input.findAnnotation(RequestMapping.class))
				.paths((String input) -> input.matches(swaggerConfig.getPathRegex())).build();
	}

	private Docket getOtherSwagger2Docket(List<String> pathRegexs) {
		Swagger2GroupProperties otherSwagger = otherSwagger();
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(new ApiInfoBuilder().title(otherSwagger.getTitle())
						.description(otherSwagger.getDescription())
						.termsOfServiceUrl(otherSwagger.getTermsOfServiceUrl()).version(otherSwagger.getVersion())
						.contact(otherSwagger.getContact().toContact()).license(otherSwagger.getLicense())
						.licenseUrl(otherSwagger.getLicense()).build())
				.groupName(otherSwagger.getGroupName()).pathMapping(otherSwagger.getPathMapping())// 最终调用接口后会和paths拼接在一起
				.select()
				.apis((RequestHandler input) -> null != input.findAnnotation(GetMapping.class)
						|| null != input.findAnnotation(PostMapping.class)
						|| null != input.findAnnotation(DeleteMapping.class)
						|| null != input.findAnnotation(PutMapping.class)
						|| null != input.findAnnotation(RequestMapping.class))
				.paths((String input) -> {
					for (String pathRegex : pathRegexs) {
						if (input.matches(pathRegex))
							return false;
					}
					return true;
				}).build();
	}

	private Swagger2GroupProperties otherSwagger() {
		Swagger2GroupProperties otherSwagger = new Swagger2GroupProperties();
		otherSwagger.setGroupName("other-api");
		otherSwagger.setDescription("以上api中未被包含进来得接口");
		otherSwagger.setPathMapping("/");
		otherSwagger.setVersion("");
		otherSwagger.setPathRegex(null);
		otherSwagger.setTitle("其它api");
		return otherSwagger;
	}

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		Swagger2Properties Swagger2Properties = getSwagger2Properties();
		Map<String, Swagger2GroupProperties> swaggers = Swagger2Properties.getSwaggerGroup();
		List<String> pathRegexs = new ArrayList<>();
		if (swaggers != null && !swaggers.isEmpty())
			swaggers.forEach((name, swaggerConfig) -> {
				beanFactory.registerSingleton(name, this.getSwagger2Docket(swaggerConfig));
				pathRegexs.add(swaggerConfig.getPathRegex());
			});
		beanFactory.registerSingleton("other-api", this.getOtherSwagger2Docket(pathRegexs));
	}
}
