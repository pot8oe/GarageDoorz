package com.tkj.garagedoorz;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controler that maps REST endpoints to hardware control.
 * @author Thomas G. Kenny Jr
 *
 */
@RestController
public class GarageDoorzController {
	
	@RequestMapping("/")
	public String HelloDoorz() {
		return "Hello Doorz";
	}
	
	@RequestMapping("/getGarageStatus")
	public GarageStatus getGarageStatus() {
		return new GarageStatus(GarageDoorzHwController.getGarageDoors());
	}
	
	@RequestMapping("/isDoorOpen")
	public int isDoorOpen(@RequestParam(value="doorIndex", defaultValue="0") int doorIndex) {
		
		return 0;
	}
	
	@RequestMapping("/toggleDoor")
	public boolean toggleDoor(@RequestParam(value="doorIndex", defaultValue="0") int doorIndex) {
		return false;
	}
	
	@RequestMapping("/openDoor")
	public boolean openDoor(@RequestParam(value="doorIndex", defaultValue="0") int doorIndex) {
		return false;
	}

	@RequestMapping("/closeDoor")
	public boolean closeDoor(@RequestParam(value="doorIndex", defaultValue="0") int doorIndex) {
		return false;
	}

}
