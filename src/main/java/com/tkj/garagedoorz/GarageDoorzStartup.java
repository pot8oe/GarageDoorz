package com.tkj.garagedoorz;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * GarageDoorzStarup performs application & hardware initialization.
 * @author Thomas G. Kenny Jr
 *
 */
@Component
public class GarageDoorzStartup 
implements ApplicationListener<ApplicationReadyEvent> {
	
	/**
	 * This event is executed as late as conceivably possible to indicate that 
	 * the application is ready to service requests.
	 */
	@Override
	public void onApplicationEvent(ApplicationReadyEvent arg0) {
		GarageDoorzHwController.initializeHardware();
	}

}
