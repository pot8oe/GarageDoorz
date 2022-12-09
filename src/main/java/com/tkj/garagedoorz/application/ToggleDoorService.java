package com.tkj.garagedoorz.application;

import com.tkj.garagedoorz.application.in.ToggleDoorUseCase;
import com.tkj.garagedoorz.application.out.PressDoorButtonPort;
import org.springframework.stereotype.Component;

@Component
class ToggleDoorService implements ToggleDoorUseCase {

    private final PressDoorButtonPort pressDoorButtonPort;

    ToggleDoorService( final PressDoorButtonPort pressDoorButtonPort ) {

        this.pressDoorButtonPort = pressDoorButtonPort;

    }

    @Override
    public void execute( ToggleDoorCommand command ) {

        this.pressDoorButtonPort.pressDoorButton( command.index() );

    }

}
