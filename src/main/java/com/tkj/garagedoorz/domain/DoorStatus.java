package com.tkj.garagedoorz.domain;

/**
 * DoorStatus model represents the state of a garage door.
 * @author Thomas G. Kenny Jr.
 */
public class DoorStatus {

    final private String name;
    final private boolean isOpen;

    public DoorStatus( final String doorName, final boolean isDoorOpen ) {

        name = doorName;
        isOpen = isDoorOpen;

    }

    public String getDoorName() {
        return name;
    }

    public boolean isDoorOpen() {
        return isOpen;
    }

}
