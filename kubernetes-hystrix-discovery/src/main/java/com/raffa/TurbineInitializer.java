package com.raffa;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.netflix.turbine.init.TurbineInit;
import com.netflix.turbine.plugins.PluginsFactory;

public class TurbineInitializer implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		PluginsFactory.setInstanceDiscovery(new KubernetesInstanceDiscovery());
		TurbineInit.init();		
	}

}
