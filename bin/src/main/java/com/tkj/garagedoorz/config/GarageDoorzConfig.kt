package com.tkj.garagedoorz.config

import com.pi4j.io.gpio.GpioController
import com.pi4j.io.gpio.GpioFactory
import com.tkj.garagedoorz.domain.GarageDoor
import com.tkj.garagedoorz.domain.GarageDoorzHwController
import com.tkj.garagedoorz.domain.HwController
import com.tkj.garagedoorz.health.DoorStatusHealthIndicator
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.core.annotation.Order

@Configuration
class GarageDoorzConfig {

    @Bean
    fun hwController( doors: List<GarageDoor> ): HwController {

        return GarageDoorzHwController( doors )
    }

    @Profile( "pi" )
    @Configuration
    class Pi {

        @Bean
        fun gpioController(): GpioController {

            return GpioFactory.getInstance()
        }

        @Bean( "door1" )
        @Order( 1 )
        fun door1(
                gpio: GpioController,
                @Value( "\${garagedoorz.doors.jill.label}" ) doorName: String,
                @Value( "\${garagedoorz.doors.jill.actuator}" ) actuator: Int,
                @Value( "\${garagedoorz.doors.jill.position-sensor}" ) positionSensor: Int
        ): GarageDoor {

            return GarageDoor( gpio, doorName, actuator, positionSensor )
        }

        @Bean( "door2" )
        @Order( 2 )
        fun door2(
                gpio: GpioController,
                @Value( "\${garagedoorz.doors.tom.label}" ) doorName: String,
                @Value( "\${garagedoorz.doors.tom.actuator}" ) actuator: Int,
                @Value( "\${garagedoorz.doors.tom.position-sensor}" ) positionSensor: Int
        ): GarageDoor {

            return GarageDoor( gpio, doorName, actuator, positionSensor )
        }

        @Bean
        fun doorStatusHealtIndicator( hwController: HwController ): DoorStatusHealthIndicator {

            return DoorStatusHealthIndicator( hwController )
        }

    }

}