package com.example.garagedoorz;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;

@RestController
public class GarageDoorzController {
	
	@RequestMapping("/")
	public String HelloFucknutz() {
		return "Hello Doorz";
	}
	
	@RequestMapping("/getGarageStatus")
	public GarageStatus getGarageStatus() {
		
		// create gpio controller
        final GpioController gpio = GpioFactory.getInstance();

        // provision gpio pin #01 as an output pin and turn on
        final GpioPinDigitalOutput pin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_01, "MyLED", PinState.HIGH);

        // set shutdown state for this pin
        pin.setShutdownOptions(true, PinState.LOW);


        // turn off gpio pin #01
        pin.low();
        System.out.println("--> GPIO state should be: OFF");
		
		return new GarageStatus(0,0);
	}
	
	@RequestMapping("/isDoorOpen")
	public int isDoorOpen(@RequestParam(value="doorIndex", defaultValue="0") int doorIndex) {
		
		return 0;
	}
	
	@RequestMapping("/toggleDoor")
	public boolean toggleDoor(@RequestParam(value="doorIndex", defaultValue="0") int doorIndex) {
		return false;
	}
	
	@RequestMapping("/openDoor")
	public boolean openDoor(@RequestParam(value="doorIndex", defaultValue="0") int doorIndex) {
		return false;
	}

	@RequestMapping("/closeDoor")
	public boolean closeDoor(@RequestParam(value="doorIndex", defaultValue="0") int doorIndex) {
		return false;
	}

}
