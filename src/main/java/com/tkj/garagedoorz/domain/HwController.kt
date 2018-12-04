package com.tkj.garagedoorz.domain

import kotlin.collections.List

interface HwController {

    fun pressDoorButton( doorIndex: Int )

    fun isDoorClosed( doorIndex: Int ): Boolean

    fun openDoor( doorIndex: Int ): Boolean

    fun closeDoor( doorIndex: Int ): Boolean

    val garageDoorStatuses: List<DoorStatus>

}
