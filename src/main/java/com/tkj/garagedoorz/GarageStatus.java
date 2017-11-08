package com.tkj.garagedoorz;

import org.springframework.stereotype.Component;

/**
 * GarageStatus Model represents the state of all doors in a garage.
 * @author Thomas G. Kenny Jr.
 */
@Component
public class GarageStatus {
	
	/**
	 * DoorStatus model represents the state of a garage door.
	 * @author Thomas G. Kenny Jr.
	 */
	public class DoorStatus {
		final private String name;
		final private boolean isOpen;
		
		public DoorStatus(String doorName, boolean isDoorOpen) {
			name = doorName;
			isOpen = isDoorOpen;
		}
		
		public String getDoorName() {
			return name;
		}
		
		public boolean isDoorOpen() {
			return isOpen;
		}
	}
	
	
	/**
	 * Array of booleans representing if doors are open
	 */
	private final DoorStatus[] doorStatuses;
	
	
	/**
	 * Constructs a GarageStatus from an array of GarageDoor Hardware objects.
	 * The hardware state of each door is read during construction.
	 * @param doors
	 */
	public GarageStatus(IGarageDoor[] doors) {
		doorStatuses = new DoorStatus[doors.length];
		
		for(int i=0; i<doors.length; i++) {
			doorStatuses[i] = new DoorStatus(doors[i].getName(), doors[i].isDoorOpen());
		}
	}


}
