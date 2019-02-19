package com.niit.configuration;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	
	public WebAppInitializer() {
		System.out.println("WebAppInitializer is loaded and instantiated..");
	}
	
	@Override
	protected Class<?>[] getRootConfigClasses() {
		System.out.println("In getRootConfigClasses method");
		return new Class[]{WebAppConfig.class};
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		System.out.println("In getServletConfigClasses method");
		return null;
	}

	@Override
	protected String[] getServletMappings() {
		System.out.println("in getServletMappings method");
		return new String[]{"/"};
	}

}
