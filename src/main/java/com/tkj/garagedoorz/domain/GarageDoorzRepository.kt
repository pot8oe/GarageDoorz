package com.tkj.garagedoorz.domain

import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

/**
 * GarageDoorzRepository manages an array of garage doors which are interfaces to through
 * Raspberry Pi GPIO pins.
 * @author Thomas G. Kenny Jr.
 */
@Component
class GarageDoorzRepository( private val doors: List<GarageDoor> ) {

    fun lookupByDoorIndex( doorIndex: Int ): Mono<GarageDoor> {

        return Mono.just( doors[ doorIndex ] )
    }

    /**
     * @return The array of GarageDoors.
     */
    val garageDoorStatuses: List<DoorStatus>
        get() = doors
                .map { door -> door.doorStatus() }

}
