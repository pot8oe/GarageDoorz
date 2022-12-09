package com.tkj.garagedoorz.application;

import com.tkj.garagedoorz.application.in.IsDoorOpenUseCase;
import com.tkj.garagedoorz.application.out.IsDoorOpenPort;
import org.springframework.stereotype.Component;

@Component
class IsDoorOpenService implements IsDoorOpenUseCase {

    private final IsDoorOpenPort isDoorOpenPort;

    IsDoorOpenService( final IsDoorOpenPort isDoorOpenPort ) {

        this.isDoorOpenPort = isDoorOpenPort;
    }

    @Override
    public boolean execute( IsDoorOpenCommand command ) {

        return this.isDoorOpenPort.isDoorOpen( command.index() );
    }

}
