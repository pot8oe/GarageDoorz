package com.tkj.garagedoorz.domain

import com.pi4j.io.gpio.GpioController
import com.pi4j.io.gpio.PinPullResistance
import com.pi4j.io.gpio.PinState
import com.pi4j.io.gpio.test.MockPin

/**
 * Gets the garage door name
 * @return
 */
class MockGarageDoor(
        private val gpio: GpioController
): GarageDoor {

    val name: String = "test"

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

        val output = gpio.provisionDigitalOutputPin( MockPin.DIGITAL_OUTPUT_PIN, name + "_actuator", PinState.HIGH )

        output.setShutdownOptions( true, PinState.HIGH )

        output.pulse( 500, PinState.LOW )

        gpio.unprovisionPin( output )

    }

    /**
     * Returns if the garage door is closed
     * @return
     */
    private fun isDoorClosed(): Boolean {

        val input = gpio.provisionDigitalInputPin( MockPin.DIGITAL_INPUT_PIN, name + "_position", PinPullResistance.PULL_UP )

        val isLow = input.isLow

        gpio.unprovisionPin( input )

        return isLow
    }

    override fun toString(): String {

        return "RpiGarageDoor(name='$name'"
    }

}
