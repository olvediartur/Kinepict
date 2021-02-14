package com.weather.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.weather.service.LocationService;

@SpringBootApplication
public class KinepictApplication {

	private static Logger logger = LoggerFactory.getLogger(KinepictApplication.class);

	public static void main(String[] args) throws Throwable {
		SpringApplication.run(KinepictApplication.class, args);
		logger.info("This program reads a city temperature from OpenWeather.");
		LocationService service = new LocationService();

		logger.info(String.format("The temperature in %s is %.2f Celsius", service.getLocation().getName(),
				service.getLocation().getMain().getTemp()));
	}

}
