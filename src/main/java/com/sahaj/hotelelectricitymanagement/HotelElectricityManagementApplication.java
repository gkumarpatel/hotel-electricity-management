package com.sahaj.hotelelectricitymanagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.context.support.GenericWebApplicationContext;

@SpringBootApplication
public class HotelElectricityManagementApplication {

	@Autowired
	private static GenericWebApplicationContext context;

	public static void main(String[] args) {
		SpringApplication.run(HotelElectricityManagementApplication.class, args);
	}
}

