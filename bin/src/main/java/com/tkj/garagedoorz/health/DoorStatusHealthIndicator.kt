package com.tkj.garagedoorz.health

import com.tkj.garagedoorz.domain.HwController
import org.springframework.boot.actuate.health.AbstractHealthIndicator
import org.springframework.boot.actuate.health.Health.Builder

/**
 * HealthIndicator that reports garage door statuses.
 * @author Thomas G. Kenny Jr
 */
class DoorStatusHealthIndicator( private val hwController: HwController ) : AbstractHealthIndicator() {

    @Throws( Exception::class )
    override fun doHealthCheck( builder: Builder ) {

        val doorStatuses = hwController.garageDoorStatuses
        builder
                .up()
                .withDetail( "doors", doorStatuses )

    }

}
