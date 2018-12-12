package com.tkj.garagedoorz

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.router

@Configuration
class Route( private val handler: Handler) {

    @Bean
    fun router() = router {
        "/doors".nest {
            GET( "/", handler::doorStatuses )
            GET( "/{door}", handler::doorStatus )
            GET( "/{door}/openDoor", handler::openDoor )
            GET( "/{door}/closeDoor", handler::closeDoor )
        }
        GET( "/", handler::helloDoorz )
    }

}