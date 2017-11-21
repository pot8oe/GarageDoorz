package com.tkj.garagedoorz.domain;

/**
 * DoorStatus model represents the state of a garage door.
 * @author Thomas G. Kenny Jr.
 */
public class DoorStatus {

	final String STATUS_CLOSED = "closed";
	final String STATUS_CLOSING = "closing";
	final String STATUS_OPEN = "open";
	final String STATUS_OPENING = "opening";
	
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
    
    public String getStatus() {
    	if(isClosed) return STATUS_CLOSED;
    	return STATUS_OPEN;
    }

}
