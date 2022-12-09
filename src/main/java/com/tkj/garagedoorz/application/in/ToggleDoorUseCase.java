package com.tkj.garagedoorz.application.in;

import jakarta.validation.constraints.NotEmpty;

public interface ToggleDoorUseCase {

    void execute( ToggleDoorCommand command );

    record ToggleDoorCommand( @NotEmpty String index ) { }

}
