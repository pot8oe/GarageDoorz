package com.tkj.garagedoorz

import com.tkj.garagedoorz.domain.HwController
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.Import
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.reactive.server.WebTestClient

@RunWith( SpringRunner::class )
@WebFluxTest
@Import( GarageDoorzRoute::class )
class GarageDoorzRouteTests {

    @Autowired
    private lateinit var client: WebTestClient

    @MockBean
    private lateinit var hwController: HwController

    @Test
    fun testHello() {

        client.get()
                .uri( "/" )
                .exchange()
                .expectStatus().isOk
//                .expectBody( String::class.java ).value( "Hello Doorz" )
    }

    @Test
    fun whenIsDoorClosed_verifyFalse() {

        val result = this.client.get()
                .uri( "/{door}/doorStatus", 1 )
                .exchange()
                .expectStatus().isOk
                .returnResult( Boolean::class.java )

        assertThat( result.responseBody.blockFirst() ).isEqualTo( false )

        verify<HwController>( this.hwController ).isDoorClosed( 1 )

    }

    @Test
    fun whenIsDoorClosed_verifyTrue() {

        `when`( this.hwController!!.isDoorClosed( 1 ) ).thenReturn( true )

        val result = this.client.get()
                .uri( "/{door}/doorStatus", 1 )
                .exchange()
                .expectStatus().isOk
                .returnResult( Boolean::class.java )

        assertThat( result.responseBody.blockFirst() ).isEqualTo( true )

        verify<HwController>( this.hwController ).isDoorClosed( 1 )

    }

    @Test
    fun testPressDoorButton() {

        this.client.get()
                .uri( "/{door}/pressDoorButton", 1 )
                .exchange()
                .expectStatus().isAccepted

        verify<HwController>( this.hwController ).pressDoorButton( 1 )

    }

}