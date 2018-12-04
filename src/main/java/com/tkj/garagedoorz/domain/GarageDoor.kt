package com.tkj.garagedoorz.domain

import com.pi4j.io.gpio.GpioController
import com.pi4j.io.gpio.PinPullResistance
import com.pi4j.io.gpio.PinState
import com.pi4j.io.gpio.RaspiPin

/**
 * Gets the garage door name
 * @return
 */
class GarageDoor(
        private val gpio: GpioController,
        val name: String,
        val pinOutActuator: Int,
        val pinInPosition: Int
) {

    /**
     * Returns if the garage door is closed
     * @return
     */
    fun isDoorClosed(): Boolean {

        val input = gpio.provisionDigitalInputPin( RaspiPin.getPinByAddress( pinInPosition ), name + "_position", PinPullResistance.PULL_UP )

        val isLow = input.isLow

        gpio.unprovisionPin( input )

        return isLow
    }

    /**
     * Presses the garage door button
     */
    fun pressDoorButton() {

        val output = gpio.provisionDigitalOutputPin( RaspiPin.getPinByAddress( pinOutActuator ), name + "_actuator", PinState.HIGH )

        output.setShutdownOptions( true, PinState.HIGH )

        output.pulse( 500, PinState.LOW )

        gpio.unprovisionPin( output )

    }

}
