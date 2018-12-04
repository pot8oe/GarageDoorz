package com.tkj.garagedoorz

import com.tkj.garagedoorz.domain.HwController
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyInserters.fromObject
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse.accepted
import org.springframework.web.reactive.function.server.ServerResponse.ok

@Component
class Handler( private val hwController: HwController ) {

    fun helloDoorz( req: ServerRequest ) = ok()
            .body( fromObject( "Hello Doorz" ) )

    fun isDoorClosed( req: ServerRequest ) = ok()
            .body( fromObject( hwController.isDoorClosed( req.pathVariable( "door" ).toInt() ) ) )

    fun pressDoorButton( req: ServerRequest ) = accepted()
            .body( fromObject( hwController.pressDoorButton( req.pathVariable( "door" ).toInt() ) ) )

    fun openDoor( req: ServerRequest ) = accepted()
            .body( fromObject( hwController.openDoor( req.pathVariable( "door" ).toInt() ) ) )

    fun closeDoor( req: ServerRequest ) = accepted()
            .body( fromObject( hwController.closeDoor( req.pathVariable( "door" ).toInt() ) ) )

}