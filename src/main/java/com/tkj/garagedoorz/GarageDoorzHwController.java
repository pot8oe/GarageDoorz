package com.tkj.garagedoorz;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;

/**
 * GarageDoorzHwController manages an array of garage doors which are interfaces to through 
 * Raspberry Pi GPIO pins.
 * @author Thomas G. Kenny Jr.
 */
public final class GarageDoorzHwController {

	/**
	 * Attention! The GPIO pin numbering used in these constants is intended for use with
	 * WiringPi or Pi4J. This pin numbering is not the raw Broadcom GPIO pin numbers.
	 * http://pi4j.com/pins/model-zero-rev1.html
	 */
	public final static Pin Door1_Actuator_GPIO = RaspiPin.GPIO_15;
	public final static Pin Door2_Actuator_GPIO = RaspiPin.GPIO_16;
	public final static Pin Door1_Position_Sensor_GPIO = RaspiPin.GPIO_00;
	public final static Pin Door2_Position_Sensor_GPIO = RaspiPin.GPIO_02;
	public final static String Door1_Name = "Jill's Door";
	public final static String Door2_Name = "Tom's Door";
	
	/**
	 * Static members
	 */
	final static GpioController gpio = GpioFactory.getInstance();
	static boolean initialized = false;
	static GarageDoor[] doors;
	
	/**
	 * Represents a single garage door with a button and open/closed sensor.
	 * Hardware interface is Pi4J to access Raspberry Pi GPIO pins.
	 * @author Thomas G. Kenny Jr.
	 */
	public static class GarageDoor {
		String name;
		GpioPinDigitalOutput pinOutActuator;
		GpioPinDigitalInput pinInPosition;
		
		public GarageDoor(String doorName, Pin actuator, Pin position) {
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
			return pinInPosition.isLow();
		}
	}
	
	/**
	 * Returns true if given index is in valid for doors array.
	 * If doors is empty or null false is returned.
	 * @param index
	 * @return
	 */
	private static boolean isIndexValid(int index) {
		if(doors == null) return false;
		if(doors.length <= 0) return false;
		if(index < 0) return false;
		if(index >= doors.length) return false;
		return true;
	}
	
	
	/**
	 * Initializes Garage door GPIO hardware.
	 */
	public static void initializeHardware() {
		
		if(initialized) return;

		doors = new GarageDoor[] {
			new GarageDoor(Door1_Name, GarageDoorzHwController.Door1_Actuator_GPIO, GarageDoorzHwController.Door1_Position_Sensor_GPIO),
			new GarageDoor(Door2_Name, GarageDoorzHwController.Door2_Actuator_GPIO, GarageDoorzHwController.Door2_Position_Sensor_GPIO)
		};
		
        initialized = true;
	}
	
	/**
	 * Presses the garage door button.
	 * @param doorIndex
	 * @throws IndexOutOfBoundsException
	 */
	public static void pressDoorButton(int doorIndex) {
		if(!isIndexValid(doorIndex)) throw new IndexOutOfBoundsException();
		doors[doorIndex].pressDoorButton();
	}
	
	/**
	 * @param doorIndex
	 * @return True if door is open.
	 * @throws IndexOutOfBoundsException
	 */
	public static boolean isDoorOpen(int doorIndex) {
		if(!isIndexValid(doorIndex)) throw new IndexOutOfBoundsException();
		return doors[doorIndex].isDoorOpen();
	}
	
	/**
	 * @param doorIndex
	 * @return The requested GarageDoor object
	 */
	public static GarageDoor getGarageDoor(int doorIndex) {
		if(!isIndexValid(doorIndex)) throw new IndexOutOfBoundsException();
		return doors[doorIndex];
	}
	
	/**
	 * @return The array of GarageDoors.
	 */
	public static GarageDoor[] getGarageDoors() {
		return doors;
	}
	
	
}
