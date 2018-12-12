package com.tkj.garagedoorz.domain

interface GarageDoor {

    fun openDoor(): DoorStatus
    fun closeDoor(): DoorStatus
    fun doorStatus(): DoorStatus

}