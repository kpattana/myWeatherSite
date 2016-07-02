package com.weathersite.model;

public class Weather {
	String date;
	String name;
	String description;
	String celsius;
	String fahrenheit;
	String sunrise;
	String sunset;


	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void setCelsius(String celsius) {
		this.celsius = celsius;
	}

	public void setFahrenheit(String fahrenheit) {
		this.fahrenheit = fahrenheit;
	}
	public String getSunrise() {
		return sunrise;
	}
	public void setSunrise(String sunrise) {
		this.sunrise = sunrise;
	}
	public String getSunset() {
		return sunset;
	}
	public void setSunset(String sunset) {
		this.sunset = sunset;
	}

	public String getCelsius() {
		return celsius;
	}

	public String getFahrenheit() {
		return fahrenheit;
	}
}
