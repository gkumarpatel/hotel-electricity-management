package com.sahaj.hotelelectricitymanagement.config;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

import com.sahaj.hotelelectricitymanagement.service.HotelService;
import com.sahaj.hotelelectricitymanagement.vo.Hotel;

/***
 * Responsible for scheduling idle sub corridor detection and temporarily responsible for reading motion in a sub corridor from a file
 */
@Component
public class SchedulerHelper {
	@Autowired
	private Hotel hotel;

	@Autowired
	private ThreadPoolTaskScheduler threadPoolTaskScheduler;

	@Autowired
	HotelService hotelService;

	/***
	 * Schedules the scheduler to monitor idle sub corridor
	 */
	@PostConstruct
	public void schedule() {
		hotel
				.getFloors()
				.forEach(f -> f.getSubCorridors()
						.forEach(sc -> threadPoolTaskScheduler.scheduleAtFixedRate(() -> hotelService.monitorSubcorridorLights(f.getId(), sc.getId()), 1000L)));
	}

	/***
	 * Schedules the scheduler to read sub corridor motions from a file
	 */
	@PostConstruct
	public void scheduleMotions() {
		threadPoolTaskScheduler.scheduleAtFixedRate(() -> hotelService.readMotion(), 10000L);
	}

}
