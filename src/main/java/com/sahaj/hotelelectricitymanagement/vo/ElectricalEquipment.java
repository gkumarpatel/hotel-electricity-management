package com.sahaj.hotelelectricitymanagement.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

/***
 * General Electrical Equipment
 */
@NoArgsConstructor
@Data
public abstract class ElectricalEquipment {
	private int power;
	private boolean switchedOn;

	public ElectricalEquipment(int power, boolean switchedOn) {
		this.power = power;
		this.switchedOn = switchedOn;
	}

	@Override
	public String toString() {
		return ": " + (switchedOn ? "ON" : "OFF");
	}
}
