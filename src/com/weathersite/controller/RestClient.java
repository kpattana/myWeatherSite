package com.weathersite.controller;


	//import org.glassfish.jersey.client.ClientConfig;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.config.DefaultClientConfig;
	import com.sun.jersey.api.client.ClientResponse;
	import com.sun.jersey.api.client.WebResource;
	 
	/**
	 * @author Crunchify
	 * 
	 */
	 
	public class RestClient {
	 
		public static void main(String[] args) {
	 
			RestClient crunchifyClient = new RestClient();
			//crunchifyClient.getCtoFResponse();
			crunchifyClient.getFtoCResponse();
		}
	 
		private void getFtoCResponse() {
			try {
				DefaultClientConfig config = new DefaultClientConfig();
	 
				Client client = Client.create(config);
				//WebResource webResource2 = client.resource("http://localhost:8080/CrunchifyRESTJerseyExample/crunchify/ftocservice/90");
				WebResource webResource2 = client.resource("http://api.openweathermap.org/data/2.5/weather?q=London&appid=5faf668d624cabafa8f6c70ff24f3730");
			
				ClientResponse response2 = webResource2.accept("application/json").get(ClientResponse.class);
				if (response2.getStatus() != 200) {
					throw new RuntimeException("Failed : HTTP error code : " + response2.getStatus());
				}
	 
				String output2 = response2.getEntity(String.class);
				System.out.println("\n============getFtoCResponse============");
				System.out.println(output2);
	 
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	 
		private void getCtoFResponse() {
			try {
	 
				Client client = Client.create();
				WebResource webResource = client.resource("http://localhost:8080/CrunchifyRESTJerseyExample/crunchify/ctofservice/40");
				ClientResponse response = webResource.accept("application/xml").get(ClientResponse.class);
				if (response.getStatus() != 200) {
					throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
				}
	 
				String output = response.getEntity(String.class);
				System.out.println("============getCtoFResponse============");
				System.out.println(output);
	 
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
}
