package com.tkj.garagedoorz

import com.pi4j.io.gpio.GpioController
import com.pi4j.io.gpio.test.MockGpioFactory
import com.tkj.garagedoorz.domain.GarageDoor
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Primary
import org.springframework.test.context.junit4.SpringRunner

@RunWith( SpringRunner::class )
@SpringBootTest
class GarageDoorzApplicationTests() {

	@Autowired
	lateinit var context: ApplicationContext

	@Test
	fun contextLoads() {

		assertThat( context.containsBean( "mockGpioController" ) )
		assertThat( context.containsBean( "door1" ) )
		assertThat( context.containsBean( "hwController" ) )

	}

	@TestConfiguration
	class GarageDoorzTestConfiguration {

		@Bean
		@Primary
		fun mockGpioController(): GpioController {

			return MockGpioFactory.getInstance()
		}

		@Bean( "door1" )
		fun door1(
				gpio: GpioController,
				@Value( "\${garagedoorz.doors.test.label}" ) doorName: String,
				@Value( "\${garagedoorz.doors.test.actuator}" ) actuator: Int,
				@Value( "\${garagedoorz.doors.test.position-sensor}" ) positionSensor: Int
		): GarageDoor {

			return GarageDoor( gpio, doorName, actuator, positionSensor )
		}

	}

}
