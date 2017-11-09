package com.tkj.garagedoorz.domain;

import java.util.List;

public interface HwController {

    void pressDoorButton( int doorIndex );

    boolean isDoorClosed( int doorIndex );

    List<DoorStatus> getGarageDoorStatuses();

}
