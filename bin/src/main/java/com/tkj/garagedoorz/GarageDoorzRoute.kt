package com.tkj.garagedoorz

import com.tkj.garagedoorz.domain.HwController
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.BodyInserters.fromObject
import org.springframework.web.reactive.function.server.router

@Configuration
class GarageDoorzRoute(  private val hwController: HwController ) {

    @Bean
    fun route() = router {
        GET( "/" ) {
            req -> ok()
                .body( fromObject( "Hello Doorz" ) )
        }
        GET( "/{door}/doorStatus" ) {
            req -> ok()
                .body( fromObject( hwController.isDoorClosed( req.pathVariable( "door" ).toInt() ) ) )
        }
        GET( "/{door}/pressDoorButton" ) {
            req -> accepted()
                .body( fromObject( hwController.pressDoorButton( req.pathVariable( "door" ).toInt() ) ) )
        }
        GET( "/{door}/openDoor" ) {
            req -> accepted()
                .body {
                    if( hwController.isDoorClosed( req.pathVariable( "door" ).toInt() ) ) {

                        hwController.pressDoorButton( req.pathVariable( "door" ).toInt() )

                        true
                    }

                    false
                }
        }
    }

}