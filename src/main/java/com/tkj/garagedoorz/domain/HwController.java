package com.tkj.garagedoorz.domain;

import java.util.List;

public interface HwController {

    void pressDoorButton( int doorIndex );

    boolean isDoorOpen( int doorIndex );

    List<DoorStatus> getGarageDoorStatuses();

}
