package com.tkj.garagedoorz;

import com.pi4j.io.gpio.Pin;

public interface IGarageDoor {
	
	/**
	 * Gets the garage door name
	 * @return
	 */
	public String getName();
	
	/**
	 * Presses the garage door button
	 */
	public void pressDoorButton();
	
	/**
	 * Returns if the garage door is open
	 * @return
	 */
	public boolean isDoorOpen();
}
