package com.sahaj.hotelelectricitymanagement.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.sahaj.hotelelectricitymanagement.service.HotelService;
import com.sahaj.hotelelectricitymanagement.vo.AC;
import com.sahaj.hotelelectricitymanagement.vo.Floor;
import com.sahaj.hotelelectricitymanagement.vo.Hotel;
import com.sahaj.hotelelectricitymanagement.vo.Light;
import com.sahaj.hotelelectricitymanagement.vo.MainCorridor;
import com.sahaj.hotelelectricitymanagement.vo.SubCorridor;

@RunWith(SpringRunner.class)
@WebMvcTest(SensorAPIImpl.class)
public class SensorAPIImplTest {
	@Autowired
	private MockMvc mvc;

	@MockBean
	private HotelService hotelService;

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void postMovement() throws Exception {
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
		Hotel hotel = Hotel.builder().floors(Collections.singletonList(floor)).build();

		Mockito.when(hotelService.switchOnLight(1, 1)).thenReturn(hotel);
		mvc.perform(get("/floor/1/subcorridor/1")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}
}
