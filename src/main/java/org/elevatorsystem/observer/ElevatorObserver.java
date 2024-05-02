package org.elevatorsystem.observer;

import org.elevatorsystem.model.Elevator;

public interface ElevatorObserver {
    void onElevatorStateChanged(Elevator elevator);
}
