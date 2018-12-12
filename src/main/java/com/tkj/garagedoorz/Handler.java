package com.tkj.garagedoorz;

import com.tkj.garagedoorz.domain.GarageDoor;
import com.tkj.garagedoorz.domain.GarageDoorzRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.TEXT_PLAIN;
import static org.springframework.web.reactive.function.BodyInserters.fromObject;
import static org.springframework.web.reactive.function.server.ServerResponse.*;

@Component
public class Handler {

    private static final Logger log = LoggerFactory.getLogger( Handler.class );

    private final GarageDoorzRepository repository;

    public Handler( final GarageDoorzRepository repository ) {

        this.repository = repository;

    }

    public Mono<ServerResponse> helloDoorz( ServerRequest req ) {
        log.debug( "hello : enter" );

        return ok()
                .contentType( TEXT_PLAIN )
                .body( fromObject( "Hello Doorz" ) );
    }

    public Mono<ServerResponse> doorStatuses( ServerRequest req ) {
        log.debug( "doorStatuses : enter" );

        return ok()
                .body( fromObject( repository.getGarageDoorStatuses() ) )
                .switchIfEmpty( notFound().build() );
    }

    public Mono<ServerResponse> doorStatus(ServerRequest req ) {
        log.debug( "doorStatus : enter" );

        return repository.lookupByDoorIndex( Integer.valueOf( req.pathVariable( "door" ) ) )
                .map( GarageDoor::doorStatus )
                .flatMap(
                        status -> ok()
                                .body( fromObject( status ) )
                                .switchIfEmpty( notFound().build() )
                );
    }

    public Mono<ServerResponse> openDoor( ServerRequest req ) {
        log.debug( "openDoor : enter" );

        return repository.lookupByDoorIndex( Integer.valueOf( req.pathVariable( "door" ) ) )
                .map( GarageDoor::openDoor )
                .flatMap(
                        status -> accepted()
                                .body( fromObject( status ) )
                                .switchIfEmpty( notFound().build() )
                );
    }

    public Mono<ServerResponse> closeDoor( ServerRequest req ) {
        log.debug( "closeDoor : enter" );

        return repository.lookupByDoorIndex( Integer.valueOf( req.pathVariable( "door" ) ) )
                .map( GarageDoor::closeDoor )
                .flatMap(
                        status -> accepted()
                                .body( fromObject( status ) )
                                .switchIfEmpty( notFound().build() )
                );
    }

}
