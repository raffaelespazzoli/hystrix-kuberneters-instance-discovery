# Kubernetes hystrix discovery

This library allows you to discover Hystrix endpoints in a Kubernetes environment.
Hystrix endpoint must be published as part of a Kubernetes service.
Hystrix endpoints will be aggregated by Turbine an then can be visualized.

In order for this library to work you need to pass the following environment variables:
 * namespace: this is the namespace under which the service kubernetes runs
 * selector_key: this is the key of the selector that will, be used to get the list of pods.
 * selector_value: this is the balue of the selector that will be used to get the list of pods.
 * hystrix\_cluster\_name: this is the name of the cluster under which turbine will publish the aggregated data. For example if you cluster name is bob, you'll be able to retrieve the data at ??
 
 You'll need to create a web app with this library and initialize Netflix Turbine appropriately. To initialize the Kubernetes Hystrix discovery, please refer to the [netflix turbine project](https://github.com/Netflix/Turbine/wiki). 
 If you don't need special initialization a simpler initializer is provided. Just add the following to your web.xml:
 
	 <listener>
			<listener-class>
	             com.raffa.TurbineInitializer 
	        </listener-class>       
	 </listener>
 