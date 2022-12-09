package com.tkj.garagedoorz.application;

import com.tkj.garagedoorz.application.in.OpenDoorUseCase;
import com.tkj.garagedoorz.application.out.IsDoorOpenPort;
import com.tkj.garagedoorz.application.out.PressDoorButtonPort;
import org.springframework.stereotype.Component;

@Component
class OpenDoorService implements OpenDoorUseCase {

    private final PressDoorButtonPort pressDoorButtonPort;
    private final IsDoorOpenPort isDoorOpenPort;

    OpenDoorService( final PressDoorButtonPort pressDoorButtonPort, final IsDoorOpenPort isDoorOpenPort ) {

        this.pressDoorButtonPort = pressDoorButtonPort;
        this.isDoorOpenPort = isDoorOpenPort;
    }

    @Override
    public boolean execute( OpenDoorCommand command ) {

        if( !this.isDoorOpenPort.isDoorOpen( command.index() ) ) {

            this.pressDoorButtonPort.pressDoorButton( command.index() );

            return true;
        }

        return false;
    }

}
