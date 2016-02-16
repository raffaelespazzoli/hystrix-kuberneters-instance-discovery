# hystrix-kuberneters-instance-discovery

This library allows you to discover [Hystrix](https://github.com/Netflix/Hystrix) data stream endpoints in a [Kubernetes](http://kubernetes.io/) environment.
Hystrix endpoints must be published as part of a [Kubernetes service](http://kubernetes.io/v1.1/docs/user-guide/services.html).
Hystrix endpoints will be aggregated by [Turbine](https://github.com/Netflix/Turbine/wiki) and then can be visualized using the [hystrix dashboard](https://github.com/Netflix/Hystrix/tree/master/hystrix-dashboard).

In order for this library to work you need to pass the following environment variables:
 * namespace: this is the namespace under which the service kubernetes runs.
 * selector_key: this is the key of the selector that will, be used to get the list of pods.
 * selector_value: this is the balue of the selector that will be used to get the list of pods.
 * hystrix\_cluster\_name: this is the name of the cluster under which turbine will publish the aggregated data. For example if you cluster name is bob, you'll be able to retrieve the data at ??
 
If you run this library inside a kubernetes cluster the [fabric8](http://fabric8.io/) kuebernetes client should be able to initialize itself. if you're running this library outside a kubernetes cluster you will need to set additional environment variables in order to initialize the kuebernetes client as described [here](https://github.com/fabric8io/kubernetes-client).
 
You'll need to create a web app with this library and initialize Netflix Turbine appropriately. To initialize the Kubernetes Hystrix instance discovery, please refer to the [netflix turbine project](https://github.com/Netflix/Turbine/wiki). The discovery class name is com.raffa. KubernetesInstanceDiscovery.

If you don't need special initialization a simple initializer is provided. Just add the following to your web.xml:
 
	 <listener>
			<listener-class>
	             com.raffa.TurbineInitializer 
	        </listener-class>       
	 </listener>

I created a simple web app that includes turbine with this initialer and the hystrix dashboard.
You can find the project here.
I also created a container with the above web app deplyed on tomcat. The container can be found [here](https://hub.docker.com/r/raffaelespazzoli/kubernetes-hystrix-dashboard/).
[Here](https://github.com/raffaelespazzoli/hystrix-kuberneters-instance-discovery/tree/master/kubernetes-hystrix-dashboard/src/main/kubernetes) you can find sample yaml files nee
