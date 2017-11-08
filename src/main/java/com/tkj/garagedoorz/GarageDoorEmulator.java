package com.tkj.garagedoorz;

import org.springframework.context.annotation.Profile;

@Profile("test")
public class GarageDoorEmulator implements IGarageDoor {
	
	private String name;
	private boolean isOpen;
	
	public GarageDoorEmulator(String doorName) {
		name = doorName;
		isOpen = false;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void pressDoorButton() {
		isOpen = !isOpen;
	}

	@Override
	public boolean isDoorOpen() {
		return isOpen;
	}

}
