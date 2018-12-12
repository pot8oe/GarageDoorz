package com.tkj.garagedoorz.domain

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.verifyNoMoreInteractions
import com.nhaarman.mockito_kotlin.whenever
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test

class GarageDoorzRepositoryTests {

    private lateinit var subject: GarageDoorzRepository

    private lateinit var mockGarageDoor: GarageDoor

    @Before
    fun setup() {

        mockGarageDoor = mock()

        whenever( mockGarageDoor.doorStatus() ).thenReturn( DoorStatus( "test", true ) )
        whenever( mockGarageDoor.closeDoor() ).thenReturn( DoorStatus( "test", true ) )
        whenever( mockGarageDoor.openDoor() ).thenReturn( DoorStatus( "test", false ) )

        subject = GarageDoorzRepository( listOf( mockGarageDoor ) )

    }

    @Test
    fun testLookupByDoorIndex() {

        val garageDoor = subject.lookupByDoorIndex( 0 ).block()

        assertThat( garageDoor!!.doorStatus() ).isEqualTo( DoorStatus( "test", true ) )
        assertThat( garageDoor.closeDoor() ).isEqualTo( DoorStatus( "test", true ) )
        assertThat( garageDoor.openDoor() ).isEqualTo( DoorStatus( "test", false ) )

        verify( garageDoor ).doorStatus()
        verify( garageDoor ).closeDoor()
        verify( garageDoor ).openDoor()
        verifyNoMoreInteractions( mockGarageDoor )

    }

    @Test
    fun testDoorStatuses() {

        val garageDoors = subject.garageDoorStatuses
        assertThat( garageDoors )
                .isNotEmpty
                .hasSize( 1 )
                .contains( DoorStatus( "test", true ) )

    }

}
