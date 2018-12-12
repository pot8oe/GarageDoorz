package com.tkj.garagedoorz.domain

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * DoorStatus model represents the state of a garage door.
 * @author Thomas G. Kenny Jr.
 */
data class DoorStatus( @JsonProperty( "doorName" ) val doorName: String, val isDoorClosed: Boolean ) {

    private val STATUS_CLOSED = "closed"
    private val STATUS_OPEN = "open"

    @JsonProperty("status")
    fun status(): String {

        return if ( isDoorClosed ) STATUS_CLOSED else STATUS_OPEN
    }

    override fun equals( other: Any? ): Boolean {
        if( this === other ) return true
        if( other !is DoorStatus ) return false

        if( doorName != other.doorName ) return false

        return true
    }

    override fun hashCode(): Int {

        return doorName.hashCode()
    }

    override fun toString(): String {
        return "DoorStatus(doorName='$doorName', doorStatus=$isDoorClosed, status='${status()}')"
    }

}
