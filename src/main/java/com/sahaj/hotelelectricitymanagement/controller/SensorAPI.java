package com.sahaj.hotelelectricitymanagement.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.sahaj.hotelelectricitymanagement.vo.Hotel;

/***
 * Rest API to post the sensor data for any movement in any subcorridor
 * Not using API in this assignment instead taking input from HotelInput.txt
 */
public interface SensorAPI {
	@GetMapping("/floor/{floorId}/subcorridor/{subcorridorId}")
	Hotel postMovement(@PathVariable int floorId, @PathVariable int subcorridorId);
}
