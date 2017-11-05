package com.example.garagedoorz;

public class GarageStatus {
	
	private final int dId;
	private final int dStatus;
	
	public GarageStatus(int doorId, int doorStatus) {
		dId = doorId;
		dStatus = doorStatus;
	}

	/**
	 * @return the Door Id
	 */
	public int getDoorId() {
		return dId;
	}
	
	/**
	 * @return the Door Status
	 */
	public int getDoorStatus() {
		return dStatus;
	}
	

}
