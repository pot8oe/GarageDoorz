package com.tkj.garagedoorz.endpoint;

import com.tkj.garagedoorz.domain.DoorStatus;
import com.tkj.garagedoorz.domain.HwController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST controler that maps REST endpoints to hardware control.
 * @author Thomas G. Kenny Jr
 *
 */
@RestController
public class GarageDoorzController {

	private final HwController hwController;

	public GarageDoorzController( final HwController hwController ) {

		this.hwController = hwController;

	}

	@RequestMapping ("/" )
	public String HelloDoorz() {

		return "Hello Doorz";
	}
	
	@RequestMapping( "/getGarageStatus" )
	public List<DoorStatus> getGarageStatus() {

		return hwController.getGarageDoorStatuses();
	}
	
	@RequestMapping( "/{door}/isDoorOpen" )
	public boolean isDoorOpen( @PathVariable int door ) {
		
		return hwController.isDoorOpen( door );
	}
	
	@RequestMapping( "/{door}/toggleDoor" )
	@ResponseStatus( HttpStatus.ACCEPTED )
	public void toggleDoor( @PathVariable int door ) {

		hwController.pressDoorButton( door );

	}
	
	@RequestMapping( "/{door}/openDoor" )
	@ResponseStatus( HttpStatus.ACCEPTED )
	public void openDoor( @PathVariable int door ) {


	}

	@RequestMapping( "/{door}/closeDoor" )
	@ResponseStatus( HttpStatus.ACCEPTED )
	public void closeDoor( @PathVariable int door ) {

	}

}
