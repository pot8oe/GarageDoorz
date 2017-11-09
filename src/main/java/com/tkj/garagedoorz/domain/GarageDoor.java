package com.tkj.garagedoorz.domain;

public class GarageDoor {

    private final String name;
    private final int pinOutActuator;
    private final int pinInPosition;

    public GarageDoor( final String doorName, final int actuatorPin, final int positionPin ) {

        this.name = doorName;
        this.pinOutActuator = actuatorPin;
        this.pinInPosition = positionPin;
    }

    /**
     * Gets the garage door name
     * @return
     */
    public String getName() {

        return name;
    }

    public int getPinOutActuator() {

        return pinOutActuator;
    }

    public int getPinInPosition() {

        return pinInPosition;
    }

}
