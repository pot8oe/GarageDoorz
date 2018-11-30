package com.tkj.garagedoorz.domain

/**
 * GarageDoorzHwController manages an array of garage doors which are interfaces to through
 * Raspberry Pi GPIO pins.
 * @author Thomas G. Kenny Jr.
 */
class GarageDoorzHwController( private val doors: List<GarageDoor> ) : HwController {

    /**
     * Presses the garage door button.
     * @param doorIndex
     */
    override fun pressDoorButton( doorIndex: Int ) {

        doors[ doorIndex ].pressDoorButton()

    }

    /**
     * @param doorIndex
     * @return True if door is closed.
     */
    override fun isDoorClosed( doorIndex: Int ): Boolean {

        return doors[ doorIndex ].isDoorClosed()
    }

    /**
     * @return The array of GarageDoors.
     */
    override val garageDoorStatuses: List<DoorStatus>
        get() = doors
                .map { door -> DoorStatus( door.name, door.isDoorClosed() ) }


}
