package com.tkj.garagedoorz;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller that maps REST end points to Garage door hardware status and control.
 * @author Thomas G. Kenny Jr
 *
 */
@RestController
public class GarageDoorzController {
	
	/**
	 * @return Hello Doorz
	 */
	@RequestMapping("/")
	public String HelloDoorz() {
		return "Hello Doorz";
	}
	
	/**
	 * @return The status of the Garage.
	 */
	@RequestMapping("/getGarageStatus")
	public GarageStatus getGarageStatus() {
		return new GarageStatus(GarageDoorzHwController.getGarageDoors());
	}
	
	/**
	 * Return if the door is open. At present we do not have any in motion state information.
	 * @param doorIndex Index of door to read open state. Defaults to 0 (first door).
	 * @return True if door is not fully closed.
	 */
	@RequestMapping("/isDoorOpen")
	public boolean isDoorOpen(@RequestParam(value="doorIndex", defaultValue="0") int doorIndex) {
		return GarageDoorzHwController.isDoorOpen(doorIndex);
	}
	
	/**
	 * Press the requested garage door button.
	 * @param doorIndex Index of door to actuate. Defaults to 0 (first door).
	 */
	@RequestMapping("/pressDoorButton")
	public void toggleDoor(@RequestParam(value="doorIndex", defaultValue="0") int doorIndex) {
		GarageDoorzHwController.pressDoorButton(doorIndex);
	}
	
	/**
	 * Opens the requested door if isOpen reports false. This function can erroneously stop the door if it
	 * is in motion when called. Door isOpen reports true as soon as the door is not closed and the
	 * state of motion is unknown at this time.
	 * @param doorIndex Index of door to actuate. Defaults to 0 (first door).
	 * @return
	 */
	@RequestMapping("/openDoor")
	public boolean openDoor(@RequestParam(value="doorIndex", defaultValue="0") int doorIndex) {
		if(!GarageDoorzHwController.isDoorOpen(doorIndex)) {
			GarageDoorzHwController.pressDoorButton(doorIndex);
			return true;
		}
		return false;
	}

	/**
	 * Closes the requested door if isOpen reports true. This function can erroneously stop the door if it
	 * is in motion when called. Door isOpen reports true as soon as the door is not closed and the
	 * state of motion is unknown at this time.
	 * @param doorIndex Index of door to actuate. Defaults to 0 (first door).
	 * @return
	 */
	@RequestMapping("/closeDoor")
	public boolean closeDoor(@RequestParam(value="doorIndex", defaultValue="0") int doorIndex) {
		if(GarageDoorzHwController.isDoorOpen(doorIndex)) {
			GarageDoorzHwController.pressDoorButton(doorIndex);
			return true;
		}
		return false;
	}

}
