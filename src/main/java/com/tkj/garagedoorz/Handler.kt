package com.tkj.garagedoorz

import com.tkj.garagedoorz.domain.GarageDoorzRepository
import org.slf4j.LoggerFactory
import org.springframework.http.MediaType.TEXT_PLAIN
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyInserters.fromObject
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.*
import reactor.core.publisher.Mono

@Component
class Handler( private val repository: GarageDoorzRepository ) {

    companion object {

        private val log = LoggerFactory.getLogger( Handler::class.java )
    }

    fun helloDoorz( req: ServerRequest): Mono<ServerResponse> {
        log.debug( "helloDoorz : enter" )

        return ok()
                .contentType( TEXT_PLAIN )
                .body( fromObject( "Hello Doorz" ) )
    }

    fun doorStatuses( req: ServerRequest ): Mono<ServerResponse> {
        log.debug( "doorStatuses : enter" )

        return ok()
                .body( fromObject( repository.garageDoorStatuses ) )
                .switchIfEmpty( notFound().build() )
    }

    fun doorStatus( req: ServerRequest ): Mono<ServerResponse> {
        log.debug( "doorStatus : enter" )

        return repository.lookupByDoorIndex( req.pathVariable("door" ).toInt() )
                .map { it.doorStatus() }
                .flatMap { status ->
                    ok()
                            .body( fromObject( status ) )
                            .switchIfEmpty( notFound().build() )
                }
    }

    fun openDoor( req: ServerRequest ): Mono<ServerResponse> {
        log.debug( "openDoor : enter" )

        return repository.lookupByDoorIndex( req.pathVariable( "door" ).toInt() )
                .map { it.openDoor() }
                .flatMap { status ->
                    accepted()
                            .body( fromObject( status ) )
                            .switchIfEmpty( notFound().build() )
                }
    }

    fun closeDoor( req: ServerRequest ): Mono<ServerResponse> {
        log.debug( "closeDoor : enter" )

        return repository.lookupByDoorIndex( req.pathVariable( "door" ).toInt() )
                .map { it.closeDoor() }
                .flatMap { status ->
                    accepted()
                            .body( fromObject( status ) )
                            .switchIfEmpty( notFound().build() )
                }
    }

}
