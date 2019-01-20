package com.sahaj.hotelelectricitymanagement.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import lombok.extern.slf4j.Slf4j;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import com.sahaj.hotelelectricitymanagement.vo.AC;
import com.sahaj.hotelelectricitymanagement.vo.Floor;
import com.sahaj.hotelelectricitymanagement.vo.Hotel;
import com.sahaj.hotelelectricitymanagement.vo.Light;
import com.sahaj.hotelelectricitymanagement.vo.MainCorridor;
import com.sahaj.hotelelectricitymanagement.vo.SubCorridor;

/***
 * Config to create various beans used in the hotel management
 */
@Slf4j
@Configuration
public class HotelConfig {

	/***
	 * File scanner for reading initial Hotel configuration and motion in the sub corridors
	 * @return {@link Scanner}
	 * @throws IOException
	 */
	@Bean
	Scanner fileScanner() throws IOException {
		Resource resource = new ClassPathResource("HotelInput.txt");
		InputStream resourceInputStream = resource.getInputStream();
		return new Scanner(resourceInputStream);
	}

	/***
	 * Bean to define the hotel
	 * @param fileScanner
	 * @return {@link Hotel}
	 */
	@Bean
	Hotel hotel(Scanner fileScanner) {
		int noOfFloors = Integer.parseInt(fileScanner.next());
		int mainCorridorPerFloor = Integer.parseInt(fileScanner.next());
		int subCorridorPerFloor = Integer.parseInt(fileScanner.next());

		List<Floor> floors = new ArrayList<>(noOfFloors);
		for (int i = 0; i < noOfFloors; i++) {
			List<MainCorridor> mainCorridors = new ArrayList<>(mainCorridorPerFloor);
			List<SubCorridor> subCorridors = new ArrayList<>(subCorridorPerFloor);
			for (int j = 0; j < mainCorridorPerFloor; j++) {
				mainCorridors.add(MainCorridor
						.builder()
						.id(j + 1)
						.light(Light.builder().switchedOn(true).build())
						.ac(AC.builder().build())
						.build());
			}
			for (int j = 0; j < subCorridorPerFloor; j++) {
				subCorridors.add(SubCorridor
						.builder()
						.id(j + 1)
						.light(Light.builder().switchedOn(false).build())
						.ac(AC.builder().build())
						.build());
			}
			Floor floor = Floor
					.builder()
					.id(i + 1)
					.mainCorridors(mainCorridors)
					.subCorridors(subCorridors)
					.build();
			log.info("Max power consuption for floor {} is {}", floor.getId(), floor.getMaxPowerUsage());
			floors.add(floor);
		}

		Hotel hotel = Hotel.builder().floors(floors)
				.build();
		log.info(hotel.toString());
		return hotel;
	}

	/***
	 * Scheduler to detect idle sub corridors and temporarily used for reading sub corridor motions as well
	 * @param hotel
	 * @return {@link ThreadPoolTaskScheduler}
	 */
	@Bean
	public ThreadPoolTaskScheduler threadPoolTaskScheduler(Hotel hotel) {
		int THREADS_COUNT = hotel.getFloors().size() * hotel.getFloors().get(0).getSubCorridors().size();
		ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
		threadPoolTaskScheduler.setPoolSize(THREADS_COUNT);
		return threadPoolTaskScheduler;
	}
}
