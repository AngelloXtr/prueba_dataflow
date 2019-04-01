package com.bbva.config;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @version 1.0
 * @author GCP Team
 * */
public class ObjectifyLoader implements ServletContextListener {

	static {
//		ObjectifyService.register(String.class);	
	}

	public void contextInitialized(ServletContextEvent sce) {
		
	}

	public void contextDestroyed(ServletContextEvent sce) {
		
	}
}