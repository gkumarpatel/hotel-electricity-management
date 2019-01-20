package com.sahaj.hotelelectricitymanagement.vo;

import lombok.Builder;
import lombok.Data;

/**
 * Main corridor
 */
@Data
public class MainCorridor extends Corridor {
	@Builder
	public MainCorridor(int id, Light light, AC ac) {
		super(id, light, ac);
	}

	@Override
	public String toString() {
		return "Main Corridor " + super.toString();
	}
}
