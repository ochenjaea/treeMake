package com.taxholic.core.configuration.web;

import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.BeanNameViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesView;

@Configuration
@EnableWebMvc
//@Import({TilesConfiguration.class, PropertiesConfiguration.class, MessageConfiguration.class, HttpClientConfiguration.class})
@ComponentScan(basePackages = {"com.taxholic.web"}, includeFilters = {
													@ComponentScan.Filter(Controller.class), @ComponentScan.Filter(ControllerAdvice.class)
												  }, useDefaultFilters = false)
public class SpringMvcConfiguration extends WebMvcConfigurerAdapter {
	
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
	
    @Bean
    public MultipartResolver multipartResolver() {
    	CommonsMultipartResolver resolver = new CommonsMultipartResolver();
	    resolver.setMaxUploadSize(10485760);
	    
	    return resolver;
    }

    @Bean
    public ViewResolver viewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setViewClass(JstlView.class);
        resolver.setPrefix("/WEB-INF/jsp/");
        resolver.setSuffix(".jsp");
        resolver.setOrder(2);
        return resolver;
    }
   
 
    @Bean
    public UrlBasedViewResolver tilesViewResolver() {
        UrlBasedViewResolver resolver = new UrlBasedViewResolver();
        resolver.setViewClass(TilesView.class);
        resolver.setOrder(1);
        return resolver;
    }
 
    @Bean
    public TilesConfigurer tilesConfigurer() {
        TilesConfigurer tilesConfigurer = new TilesConfigurer();
        tilesConfigurer.setDefinitions(new String[] {"classpath:config/tiles/tiles-*.xml"});
        return tilesConfigurer;
    }
    
    @Bean
    public MappingJacksonJsonView jsonView() {
        MappingJacksonJsonView jsonView = new MappingJacksonJsonView();
        jsonView.setContentType("application/json;charset=UTF-8");
        return jsonView;
    }
    
    @Bean
    public BeanNameViewResolver beanNameResolver() {
    	BeanNameViewResolver beanNameViewResolver = new BeanNameViewResolver();
    	beanNameViewResolver.setOrder(0);
        return beanNameViewResolver;
    }
    

}
