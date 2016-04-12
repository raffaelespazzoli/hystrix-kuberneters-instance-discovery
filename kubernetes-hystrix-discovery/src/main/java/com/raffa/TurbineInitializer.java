package com.raffa;

import java.util.HashMap;
import java.util.logging.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.configuration.EnvironmentConfiguration;
import org.apache.commons.configuration.MapConfiguration;
import org.apache.commons.configuration.SystemConfiguration;

import com.netflix.config.ConcurrentCompositeConfiguration;
import com.netflix.config.ConcurrentMapConfiguration;
import com.netflix.config.ConfigurationManager;
import com.netflix.config.DynamicProperty;
import com.netflix.config.DynamicPropertyFactory;
import com.netflix.config.DynamicStringProperty;
import com.netflix.turbine.init.TurbineInit;
import com.netflix.turbine.plugins.PluginsFactory;

public class TurbineInitializer implements ServletContextListener {

	private final static Logger logger = Logger.getLogger(TurbineInitializer.class.getName());

	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		logger.fine("entering contextInitialized");
		EnvironmentConfiguration configFromSystemProperties = new EnvironmentConfiguration();
		MapConfiguration mapConfig=new MapConfiguration(new HashMap<String, Object>());
		// hack to workaround kubernetes yaml file limitation on property name (Must be a C identifier)
		mapConfig.addProperty("turbine.instanceUrlSuffix", configFromSystemProperties.getProperty("turbineInstanceUrlSuffix"));
		ConcurrentCompositeConfiguration finalConfig = new ConcurrentCompositeConfiguration();
		finalConfig.addConfiguration(configFromSystemProperties, "systemConfig");
		finalConfig.addConfiguration(mapConfig, "mapConfig");
		ConfigurationManager.install(finalConfig);
		DynamicStringProperty prop = DynamicPropertyFactory.getInstance().getStringProperty("turbine.instanceUrlSuffix",null);
		logger.fine("using turbine.instanceUrlSuffix:" + prop.getValue());
		PluginsFactory.setInstanceDiscovery(new KubernetesInstanceDiscovery());
		TurbineInit.init();
		logger.fine("exiting contextInitialized");
	}

}
