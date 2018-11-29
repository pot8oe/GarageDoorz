package com.tkj.garagedoorz.domain

/**
 * DoorStatus model represents the state of a garage door.
 * @author Thomas G. Kenny Jr.
 */
class DoorStatus( val doorName: String, val isDoorClosed: Boolean ) {

    internal val STATUS_CLOSED = "closed"
    internal val STATUS_OPEN = "open"

    val status: String
        get() = if ( isDoorClosed ) STATUS_CLOSED else STATUS_OPEN

}
