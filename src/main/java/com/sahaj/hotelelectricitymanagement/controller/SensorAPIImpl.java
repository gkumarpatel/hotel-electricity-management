package com.sahaj.hotelelectricitymanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.sahaj.hotelelectricitymanagement.service.HotelService;
import com.sahaj.hotelelectricitymanagement.vo.Hotel;

@RestController
public class SensorAPIImpl implements SensorAPI {
	@Autowired
	HotelService hotelService;

	@Override
	public Hotel postMovement(int floorId, int subcorridorId) {
		return hotelService.switchOnLight(floorId, subcorridorId);
	}
}
