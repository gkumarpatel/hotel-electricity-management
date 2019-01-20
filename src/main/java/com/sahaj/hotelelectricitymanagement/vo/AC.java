package com.sahaj.hotelelectricitymanagement.vo;

import lombok.Builder;

/**
 * AC with power consumption of 10 Unit
 */
public class AC extends ElectricalEquipment {
	@Builder
	public AC() {
		super(10, true);
	}

	@Override
	public String toString() {
		return "AC " + super.toString();
	}
}
