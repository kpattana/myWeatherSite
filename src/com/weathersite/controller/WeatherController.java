package com.weathersite.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.weathersite.exception.WeatherException;
import com.weathersite.model.City;
import com.weathersite.model.Weather;
import com.weathersite.service.WeatherService;

@Controller
@RequestMapping("/")
public class WeatherController {

	/*
	 * This method will serve as default GET handler.
	 * Loads welcome page with a select box to choose city
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String loadWelcome(ModelMap model) {
		City city = new City();
		model.addAttribute("city", city);
		String message = "<br><div style='text-align:center;'>"
				+ "<h3>Weather App</h3>Details sourced from openweathermap.org</div><br><br>";
		model.addAttribute("message", message);
		model.addAttribute("display", "2");
		return "welcome" ; 
	}

	/*
	 * This method will be called on form submission, handling POST request
	 * Displays weather details for city selected or any errors received
	 */
	@RequestMapping(method = RequestMethod.POST)
	public String fetchWeatherForecast(City city, BindingResult result, ModelMap model){

		String message = "<br><div style='text-align:center;'>"
				+ "<h3>Weather App</h3>Details sourced from openweathermap.org</div><br><br>";
		model.addAttribute("message", message);
		try {
			if (city != null && !city.getName().trim().equals("")) {
				Weather weatherObj;	
				weatherObj = new WeatherService().getWeatherForCity(city.getName());
				if (weatherObj != null) {

					model.addAttribute("date", weatherObj.getDate());
					model.addAttribute("cityName", weatherObj.getName());
					model.addAttribute("forecast", weatherObj.getDescription());
					model.addAttribute("celsius", weatherObj.getCelsius());
					model.addAttribute("faranite", weatherObj.getFahrenheit());
					model.addAttribute("sunrise", weatherObj.getSunrise());
					model.addAttribute("sunset", weatherObj.getSunset());
					model.addAttribute("display", "1");

				} else {
					//could do with an error page showing message but for this solution displaying in the same page
					model.addAttribute("display", "0");
					model.addAttribute("error", "No data returned for city selected.");						
				}
			} else {
				model.addAttribute("display", "0");
				model.addAttribute("error", "Invalid city selected.");
			}
		} catch (WeatherException e) {
			model.addAttribute("display", "0");
			model.addAttribute("error", e.getMessage());
		}
		return "welcome";
	}

	/*
	 * Method used to populate the city list to be selected for weather.
	 * Hardcoded values, can be sourced externally from db etc.
	 */
	@ModelAttribute("cities")
	public List<String> initializeCountries() {

		List<String> cities = new ArrayList<String>();
		cities.add("London");
		cities.add("Hong Kong");
		cities.add("Paris");
		return cities;
	}
}
