package com.tkj.garagedoorz.application.in;

import jakarta.validation.constraints.NotEmpty;

public interface IsDoorOpenUseCase {

    boolean execute( IsDoorOpenCommand command );

    record IsDoorOpenCommand( @NotEmpty String index ) { }

}
