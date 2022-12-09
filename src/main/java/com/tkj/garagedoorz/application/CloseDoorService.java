package com.tkj.garagedoorz.application;

import com.tkj.garagedoorz.application.in.CloseDoorUseCase;
import com.tkj.garagedoorz.application.out.IsDoorOpenPort;
import com.tkj.garagedoorz.application.out.PressDoorButtonPort;
import org.springframework.stereotype.Component;

@Component
class CloseDoorService implements CloseDoorUseCase {

    private final PressDoorButtonPort pressDoorButtonPort;
    private final IsDoorOpenPort isDoorOpenPort;

    CloseDoorService( final PressDoorButtonPort pressDoorButtonPort, final IsDoorOpenPort isDoorOpenPort ) {

        this.pressDoorButtonPort = pressDoorButtonPort;
        this.isDoorOpenPort = isDoorOpenPort;

    }

    @Override
    public boolean execute( CloseDoorCommand command ) {

        if( this.isDoorOpenPort.isDoorOpen( command.index() ) ) {

            this.pressDoorButtonPort.pressDoorButton( command.index() );

            return true;
        }

        return false;
    }

}
