package com.tkj.garagedoorz.domain;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.test.MockGpioFactory;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class HwControllerTests {

    private HwController subject;

    private GpioController gpio = MockGpioFactory.getInstance();
    private GarageDoor mockGarageDoor;

    @Before
    public void setup() {

//        GarageDoor garageDoor = new GarageDoor( gpio,"test", 1, 0 );
        mockGarageDoor = mock( GarageDoor.class );

        subject = new GarageDoorzHwController( singletonList( mockGarageDoor ) );

    }

    @Test
    public void testPressDoorButton() {

        subject.pressDoorButton( 0 );

        verify( mockGarageDoor ).pressDoorButton();

    }

    @Test
    public void whenIsDoorClosed_verifyTrue() {

        when( mockGarageDoor.isDoorClosed() ).thenReturn( true );

        boolean state = subject.isDoorClosed( 0 );
        assertThat( state ).isTrue();

    }

    @Test
    public void whenIsDoorClosed_verifyFalse() {

        when( mockGarageDoor.isDoorClosed() ).thenReturn( false );

        boolean state = subject.isDoorClosed( 0 );
        assertThat( state ).isFalse();

    }

    @Test
    public void testGetDoorStatuses() {

        when( mockGarageDoor.isDoorClosed() ).thenReturn( true );
        when( mockGarageDoor.getName() ).thenReturn( "test" );

        DoorStatus expected = new DoorStatus( "test", true );

        List<DoorStatus> statuses = subject.getGarageDoorStatuses();
        assertThat( statuses )
                .isNotNull()
                .hasSize( 1 )
                .containsExactly( expected );

    }

}
