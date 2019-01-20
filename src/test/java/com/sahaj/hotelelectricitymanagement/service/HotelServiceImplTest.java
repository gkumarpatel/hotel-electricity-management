package com.sahaj.hotelelectricitymanagement.service;

import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Collections;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.sahaj.hotelelectricitymanagement.vo.AC;
import com.sahaj.hotelelectricitymanagement.vo.Floor;
import com.sahaj.hotelelectricitymanagement.vo.Hotel;
import com.sahaj.hotelelectricitymanagement.vo.Light;
import com.sahaj.hotelelectricitymanagement.vo.MainCorridor;
import com.sahaj.hotelelectricitymanagement.vo.SubCorridor;

public class HotelServiceImplTest {
	@Mock
	private Hotel hotel;

	@InjectMocks
	HotelServiceImpl hotelService;

	@Before
	public void setUp() throws Exception {
		hotelService = new HotelServiceImpl();
		MockitoAnnotations.initMocks(this);
		MainCorridor mainCorridor = MainCorridor
				.builder()
				.id(1)
				.light(Light.builder().switchedOn(true).build())
				.ac(AC.builder().build())
				.build();
		SubCorridor subCorridor = SubCorridor
				.builder()
				.id(1)
				.light(Light.builder().switchedOn(false).build())
				.ac(AC.builder().build())
				.build();
		Floor floor = Floor
				.builder()
				.id(1)
				.mainCorridors(Collections.singletonList(mainCorridor))
				.subCorridors(Collections.singletonList(subCorridor))
				.build();
		when(hotel.getFloors()).thenReturn(Collections.singletonList(floor));
	}

	@Test
	public void switchOnLight() {
		Hotel updatedHotel = hotelService.switchOnLight(1, 1);
		Assert.assertEquals(updatedHotel.getFloors(), hotel.getFloors());
	}

	@Test
	public void monitorSubcorridorLights() {
		hotel.getFloors().get(0).getSubCorridors().get(0).getLight().setSwitchedOn(true);
		hotel.getFloors().get(0).getSubCorridors().get(0).setLastMotionDetectedOn(LocalDateTime.now().minusMinutes(2L));
		Hotel updateHotel = hotelService.monitorSubcorridorLights(1, 1);
		Assert.assertEquals(updateHotel.getFloors().get(0).getSubCorridors().get(0).getLight().isSwitchedOn(), false);
	}

	@Test
	public void monitorSubcorridorLights_failure() {
		hotel.getFloors().get(0).getSubCorridors().get(0).getLight().setSwitchedOn(true);
		hotel.getFloors().get(0).getSubCorridors().get(0).setLastMotionDetectedOn(LocalDateTime.now());
		Hotel updateHotel = hotelService.monitorSubcorridorLights(1, 1);
		Assert.assertEquals(updateHotel.getFloors().get(0).getSubCorridors().get(0).getLight().isSwitchedOn(), true);
	}

}
