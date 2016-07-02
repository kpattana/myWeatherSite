package com.weathersite.exception;

public class WeatherException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2212787245990230928L;

	public WeatherException(String message) {
		super(message);
	}
	
	public WeatherException(Throwable cause){
		super(cause);
	}
	
	public WeatherException(String message, Throwable cause) {
		super(message, cause);
	}
}
