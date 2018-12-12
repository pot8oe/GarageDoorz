package com.tkj.garagedoorz;

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class GarageDoorzApplication {

	fun main( args: Array<String> ) {
		runApplication<GarageDoorzApplication>( *args )
	}

}