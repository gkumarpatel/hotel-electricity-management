package com.sahaj.hotelelectricitymanagement.service;

import com.sahaj.hotelelectricitymanagement.vo.Hotel;

public interface HotelService {
	Hotel switchOnLight(int floorId, int subcorridorId);

	Hotel monitorSubcorridorLights(int floorId, int subcorridorId);

	void readMotion();
}
