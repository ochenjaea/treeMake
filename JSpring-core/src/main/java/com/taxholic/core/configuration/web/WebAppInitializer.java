package com.taxholic.core.configuration.web;

import java.util.EnumSet;


import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.multipart.support.MultipartFilter;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.util.Log4jConfigListener;


public class WebAppInitializer implements WebApplicationInitializer {
 
	private AnnotationConfigWebApplicationContext context;
	private ServletContext servletContext;
	
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
    	
    	this.servletContext = servletContext; 
		
		 initWebApplicationContext();
		 addListener();
		 setServletDispatcher();
		 addServletFilter();
		 addSecurityFilter();
    }
    
    /**
     * Add Servlet Filter
     */
    public void addServletFilter() throws ServletException {
        addEncodeFilter();
    }
    
    /**
     * Add Servlet Listener
     */
    public void addListener() {
        addSpringCoreListener();
    }
    
    /**
     * load Spring config
     */
    public void initWebApplicationContext() {
        context = new AnnotationConfigWebApplicationContext();
        context.register(AnnotationConfiguration.class);

        // set application context
        ApplicationContextProvider.set(context);
    }
    
    
    /**
     * Add Encoding Filter
     *
     * @throws ServletException
     */
    public void addEncodeFilter() throws ServletException {
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        characterEncodingFilter.setForceEncoding(true);
        FilterRegistration.Dynamic characterEncoding = servletContext.addFilter("characterEncodingFilter",  characterEncodingFilter);
        characterEncoding.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), true, "/*");
    }
    
    
    /**
     * Add Listener
     */
    public void addSpringCoreListener() {
    	
//     servletContext.setInitParameter("log4jConfigLocation", "classpath:config/log4j.xml");
//     servletContext.addListener(new Log4jConfigListener());
        servletContext.addListener(new ContextLoaderListener(context));
    }

    public void setServletDispatcher() {
    	
        AnnotationConfigWebApplicationContext dispatcherServletContext = new AnnotationConfigWebApplicationContext();
        dispatcherServletContext.register(SpringMvcConfiguration.class);
        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatcher", new DispatcherServlet(dispatcherServletContext));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("*.do");

        context.refresh();
    }
    
    /**
     * Add Security Filter
     */
    public void addSecurityFilter() {
        DelegatingFilterProxy springSecurityFilterChain = new DelegatingFilterProxy();
        FilterRegistration.Dynamic securityFilter = servletContext.addFilter("springSecurityFilterChain",springSecurityFilterChain);
        securityFilter.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
    }
    

  
 }