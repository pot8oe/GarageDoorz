package com.tkj.garagedoorz.config

import com.tkj.garagedoorz.domain.DoorStatus
import com.tkj.garagedoorz.domain.GarageDoor
import com.tkj.garagedoorz.domain.GarageDoorzHwController
import com.tkj.garagedoorz.domain.HwController
import com.tkj.garagedoorz.health.DoorStatusHealthIndicator
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile

@Configuration
class GarageDoorzConfig {

    @Profile( "default" )
    @Configuration
    class Default {

        @Bean
        fun hwController(): HwController {

            return object : HwController {

                override fun pressDoorButton( doorIndex: Int ) {

                }

                override fun isDoorClosed( doorIndex: Int ): Boolean {

                    return false
                }

                override fun getGarageDoorStatuses(): List<DoorStatus> {

                    return listOf( DoorStatus( "Test Door", false ) )
                }

            }
        }

    }

    @Profile( "pi" )
    @Configuration
    class Pi {

        @Bean( "door1" )
        fun door1(
                @Value( "\${garagedoorz.doors.jill.label}" ) doorName: String,
                @Value( "\${garagedoorz.doors.jill.actuator}" ) actuator: Int,
                @Value( "\${garagedoorz.doors.jill.position-sensor}" ) positionSensor: Int
        ): GarageDoor {

            return GarageDoor(doorName, actuator, positionSensor)
        }

        @Bean( "door2" )
        fun door2(
                @Value( "\${garagedoorz.doors.tom.label}" ) doorName: String,
                @Value( "\${garagedoorz.doors.tom.actuator}" ) actuator: Int,
                @Value( "\${garagedoorz.doors.tom.position-sensor}" ) positionSensor: Int
        ): GarageDoor {

            return GarageDoor( doorName, actuator, positionSensor )
        }

        @Bean
        fun hwController( door1: GarageDoor, door2: GarageDoor ): HwController {

            return GarageDoorzHwController( door1, door2 )
        }

        @Bean
        fun doorStatusHealtIndicator( hwController: HwController ): DoorStatusHealthIndicator {

            return DoorStatusHealthIndicator( hwController )
        }

    }

}
