package com.tkj.garagedoorz.domain;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;

import java.util.Arrays;
import java.util.List;
import static java.util.stream.Collectors.toList;

/**
 * GarageDoorzHwController manages an array of garage doors which are interfaces to through 
 * Raspberry Pi GPIO pins.
 * @author Thomas G. Kenny Jr.
 */
public class GarageDoorzHwController implements HwController {

    final static GpioController gpio = GpioFactory.getInstance();

	/**
	 * Attention! The GPIO pin numbering used in these constants is intended for use with
	 * WiringPi or Pi4J. This pin numbering is not the raw Broadcom GPIO pin numbers.
	 * http://pi4j.com/pins/model-zero-rev1.html
	 */

	private List<GpioGarageDoor> doors;

	public GarageDoorzHwController( GarageDoor... doors ) {

        this.doors =
                Arrays.stream( doors )
                        .map( GpioGarageDoor::new )
                        .collect( toList() );

	}

	/**
	 * Presses the garage door button.
	 * @param doorIndex
	 */
	@Override
	public void pressDoorButton( int doorIndex ) {

		doors.get( doorIndex ).pressDoorButton();

	}
	
	/**
	 * @param doorIndex
	 * @return True if door is closed.
	 */
	public boolean isDoorClosed( int doorIndex ) {

        return doors.get( doorIndex ).isDoorClosed();
	}
	
	/**
	 * @return The array of GarageDoors.
	 */
	@Override
	public List<DoorStatus> getGarageDoorStatuses() {

	    return doors.stream()
                .map( door -> new DoorStatus( door.getName(), door.isDoorClosed() ) )
                .collect( toList() );
	}

    /**
     * Represents a single garage door with a button and closed sensor.
     * Hardware interface is Pi4J to access Raspberry Pi GPIO pins.
     * @author Thomas G. Kenny Jr.
     */
    public class GpioGarageDoor {

        String name;
        GpioPinDigitalOutput pinOutActuator;
        GpioPinDigitalInput pinInIsClosed;

        public GpioGarageDoor( GarageDoor garageDoor ) {

            name = garageDoor.getName();
            pinOutActuator = gpio.provisionDigitalOutputPin( RaspiPin.getPinByAddress( garageDoor.getPinOutActuator() ), name + "_actuator", PinState.HIGH );
            pinOutActuator.setShutdownOptions(true, PinState.HIGH );
            pinInIsClosed = gpio.provisionDigitalInputPin( RaspiPin.getPinByAddress( garageDoor.getPinInPosition() ), name + "_position", PinPullResistance.PULL_UP );

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
        	pinOutActuator.pulse(500, PinState.LOW);
        }

        /**
         * Returns if the garage door is closed
         * @return
         */
        public boolean isDoorClosed() {
            return pinInIsClosed.isLow();
        }

    }

}
