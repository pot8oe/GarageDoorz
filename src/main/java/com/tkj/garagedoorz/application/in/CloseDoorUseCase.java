package com.tkj.garagedoorz.application.in;


import jakarta.validation.constraints.NotEmpty;

public interface CloseDoorUseCase {

    boolean execute( CloseDoorCommand command );

    record CloseDoorCommand( @NotEmpty String index ) { }

}
