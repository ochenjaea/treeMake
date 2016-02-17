package com.taxholic.configuration.beans;

import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;


@Configuration
//@ComponentScan(
//	    basePackages = {"com.taxholic.configuration.beans","com.taxholic.core.web.dao"}, 
//	   // useDefaultFilters = false,
//	    includeFilters = {
//	        @ComponentScan.Filter(type = FilterType.ANNOTATION,   value = org.springframework.stereotype.Repository.class)
//	    })

@ComponentScan(basePackages = {"com.taxholic.configuration.beans","com.taxholic.web","com.taxholic.core.web.dao"}, excludeFilters = {
		@ComponentScan.Filter(Controller.class), @ComponentScan.Filter(ControllerAdvice.class)})


public class AnnotationConfiguration {
	
	
	@Bean
	public ReloadableResourceBundleMessageSource messageSource() throws IOException {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasenames("classpath:message/server");
		messageSource.setCacheSeconds(5);
		return messageSource;
	}

	@Bean
	public MessageSourceAccessor messageSourceAccessor() throws IOException {
		return new MessageSourceAccessor(messageSource());
	}
	
	
}