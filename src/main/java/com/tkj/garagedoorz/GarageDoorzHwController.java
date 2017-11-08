package com.tkj.garagedoorz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.RaspiPin;

/**
 * GarageDoorzHwController manages an array of garage doors which are interfaces to through 
 * Raspberry Pi GPIO pins.
 * @author Thomas G. Kenny Jr.
 */
public final class GarageDoorzHwController {
	 
	@Autowired
	static Environment environment;
	
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
	static boolean initialized = false;
	static IGarageDoor[] doors;
	
	
	
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
		
		if(environment.acceptsProfiles("hw_rpi")) {
			doors = new IGarageDoor[] {
					new GarageDoorRPi(Door1_Name, GarageDoorzHwController.Door1_Actuator_GPIO, GarageDoorzHwController.Door1_Position_Sensor_GPIO),
					new GarageDoorRPi(Door2_Name, GarageDoorzHwController.Door2_Actuator_GPIO, GarageDoorzHwController.Door2_Position_Sensor_GPIO)
				};
		} else {
			doors = new IGarageDoor[] {
					new GarageDoorEmulator(Door1_Name),
					new GarageDoorEmulator(Door2_Name)
				};
		}

		
		
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
	public static IGarageDoor getGarageDoor(int doorIndex) {
		if(!isIndexValid(doorIndex)) throw new IndexOutOfBoundsException();
		return doors[doorIndex];
	}
	
	/**
	 * @return The array of GarageDoors.
	 */
	public static IGarageDoor[] getGarageDoors() {
		return doors;
	}
	
	
}
