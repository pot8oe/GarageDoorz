package com.tkj.garagedoorz.adapter.out;

import com.pi4j.io.gpio.digital.Digital;
import com.pi4j.io.gpio.digital.DigitalInput;
import com.pi4j.io.gpio.digital.DigitalOutput;
import com.pi4j.io.gpio.digital.DigitalState;
import com.tkj.garagedoorz.application.out.IsDoorOpenPort;
import com.tkj.garagedoorz.application.out.PressDoorButtonPort;
import com.tkj.garagedoorz.application.out.QueryDoorsPort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static java.util.stream.Collectors.toMap;

@Component
public class RpiAdapter implements PressDoorButtonPort, IsDoorOpenPort, QueryDoorsPort {

    private List<DigitalInput> positionSensors;
    private List<DigitalOutput> actuators;

    RpiAdapter( List<DigitalInput> positionSensors, List<DigitalOutput> actuators ) {

        this.positionSensors = positionSensors;
        this.actuators = actuators;

    }

    @Override
    public boolean isDoorOpen( String door ) {

        var isOpen = positionSensors.stream()
                .filter( positionSensor -> positionSensor.id().equals( door ) )
                .map( Digital::isLow )
                .findFirst();

        return isOpen.orElseGet( isOpen::get );

    }

    @Override
    public void pressDoorButton( String door ) {

        var found = actuators.stream()
                .filter( actuator -> actuator.id().equals( door ) )
                .findFirst();

        found.ifPresent( digitalOutput -> digitalOutput.pulse( 250, TimeUnit.MILLISECONDS, DigitalState.HIGH ) );

    }

    @Override
    public Map<String, Boolean> status() {

        return positionSensors.stream()
                .collect( toMap( Digital::name, Digital::isLow ) ) ;
    }

}
