package com.tkj.garagedoorz.application;

import com.tkj.garagedoorz.application.in.GetDoorStatusesUseCase;
import com.tkj.garagedoorz.application.out.QueryDoorsPort;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
class GetDoorStatusesService implements GetDoorStatusesUseCase {

    private final QueryDoorsPort queryDoorsPort;

    GetDoorStatusesService( final QueryDoorsPort queryDoorsPort ) {

        this.queryDoorsPort = queryDoorsPort;

    }

    @Override
    public Map<String, Boolean> execute( GetDoorStatusesCommand command ) {

        return this.queryDoorsPort.status();
    }

}
