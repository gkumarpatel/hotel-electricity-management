package com.sahaj.hotelelectricitymanagement.vo;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Hotel {
	/**
	 * Floors in a Hotel
	 * As the number of floor per floor doesn't change, I should have created floors as an Array of floors
	 */
	private List<Floor> floors;

	@Override
	public String toString() {
		StringBuilder toStringBuilder = new StringBuilder();
		for (Floor f : floors) {
			toStringBuilder.append(f.toString());
		}
		return toStringBuilder.toString();
	}
}

