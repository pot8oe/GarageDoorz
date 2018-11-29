package com.tkj.garagedoorz.endpoint

import com.tkj.garagedoorz.domain.HwController
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@RunWith( SpringRunner::class )
@WebMvcTest( GarageDoorzController::class )
class GarageDoorzControllerTests {

    @Autowired
    private val mockMvc: MockMvc? = null

    @MockBean
    private val hwController: HwController? = null

    @Test
    @Throws( Exception::class )
    fun testHello() {

        this.mockMvc!!.perform(get( "/" ) )
                .andDo( print() )
                .andExpect( status().isOk )
                .andExpect( content().string( "Hello Doorz" ) )

    }

    @Test
    @Throws( Exception::class )
    fun whenIsDoorClosed_verifyFalse() {

        this.mockMvc!!.perform( get( "/{door}/isDoorClosed", 1 ) )
                .andDo( print() )
                .andExpect( status().isOk )
                .andExpect( content().string( "false" ) )

        verify<HwController>( this.hwController ).isDoorClosed( 1 )

    }

    @Test
    @Throws( Exception::class )
    fun whenIsDoorClosed_verifyTrue() {

        `when`( this.hwController!!.isDoorClosed( 1 ) ).thenReturn( true )

        this.mockMvc!!.perform(get( "/{door}/isDoorClosed", 1 ) )
                .andDo( print() )
                .andExpect( status().isOk )
                .andExpect( content().string( "true" ) )

        verify( this.hwController ).isDoorClosed( 1 )

    }

    @Test
    @Throws( Exception::class )
    fun testPressDoorButton() {

        this.mockMvc!!.perform( get( "/{door}/pressDoorButton",  1 ) )
                .andDo( print() )
                .andExpect( status().isAccepted )

        verify<HwController>( this.hwController ).pressDoorButton( 1 )

    }

    @Test
    @Throws( Exception::class )
    fun whenOpenDoor_verifyAccepted_andTrue() {

        `when`( this.hwController!!.isDoorClosed( 1 ) ).thenReturn( true )

        this.mockMvc!!.perform(get( "/{door}/openDoor", 1 ) )
                .andDo( print() )
                .andExpect( status().isAccepted )
                .andExpect( content().string( "true" ) )

        verify( this.hwController ).isDoorClosed( 1 )
        verify( this.hwController ).pressDoorButton( 1 )
        verifyNoMoreInteractions( this.hwController )

    }

    @Test
    @Throws( Exception::class )
    fun whenOpenDoor_verifyAccepted_andFalse() {

        `when`( this.hwController!!.isDoorClosed( 1 ) ).thenReturn( false )

        this.mockMvc!!.perform( get( "/{door}/openDoor", 1 ) )
                .andDo( print() )
                .andExpect( status().isAccepted )
                .andExpect( content().string( "false" ) )

        verify( this.hwController ).isDoorClosed( 1 )
        verifyNoMoreInteractions( this.hwController )

    }

    @Test
    @Throws( Exception::class )
    fun whenCloseDoor_verifyAccepted_andTrue() {

        `when`( this.hwController!!.isDoorClosed( 1 ) ).thenReturn( false )

        this.mockMvc!!.perform( get( "/{door}/closeDoor", 1 ) )
                .andDo( print() )
                .andExpect( status().isAccepted )
                .andExpect( content().string( "true" ) )

        verify( this.hwController ).isDoorClosed( 1 )
        verify( this.hwController ).pressDoorButton( 1 )
        verifyNoMoreInteractions( this.hwController )

    }

    @Test
    @Throws( Exception::class )
    fun whenCloseDoor_verifyAccepted_andFalse() {

        `when`( this.hwController!!.isDoorClosed( 1 ) ).thenReturn( true )

        this.mockMvc!!.perform( get( "/{door}/closeDoor", 1 ) )
                .andDo( print() )
                .andExpect( status().isAccepted )
                .andExpect( content().string( "false" ) )

        verify( this.hwController ).isDoorClosed( 1 )
        verifyNoMoreInteractions( this.hwController )

    }

}
