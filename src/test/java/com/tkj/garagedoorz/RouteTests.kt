package com.tkj.garagedoorz

import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.verifyNoMoreInteractions
import com.nhaarman.mockito_kotlin.whenever
import com.tkj.garagedoorz.domain.DoorStatus
import com.tkj.garagedoorz.domain.GarageDoor
import com.tkj.garagedoorz.domain.GarageDoorzRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Mono

@RunWith( SpringRunner::class )
@WebFluxTest
@Import( Route::class, Handler::class )
class RouteTests {

    @Autowired
    private lateinit var client: WebTestClient

    @MockBean
    private lateinit var repository: GarageDoorzRepository

    @MockBean
    private lateinit var mockGarageDoor: GarageDoor

    @Test
    fun testHelloDoorz() {

        client.get()
                .uri( "/" )
                .exchange()
                .expectStatus().isOk
                .expectHeader().contentType( MediaType.TEXT_PLAIN )
//                .expectBody<String>().isEqualTo( "Hello Doorz" )

    }

    @Test
    fun testDoorStatuses() {

        whenever( this.repository.garageDoorStatuses ).thenReturn( listOf( DoorStatus( "test", false ) ) )

        this.client.get()
                .uri( "/doors/" )
                .exchange()
                .expectStatus().isOk
                .expectBody()
                    .jsonPath( "$[0].doorName" ).isEqualTo( "test" )
                    .jsonPath( "$[0].status" ).isEqualTo( "open" )

        verify( this.repository ).garageDoorStatuses
        verifyNoMoreInteractions( this.repository )

    }

    @Test
    fun testDoorStatus() {

        whenever( this.mockGarageDoor.doorStatus() ).thenReturn( DoorStatus( "test", true ) )

        whenever( this.repository.lookupByDoorIndex( 1 ) ).thenReturn( Mono.just( mockGarageDoor ) )

        this.client.get()
                .uri( "/doors/{door}", 1 )
                .exchange()
                .expectStatus().isOk
                .expectBody()
                    .jsonPath( "$.doorName" ).isEqualTo( "test" )
                    .jsonPath( "$.status" ).isEqualTo( "closed" )

        verify( this.repository ).lookupByDoorIndex( 1 )
        verifyNoMoreInteractions( this.repository )

    }

    @Test
    @Throws( Exception::class )
    fun whenOpenDoor_verifyAccepted_andTrue() {

        whenever( mockGarageDoor.openDoor() ).thenReturn( DoorStatus( "test", true ) )

        whenever( this.repository.lookupByDoorIndex( 1 ) ).thenReturn( Mono.just( mockGarageDoor ) )

        val result =
                this.client.get()
                .uri("/doors/{door}/openDoor", 1)
                .exchange()
                .expectStatus().isAccepted
                .returnResult( DoorStatus::class.java )

        val expected = DoorStatus( "test", true )
        assertThat( result.responseBody.blockFirst() ).isEqualTo( expected )

        verify( this.repository ).lookupByDoorIndex( 1 )
        verifyNoMoreInteractions( this.repository )

    }

    @Test
    @Throws( Exception::class )
    fun whenOpenDoor_verifyAccepted_andFalse() {

        whenever( mockGarageDoor.openDoor() ).thenReturn( DoorStatus( "test", false ) )

        whenever( this.repository.lookupByDoorIndex( 1 ) ).thenReturn( Mono.just( mockGarageDoor ) )

        val result = this.client.get()
                .uri("/doors/{door}/openDoor", 1 )
                .exchange()
                .expectStatus().isAccepted
                .returnResult( DoorStatus::class.java )

        val expected = DoorStatus( "test", false )
        assertThat( result.responseBody.blockFirst() ).isEqualTo( expected )

        verify( this.repository ).lookupByDoorIndex( 1 )
        verifyNoMoreInteractions( this.repository )

    }

    @Test
    @Throws( Exception::class )
    fun whenCloseDoor_verifyAccepted_andTrue() {

        whenever( mockGarageDoor.closeDoor() ).thenReturn( DoorStatus( "test", true ) )

        whenever( this.repository.lookupByDoorIndex( 1 ) ).thenReturn( Mono.just( mockGarageDoor ) )

        val result = this.client.get()
                .uri("/doors/{door}/closeDoor", 1 )
                .exchange()
                .expectStatus().isAccepted
                .returnResult( DoorStatus::class.java )

        val expected = DoorStatus( "test", true )
        assertThat( result.responseBody.blockFirst() ).isEqualTo( expected )

        verify( this.repository ).lookupByDoorIndex( 1 )
        verifyNoMoreInteractions( this.repository )

    }

    @Test
    @Throws( Exception::class )
    fun whenCloseDoor_verifyAccepted_andFalse() {

        whenever( mockGarageDoor.closeDoor() ).thenReturn( DoorStatus( "test", false ) )

        whenever( this.repository.lookupByDoorIndex( 1 ) ).thenReturn( Mono.just( mockGarageDoor ) )

        val result = this.client.get()
                .uri("/doors/{door}/closeDoor", 1 )
                .exchange()
                .expectStatus().isAccepted
                .returnResult( DoorStatus::class.java )

        val expected = DoorStatus( "test", false )
        assertThat( result.responseBody.blockFirst() ).isEqualTo( expected )

        verify( this.repository ).lookupByDoorIndex( 1 )
        verifyNoMoreInteractions( this.repository )

    }

}