package com.tkj.garagedoorz.config;

import com.pi4j.io.gpio.digital.PullResistance;

public record Position( String id, String name, int positionGpio, PullResistance pullResistance, long debounce ) {
}

