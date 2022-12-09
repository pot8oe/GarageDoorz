package com.tkj.garagedoorz.application.in;

import java.util.Map;

public interface GetDoorStatusesUseCase {

    Map<String, Boolean> execute( GetDoorStatusesCommand command );

    record GetDoorStatusesCommand() { }

}
