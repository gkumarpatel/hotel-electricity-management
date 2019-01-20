package com.sahaj.hotelelectricitymanagement.vo;

import lombok.Builder;

/**
 * Light with power consumption of 5 units
 */
public class Light extends ElectricalEquipment {
	public Light() {
		super(5, false);
	}

	@Builder
	public Light(boolean switchedOn) {
		super(5, switchedOn);
	}

	@Override
	public String toString() {
		return "Light " + super.toString();
	}
}
