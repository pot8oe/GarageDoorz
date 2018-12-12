package com.tkj.garagedoorz.domain

import com.pi4j.io.gpio.*
import com.pi4j.io.gpio.event.GpioPinListenerDigital

/**
 * Gets the garage door name
 * @return
 */
class RpiGarageDoor(
        gpio: GpioController,
        val name: String,
        val pinOutActuator: Int,
        val pinInPosition: Int
): GarageDoor {

    private val input: GpioPinDigitalInput = gpio.provisionDigitalInputPin( RaspiPin.getPinByAddress( pinInPosition ), name + "_position", PinPullResistance.PULL_UP )
    private val output: GpioPinDigitalOutput = gpio.provisionDigitalOutputPin( RaspiPin.getPinByAddress( pinOutActuator ), name + "_actuator", PinState.HIGH )

    val listener: GpioPinListenerDigital = GpioPinListenerDigital {

        System.out.println( "Event received on pin [${it.pin}], new state [${it.state}]")

    }

    init {

        gpio.addListener( listener, input )

    }

    override fun openDoor(): DoorStatus {

        if( isDoorClosed() ) {

            pressDoorButton()

        }

        return DoorStatus( name, isDoorClosed() )
    }

    override fun closeDoor(): DoorStatus {

        if( !isDoorClosed() ) {

            pressDoorButton()

        }

        return DoorStatus( name, isDoorClosed() )
    }

    override fun doorStatus(): DoorStatus {

        return DoorStatus( name, isDoorClosed() )
    }

    /**
     * Presses the garage door button
     */
    private fun pressDoorButton() {

//        val output = gpio.provisionDigitalOutputPin( RaspiPin.getPinByAddress( pinOutActuator ), name + "_actuator", PinState.HIGH )

        output.setShutdownOptions( true, PinState.HIGH )

        output.pulse( 500, PinState.LOW )

//        gpio.unprovisionPin( output )

    }

    /**
     * Returns if the garage door is closed
     * @return
     */
    private fun isDoorClosed(): Boolean {

//        val input = gpio.provisionDigitalInputPin( RaspiPin.getPinByAddress( pinInPosition ), name + "_position", PinPullResistance.PULL_UP )

        val isLow = input.isLow

//        gpio.unprovisionPin( input )

        return isLow
    }

    override fun toString(): String {

        return "RpiGarageDoor(name='$name', pinOutActuator=$pinOutActuator, pinInPosition=$pinInPosition)"
    }

}
