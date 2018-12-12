package com.tkj.garagedoorz

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import org.springframework.test.context.junit4.SpringRunner

@RunWith( SpringRunner::class )
@SpringBootTest
class GarageDoorzApplicationTests {

	@Autowired
	lateinit var context: ApplicationContext

	@Test
	fun contextLoads() {

		assertThat( context.containsBean( "mockGpioController" ) )
		assertThat( context.containsBean( "door1" ) )
		assertThat( context.containsBean( "hwController" ) )

	}

}
