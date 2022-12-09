package com.tkj.garagedoorz.application.in;

import jakarta.validation.constraints.NotEmpty;

public interface OpenDoorUseCase {

    boolean execute( OpenDoorCommand command );

    record OpenDoorCommand( @NotEmpty String index ) { }

}
