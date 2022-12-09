package com.tkj.garagedoorz.adapter.in;

import com.tkj.garagedoorz.application.in.*;
import com.tkj.garagedoorz.application.in.CloseDoorUseCase.CloseDoorCommand;
import com.tkj.garagedoorz.application.in.GetDoorStatusesUseCase.GetDoorStatusesCommand;
import com.tkj.garagedoorz.application.in.IsDoorOpenUseCase.IsDoorOpenCommand;
import com.tkj.garagedoorz.application.in.OpenDoorUseCase.OpenDoorCommand;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Controller that maps REST end points to Garage door hardware status and control.
 * @author Thomas G. Kenny Jr
 *
 */
@RestController
public class GarageDoorzController {

	private final OpenDoorUseCase openDoorUseCase;
	private final CloseDoorUseCase closeDoorUseCase;
	private final ToggleDoorUseCase toggleDoorUseCase;
	private final IsDoorOpenUseCase isDoorOpenUseCase;
	private final GetDoorStatusesUseCase getDoorStatusesUseCase;

	GarageDoorzController(
			final OpenDoorUseCase openDoorUseCase,
			final CloseDoorUseCase closeDoorUseCase,
			final ToggleDoorUseCase toggleDoorUseCase,
			final IsDoorOpenUseCase isDoorOpenUseCase,
			final GetDoorStatusesUseCase getDoorStatusesUseCase
	) {

		this.openDoorUseCase = openDoorUseCase;
		this.closeDoorUseCase = closeDoorUseCase;
		this.toggleDoorUseCase = toggleDoorUseCase;
		this.isDoorOpenUseCase = isDoorOpenUseCase;
		this.getDoorStatusesUseCase = getDoorStatusesUseCase;

	}

	/**
	 * @return Hello Doorz
	 */
	@GetMapping( "/" )
	public String HelloDoorz() {

		return "Hello Doorz";
	}
	
	/**
	 * @return The status of the Garage.
	 */
	@GetMapping( "/getGarageStatus" )
	public GarageStatus getGarageStatus() {

		var statuses = this.getDoorStatusesUseCase.execute( new GetDoorStatusesCommand() ).entrySet().stream()
				.map( entry -> new DoorStatus( entry.getKey(), entry.getValue() ) )
				.collect( toList() );

		return new GarageStatus( statuses );
	}
	
	/**
	 * Return if the door is open. At present we do not have any in motion state information.
	 * @param doorIndex Index of door to read open state. Defaults to 0 (first door).
	 * @return True if door is not fully closed.
	 */
	@GetMapping( "/isDoorOpen" )
	public boolean isDoorOpen( @RequestParam( value="doorIndex", defaultValue="0" ) String doorIndex ) {

		return this.isDoorOpenUseCase.execute( new IsDoorOpenCommand( doorIndex ) );
	}
	
	/**
	 * Press the requested garage door button.
	 * @param doorIndex Index of door to actuate. Defaults to 0 (first door).
	 */
	@GetMapping( "/pressDoorButton" )
	public void toggleDoor( @RequestParam( value="doorIndex", defaultValue="0" ) String doorIndex ) {

		this.toggleDoorUseCase.execute( new ToggleDoorUseCase.ToggleDoorCommand( doorIndex ) );

	}
	
	/**
	 * Opens the requested door if isOpen reports false. This function can erroneously stop the door if it
	 * is in motion when called. Door isOpen reports true as soon as the door is not closed and the
	 * state of motion is unknown at this time.
	 * @param doorIndex Index of door to actuate. Defaults to 0 (first door).
	 * @return
	 */
	@GetMapping( "/openDoor" )
	public boolean openDoor( @RequestParam( value="doorIndex", defaultValue="0" ) String doorIndex ) {

		return this.openDoorUseCase.execute( new OpenDoorCommand( doorIndex ) );
	}

	/**
	 * Closes the requested door if isOpen reports true. This function can erroneously stop the door if it
	 * is in motion when called. Door isOpen reports true as soon as the door is not closed and the
	 * state of motion is unknown at this time.
	 * @param doorIndex Index of door to actuate. Defaults to 0 (first door).
	 * @return
	 */
	@GetMapping( "/closeDoor" )
	public boolean closeDoor( @RequestParam( value="doorIndex", defaultValue="0" ) String doorIndex ) {

		return this.closeDoorUseCase.execute( new CloseDoorCommand( doorIndex ) );
	}

}

record DoorStatus( String name, Boolean isOpen ) { }

record GarageStatus( List<DoorStatus> doorStatuses ) { }