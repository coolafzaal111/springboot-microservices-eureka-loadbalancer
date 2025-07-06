package com.afzaalk.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


@RestController
@RequestMapping("/orders")
public class OrderController {
	
	@Autowired
	RestTemplate restTemplate;
	
//	@Autowired
//	DiscoveryClient discoveryClient;
	
	@Value("${product.service.baseurl}")
    String productBaseURL;
	
	
	/** Below code will be used when we are using Service Discovery without load balancing
	@GetMapping("/{id}")
	public ResponseEntity<String> getOrder(@PathVariable String id) {
		
		// invoke productservice API by passing proper URI - older way without service discovery
		// String response = restTemplate.getForObject("http://localhost:8082/products/id/"+id, String.class);
		
		// With Service Discovery
		List<ServiceInstance> instances = discoveryClient.getInstances("product-service");
		System.out.println("instances are : " + instances);
		URI uri = instances.get(0).getUri();	// put load balancer logic to choose a particular instance
		String response = restTemplate.getForObject(uri + "/products/" + id, String.class);
		
		System.out.println("Response from Productservice API called from order service: " + response);
		return ResponseEntity.ok("Order call successful");
	}*/
	
	
	// Below code will be used when we are using Service Discovery with load balancing
	@GetMapping("/{id}")
    public ResponseEntity<String> getOrder(@PathVariable String id) {

    	String response = restTemplate.getForObject(productBaseURL + "/products/" + id, String.class);
    	System.out.println("Response from Product api call is: " + response);
    	
    	return ResponseEntity.ok("Order call successful");
   } 
	

}
