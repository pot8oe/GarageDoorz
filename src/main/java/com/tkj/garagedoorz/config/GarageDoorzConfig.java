package com.tkj.garagedoorz.config;

import com.tkj.garagedoorz.domain.DoorStatus;
import com.tkj.garagedoorz.domain.GarageDoor;
import com.tkj.garagedoorz.domain.GarageDoorzHwController;
import com.tkj.garagedoorz.domain.HwController;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.List;

import static java.util.Collections.singletonList;

@Configuration
public class GarageDoorzConfig {

    @Bean
    public HwController hwController() {

        return new HwController() {

            @Override
            public void pressDoorButton( int doorIndex ) {

            }

            @Override
            public boolean isDoorOpen( int doorIndex ) {

                return false;
            }

            @Override
            public List<DoorStatus> getGarageDoorStatuses() {

                return singletonList( new DoorStatus( "Test Door", false ) );
            }

        };
    }

    @Profile( "pi" )
    @Configuration
    public static class Pi {

        @Bean( "door1" )
        public GarageDoor door1(
                @Value( "${garagedoorz.doors.jill.label}" ) String doorName,
                @Value( "${garagedoorz.doors.jill.actuator}" ) int actuator,
                @Value( "${garagedoorz.doors.jill.position-sensor}" ) int positionSensor
        ) {

            return new GarageDoor( doorName, actuator, positionSensor );
        }

        @Bean( "door2" )
        public GarageDoor door2(
                @Value( "${garagedoorz.doors.tom.label}" ) String doorName,
                @Value( "${garagedoorz.doors.tom.actuator}" ) int actuator,
                @Value( "${garagedoorz.doors.tom.position-sensor}" ) int positionSensor
        ) {

            return new GarageDoor( doorName, actuator, positionSensor );
        }

        @Bean
        public HwController hwController( final GarageDoor door1, final GarageDoor door2 ) {

            return new GarageDoorzHwController( new GarageDoor[] { door1, door2 } );
        }

    }

}

