package com.sahaj.hotelelectricitymanagement.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *  Corridor abstraction
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public abstract class Corridor {
	private int id;
	private Light light;
	private AC ac;

	@Override
	public String toString() {
		return id + " " + light + " " + ac + "\n";
	}

	public int currentUsage() {
		return (getAc().isSwitchedOn() ? 10 : 0) + (getLight().isSwitchedOn() ? 5 : 0);
	}
}
