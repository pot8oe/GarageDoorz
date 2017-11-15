package com.tkj.garagedoorz.health;

import java.util.List;

import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health.Builder;

import com.tkj.garagedoorz.domain.DoorStatus;
import com.tkj.garagedoorz.domain.HwController;

/**
 * HealthIndicator that reports garage door statuses.
 * @author Thomas G. Kenny Jr
 *
 */
public class DoorStatusHealthIndicator extends AbstractHealthIndicator {

	private final HwController hwController;
	
	public DoorStatusHealthIndicator(final HwController hwController) {
		this.hwController = hwController;
	}
	
	@Override
	protected void doHealthCheck(Builder builder) throws Exception {
		List<DoorStatus> doorStatuses = hwController.getGarageDoorStatuses();
		builder.up();
		builder.withDetail("doors", doorStatuses);
	}

}
