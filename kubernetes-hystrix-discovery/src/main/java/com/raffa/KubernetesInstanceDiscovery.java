package com.raffa;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.netflix.turbine.discovery.Instance;
import com.netflix.turbine.discovery.InstanceDiscovery;

import io.fabric8.kubernetes.api.model.Pod;
import io.fabric8.kubernetes.api.model.PodList;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;

public class KubernetesInstanceDiscovery implements InstanceDiscovery {

	private final static Logger logger = Logger.getLogger(KubernetesInstanceDiscovery.class.getName());
	private final static String namespace = System.getenv("namespace");
	private final static String selector_key = System.getenv("selector_key");
	private final static String selector_value = System.getenv("selector_value");
	// private final static String mbl_pod_port=System.getenv("mbl_pod_port");;
	// private final static String mbl_hystrix_stream="/hystrix.stream";
	private final static String hystrix_cluster_name = System.getenv("hystrix_cluster_name");;
	private KubernetesClient client;

	public KubernetesInstanceDiscovery() {

	}

	@Override
	public Collection<Instance> getInstanceList() {
		logger.fine("getInstanceList called");
		logger.fine("connecting to kubernetes master");
		try {
			client = new DefaultKubernetesClient();
		} catch (Throwable e) {
			logger.log(Level.SEVERE, "unable to connect to kubernetes master", e);
			return new ArrayList<Instance>();
		}
		logger.fine("client: " + client);
		logger.fine("finding mbl pods, namespace: " + namespace + " label: " + selector_key + " value: "
				+ selector_value);
		PodList podlist = client.pods().inNamespace(namespace).withLabel(selector_key, selector_value).list();
		List<Instance> sal = new ArrayList<Instance>();
		for (Pod pod : podlist.getItems()) {
			URI saUri = null;
			String podip = pod.getStatus().getPodIP();
			logger.fine("found mbl pod at: " + podip);
			Instance sa = new Instance(podip, hystrix_cluster_name, true);
			sal.add(sa);
		}
		logger.fine("instances found: " + sal);
		logger.fine("getInstanceList exiting");
		return sal;
	}

}
