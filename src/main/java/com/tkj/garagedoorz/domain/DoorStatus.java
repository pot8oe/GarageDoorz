package com.tkj.garagedoorz.domain;

/**
 * DoorStatus model represents the state of a garage door.
 * @author Thomas G. Kenny Jr.
 */
public class DoorStatus {

    final private String name;
    final private boolean isClosed;

    public DoorStatus( final String doorName, final boolean isDoorClosed ) {

        name = doorName;
        isClosed = isDoorClosed;

    }

    public String getDoorName() {
        return name;
    }

    public boolean isDoorClosed() {
        return isClosed;
    }

}
