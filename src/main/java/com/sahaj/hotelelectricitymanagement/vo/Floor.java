package com.sahaj.hotelelectricitymanagement.vo;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Floor {
	/**
	 * Floor identifier
	 */
	private int id;

	/**
	 * Main Corridors on a floor
	 * as the number of corridors per floor doesn't change, I should have created both sub and main corridor as an Array of floors.
	 * Or if this corridors are not made of concrete or if made of ply or POP, there are chances that this may change during hotel renovation
	 */
	private List<MainCorridor> mainCorridors;

	/**
	 * Sub Corridors on a floor
	 * as the number of corridors per floor doesn't change, I should have created both sub and main corridor as an Array of floors.
	 * Or if this corridors are not made of concrete or if made of ply or POP, there are chances that this may change during hotel renovation
	 */
	private List<SubCorridor> subCorridors;

	/**
	 * Max power consumption allowed per floor at an instance of time
	 */
	private int maxPowerUsage;

	@Builder
	public Floor(int id, List<MainCorridor> mainCorridors, List<SubCorridor> subCorridors) {
		this.id = id;
		this.mainCorridors = mainCorridors;
		this.subCorridors = subCorridors;
		maxPowerUsage = (mainCorridors.size() * 15) + (subCorridors.size() * 10);
	}

	@Override
	public String toString() {
		StringBuilder toStringBuilder = new StringBuilder("\nFloor ").append(id).append("\n");
		for (MainCorridor mc : mainCorridors) {
			toStringBuilder.append(mc.toString());
		}
		for (SubCorridor sc : subCorridors) {
			toStringBuilder.append(sc.toString());
		}
		toStringBuilder.append("Floor Consumption : ").append(getCurrentUsage()).append("\n");
		return toStringBuilder.toString();
	}

	/**
	 * Method to calculate the current power consumption
	 *
	 * @return sum of current power consumption of main and sub corridors on the floor
	 */
	public int getCurrentUsage() {
		return mainCorridors.stream().mapToInt(Corridor::currentUsage).sum()
				+ subCorridors.stream().mapToInt(Corridor::currentUsage).sum();
	}

	/**
	 * Switch offs AC of a sub corridor other then @param subcorridorId. If no such sub corridor found, which off AC of the current sub corridor
	 *
	 * @param subcorridorId
	 * @return true if a light is switched off in a sub corridor
	 */
	public boolean switchOffAnAC(int subcorridorId) {
		List<SubCorridor> switchOnACSubCorridors = subCorridors.stream().filter(sc -> sc.getId() != subcorridorId && sc.getAc().isSwitchedOn()).collect(Collectors.toList());
		SubCorridor subCorridor = subCorridors.stream().filter(sc -> sc.getId() == subcorridorId).collect(Collectors.toList()).get(0);
		if (!switchOnACSubCorridors.isEmpty()) {
			switchOnACSubCorridors.get(0).getAc().setSwitchedOn(false);
			return true;
		} else if (subCorridor.getAc().isSwitchedOn()) {
			subCorridor.getAc().setSwitchedOn(false);
			return true;
		} else {
			return false;
		}
	}

	/***
	 * Switch On AC of a sub corridor with subcorridorId. If it;s already on, switch on AC of other corridors
	 * @param subcorridorId
	 * @return true if a light is switched on in a sub corridor
	 */
	public boolean switchOnAnAC(int subcorridorId) {
		SubCorridor subCorridor = subCorridors.stream().filter(sc -> sc.getId() == subcorridorId).collect(Collectors.toList()).get(0);
		List<SubCorridor> switchOffACSubCorridors = subCorridors.stream().filter(sc -> sc.getId() != subcorridorId && !sc.getAc().isSwitchedOn()).collect(Collectors.toList());
		if (!subCorridor.getAc().isSwitchedOn()) {
			subCorridor.getAc().setSwitchedOn(true);
			return true;
		} else if (!switchOffACSubCorridors.isEmpty()) {
			switchOffACSubCorridors.get(0).getAc().setSwitchedOn(true);
			return true;
		} else {
			return false;
		}
	}
}
