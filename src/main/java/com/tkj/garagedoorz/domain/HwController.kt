package com.tkj.garagedoorz.domain

interface HwController {

    fun pressDoorButton( doorIndex: Int )

    fun isDoorClosed( doorIndex: Int ): Boolean

    val garageDoorStatuses: List<DoorStatus>

}
