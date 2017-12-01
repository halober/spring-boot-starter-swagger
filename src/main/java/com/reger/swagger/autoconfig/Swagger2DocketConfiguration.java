package com.reger.swagger.autoconfig;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import com.google.common.base.Predicate;
import com.reger.swagger.properties.Swagger2GroupProperties;
import com.reger.swagger.properties.Swagger2Properties;
import com.reger.swagger.properties.SwaggerProperties;

import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@SuppressWarnings("deprecation")
public class Swagger2DocketConfiguration implements BeanFactoryPostProcessor, EnvironmentAware {
	
	private static final Logger log = LoggerFactory.getLogger(Swagger2DocketConfiguration.class);

	private ConfigurableEnvironment environment;

	@Override
	public void setEnvironment(Environment environment) {
		this.environment = (ConfigurableEnvironment) environment;
	}

	private SwaggerProperties getSwaggerProperties() {
		PropertiesConfigurationFactory<SwaggerProperties> factory = new PropertiesConfigurationFactory<SwaggerProperties>(SwaggerProperties.class);
		factory.setPropertySources(environment.getPropertySources());
		factory.setConversionService(environment.getConversionService());
		factory.setIgnoreInvalidFields(false);
		factory.setIgnoreUnknownFields(true);
		factory.setIgnoreNestedProperties(false);
		factory.setTargetName(SwaggerProperties.prefix);
		try {
			factory.bindPropertiesToTarget();
			return factory.getObject();
		} catch (Exception e) {
			log.info("");
			return null;
		}
	}
	
	private Swagger2Properties getSwagger2Properties() {
		PropertiesConfigurationFactory<Swagger2Properties> factory = new PropertiesConfigurationFactory<Swagger2Properties>(
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

	private Docket getSwagger2Docket(final Swagger2GroupProperties swaggerConfig) {
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(new ApiInfoBuilder().title(swaggerConfig.getTitle())
						.description(swaggerConfig.getDescription()).version(swaggerConfig.getVersion())
						.license(swaggerConfig.getLicense()).licenseUrl(swaggerConfig.getLicenseUrl())
						.termsOfServiceUrl(swaggerConfig.getTermsOfServiceUrl())
						.contact(swaggerConfig.getContact().toContact()).build())
				.groupName(swaggerConfig.getGroupName())
				.pathMapping(swaggerConfig.getPathMapping())// 最终调用接口后会和paths拼接在一起
				.select()
 				.apis(new Predicate<RequestHandler>() { @Override public boolean apply(RequestHandler input) {

 					System.err.println(input.getHandlerMethod().getBeanType().getName());
 					System.err.println(input.getPatternsCondition());
						return false ;
				}}) 
				.paths(new Predicate<String>() { @Override public boolean apply(String input) {
						return input.matches(swaggerConfig.getPathRegex());
				}})
				.build();
	}
	private Docket getOtherSwagger2Docket(final List<String> pathRegexs) {
		Swagger2GroupProperties otherSwagger = otherSwagger();
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(new ApiInfoBuilder().title(otherSwagger.getTitle())
						.description(otherSwagger.getDescription())
						.termsOfServiceUrl(otherSwagger.getTermsOfServiceUrl()).version(otherSwagger.getVersion())
						.contact(otherSwagger.getContact().toContact()).license(otherSwagger.getLicense())
						.licenseUrl(otherSwagger.getLicense()).build())
				.groupName(otherSwagger.getGroupName()).pathMapping(otherSwagger.getPathMapping())// 最终调用接口后会和paths拼接在一起
				.select()
 				.apis(new Predicate<RequestHandler>() { @Override public boolean apply(RequestHandler input) {
					return input.isAnnotatedWith(GetMapping.class)
					|| 	input.isAnnotatedWith(PostMapping.class)
					|| 	input.isAnnotatedWith(DeleteMapping.class)
					|| 	input.isAnnotatedWith(PutMapping.class)
					|| 	input.isAnnotatedWith(RequestMapping.class);
			}}) 
			.paths(new Predicate<String>() { @Override public boolean apply(String input) {
				for (String pathRegex : pathRegexs) {
					if (input.matches(pathRegex)){
						return false;
					}
				}
				return true;
			}})
			.build();
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
		Swagger2Properties swagger2Properties = getSwagger2Properties();
		SwaggerProperties swaggerProperties = getSwaggerProperties();
		List<String> pathRegexs = new ArrayList<String>();
		if (swagger2Properties.getGroup() != null && !swagger2Properties.getGroup().isEmpty()){
			Iterator<Entry<String, Swagger2GroupProperties>> it = swagger2Properties.getGroup().entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry<String, Swagger2GroupProperties> entry = (Map.Entry<String, Swagger2GroupProperties>) it.next();
				beanFactory.registerSingleton(entry.getKey(), this.getSwagger2Docket(entry.getValue()));
				pathRegexs.add(entry.getValue().getPathRegex());
			}
		}
		if (swaggerProperties.getSwaggerGroup() != null && !swaggerProperties.getSwaggerGroup().isEmpty()){
			Iterator<Entry<String, Swagger2GroupProperties>> it = swaggerProperties.getSwaggerGroup().entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry<String, Swagger2GroupProperties> entry = (Map.Entry<String, Swagger2GroupProperties>) it.next();
				beanFactory.registerSingleton(entry.getKey(), this.getSwagger2Docket(entry.getValue()));
				pathRegexs.add(entry.getValue().getPathRegex());
			}
		}
		beanFactory.registerSingleton("other-api", this.getOtherSwagger2Docket(pathRegexs));
	}
}
