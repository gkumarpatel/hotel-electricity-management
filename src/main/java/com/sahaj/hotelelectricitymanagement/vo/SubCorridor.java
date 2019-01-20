package com.sahaj.hotelelectricitymanagement.vo;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

/***
 * Sub Corridor
 */
@Data
public class SubCorridor extends Corridor {
	/**
	 * Time on which the last motion on the sub corridor is detected.
	 * Used to identify an idle sub corridor
	 */
	private LocalDateTime lastMotionDetectedOn;

	@Builder
	public SubCorridor(int id, Light light, AC ac) {
		super(id, light, ac);
		lastMotionDetectedOn = LocalDateTime.now();
	}

	@Override
	public String toString() {
		return "Sub Corridor " + super.toString();
	}

}
