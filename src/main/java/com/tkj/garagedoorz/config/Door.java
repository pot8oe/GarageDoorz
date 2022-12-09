package com.tkj.garagedoorz.config;

import com.pi4j.io.gpio.digital.DigitalState;

public record Door( String id, String name, int actuatorGpio, DigitalState initialState, DigitalState shutdownState ) {
}

