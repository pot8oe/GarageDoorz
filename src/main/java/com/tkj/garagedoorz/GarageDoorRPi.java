package com.tkj.garagedoorz;

import org.springframework.context.annotation.Profile;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.PinState;

/**
 * Represents a single garage door with a button and open/closed sensor.
 * Hardware interface is Pi4J to access Raspberry Pi GPIO pins.
 * @author Thomas G. Kenny Jr.
 */
@Profile("hw_rpi")
public class GarageDoorRPi implements IGarageDoor {
	
	final static GpioController gpio = GpioFactory.getInstance();

	String name;
	GpioPinDigitalOutput pinOutActuator;
	GpioPinDigitalInput pinInPosition;
	
	public GarageDoorRPi(String doorName, Pin actuator, Pin position) {
		name = doorName;
		pinOutActuator = gpio.provisionDigitalOutputPin(
				actuator, name + "_actuator", PinState.LOW);
		pinOutActuator.setShutdownOptions(true, PinState.LOW);
		pinInPosition = gpio.provisionDigitalInputPin(
        		position, name + "_position", PinPullResistance.PULL_UP);
	}
	
	/**
	 * Gets the garage door name
	 * @return
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Presses the garage door button
	 */
	public void pressDoorButton() {
		pinOutActuator.high();
		try {
			Thread.sleep(250);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		pinOutActuator.low();
	}
	
	/**
	 * Returns if the garage door is open
	 * @return
	 */
	public boolean isDoorOpen() {
		return pinInPosition.isHigh();
	}

}
