package com.weathersite.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;

import javax.ws.rs.core.MediaType;

import org.json.JSONException;
import org.json.JSONObject;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.client.urlconnection.URLConnectionClientHandler;
import com.weathersite.config.URLConnectionFactory;
import com.weathersite.exception.WeatherException;
import com.weathersite.model.Weather;
import com.weathersite.util.PropertyFileUtil;

public class WeatherService {

	//populating weather object to be pass on to view to display details
	//assumed JSON keys & definition are not changed as they are hard coded
	public Weather getWeatherForCity(String city) throws WeatherException {
		
		JSONObject jsonObj;
		Weather weatherObj = null;
		try {
			jsonObj = restClientCall(city);
		
			weatherObj = new Weather();
			SimpleDateFormat df = new SimpleDateFormat("hh:mm:ss a",Locale.ENGLISH);
			weatherObj.setCelsius(String.format ("%.2f", (jsonObj.getJSONObject("main").getDouble("temp")- 273.16)));
			weatherObj.setFahrenheit(String.format ("%.2f", (((jsonObj.getJSONObject("main").getDouble("temp") - 273) * 9.0/5) + 32)));
			weatherObj.setSunrise(df.format(jsonObj.getJSONObject("sys").getLong("sunrise")*1000));
			weatherObj.setSunset(df.format(jsonObj.getJSONObject("sys").getLong("sunset")*1000));
			SimpleDateFormat dt = new SimpleDateFormat("dd/MMM/yyyy",Locale.ENGLISH);
			weatherObj.setDate(dt.format(new Date(jsonObj.getLong("dt")*1000)));
			weatherObj.setDescription(jsonObj.getJSONArray("weather").getJSONObject(0).get("main") + "\n\r" + jsonObj.getJSONArray("weather").getJSONObject(0).get("description"));
			
			weatherObj.setName(jsonObj.getString("name"));
		
		} catch (JSONException | IOException | RuntimeException e) {
			throw new WeatherException("Error when populating Weather object: " + e.getMessage());
		}

		return weatherObj;
	}
	
	//return JSON object from openweathermap, required queryparams are cityname & appkey 
	private JSONObject restClientCall(String city) throws WeatherException, IOException, JSONException {
		
		String responseBody = null;
		InputStream contentStream = null;
		
		try {
			
			//if no proxy settings required uncomment below two line, 
			//although URLConnectionFactory should be able to handle proxy or no proxy  
			//DefaultClientConfig cc = new DefaultClientConfig();
			//Client client = Client.create(cc);
			
			//this will work for proxy, please update config.properties
			URLConnectionClientHandler cc = new URLConnectionClientHandler(new URLConnectionFactory());
            Client client = new Client(cc);
          
            
			WebResource webResource = client.resource("http://api.openweathermap.org/data/2.5/weather").
					queryParam("q", city.trim().replaceAll(" ", "")).
					queryParam("appid", new PropertyFileUtil().getPropertyValue("appid")); 	//get app key from property file - openweathermap needs app key
			
			ClientResponse response = webResource.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);

			int statusCode = response.getStatus();
			if (statusCode < 200 || statusCode >= 300) {
				throw new WeatherException("Status code error :" + statusCode);
			}
			/* Read the response content */
			contentStream = response.getEntityInputStream();
			Reader isReader = new InputStreamReader (contentStream);
			int contentSize = (int) response.getLength ();
			if (contentSize < 0)
				contentSize = 8*1024;
			StringWriter strWriter = new StringWriter (contentSize);
			char[] buffer = new char[8*1024];
			int n = 0;
			while ((n = isReader.read(buffer)) != -1) {
					strWriter.write(buffer, 0, n);
			}
			responseBody = strWriter.toString ();
			contentStream.close ();
		} catch (IOException e) {
			throw new WeatherException("IO Exception while getting weather data :" + e.getMessage());
		} catch (RuntimeException re) {
			throw new WeatherException("Runtime Exception while getting weather data :" + re.getMessage());
		} finally {
			if (contentStream != null)
				contentStream.close ();
		}

		return new JSONObject(responseBody);
	}
}
