package com.taxholic.core.configuration.web;

import org.springframework.context.annotation.ComponentScan;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;

@Configuration
@ComponentScan(basePackages = {"com.taxholic.core.configuration.beans","com.taxholic.core.web","com.taxholic.web"}, excludeFilters = {
		@ComponentScan.Filter(Controller.class), @ComponentScan.Filter(ControllerAdvice.class)})
public class AnnotationConfiguration {
}
