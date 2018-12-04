package com.tkj.garagedoorz

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.router

@Configuration
class Route( private val handler: Handler ) {

    @Bean
    fun router() = router {
        "/{door}".nest {
            GET( "/isDoorClosed", handler::isDoorClosed )
            GET( "/pressDoorButton", handler::pressDoorButton )
            GET( "/openDoor", handler::openDoor )
            GET( "/closeDoor", handler::closeDoor )
        }
        GET( "/", handler::helloDoorz )
    }

}