package com.sahaj.hotelelectricitymanagement.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sahaj.hotelelectricitymanagement.vo.Floor;
import com.sahaj.hotelelectricitymanagement.vo.Hotel;
import com.sahaj.hotelelectricitymanagement.vo.SubCorridor;

/***
 * Service layer to handle hotel's business login
 */
@Slf4j
@Component
public class HotelServiceImpl implements HotelService {

	@Autowired
	private Hotel hotel;

	@Autowired
	private Scanner fileScanner;

	@Override
	public Hotel switchOnLight(int floorId, int subcorridorId) {
		log.info("Movement observed in floor {}, sub corridor {}", floorId, subcorridorId);
		Floor floor = hotel.getFloors().get(floorId - 1);
		SubCorridor subCorridor = floor.getSubCorridors().get(subcorridorId - 1);
		if (floor.getMaxPowerUsage() - floor.getCurrentUsage() >= subCorridor.getLight().getPower()) {
			subCorridor.getLight().setSwitchedOn(true);
			subCorridor.setLastMotionDetectedOn(LocalDateTime.now());
		} else if (floor.switchOffAnAC(subcorridorId)) {
			subCorridor.getLight().setSwitchedOn(true);
			subCorridor.setLastMotionDetectedOn(LocalDateTime.now());
		} else {
			//with current formula for floor max power usage this condition will not occure
			log.error("Power consumption for the floor {} is ");
		}
		return hotel;
	}

	@Override
	public Hotel monitorSubcorridorLights(int floorId, int subcorridorId) {
		Floor floor = hotel.getFloors().get(floorId - 1);
		SubCorridor subCorridor = floor.getSubCorridors().get(subcorridorId - 1);
		if (subCorridor.getLight().isSwitchedOn() && LocalDateTime.now().minusMinutes(1L).isAfter(subCorridor.getLastMotionDetectedOn())) {
			log.info("Idle subcorridor detected on floor {} : sub corridor {}", floorId, subcorridorId);
			subCorridor.getLight().setSwitchedOn(false);
			if (subCorridor.getAc().isSwitchedOn()) {
				//find subcorridor with light on and AC off
				List<SubCorridor> acOffLightONsubCorridors = floor.getSubCorridors()
						.stream()
						.filter(sc -> sc.getLight().isSwitchedOn() && !sc.getAc().isSwitchedOn())
						.collect(Collectors.toList());
				if (!acOffLightONsubCorridors.isEmpty()) {

				}
			}
			// as all the subcorridors are monitored concurrently, we need to switch on an AC in synchronised block
			if (floor.getMaxPowerUsage() - floor.getCurrentUsage() >= subCorridor.getAc().getPower()) {
				synchronized (floor) {
					if (floor.getMaxPowerUsage() - floor.getCurrentUsage() >= subCorridor.getAc().getPower()) {
						floor.switchOnAnAC(subcorridorId);
					}
				}
			}
			log.info(hotel.toString());
		}
		return hotel;
	}

	@Override
	public void readMotion() {
		if (fileScanner.hasNext()) {
			int floorId = Integer.parseInt(fileScanner.next());
			int subCorridorId = Integer.parseInt(fileScanner.next());
			Hotel hotel = switchOnLight(floorId, subCorridorId);
			log.info(hotel.toString());
		}
	}
}
