package org.elevatorsystem.controller;

import org.elevatorsystem.model.Elevator;
import org.elevatorsystem.model.State;

public class MaintainanceManager {
    private final Elevator elevator;

    public MaintainanceManager(Elevator elevator) {
        this.elevator = elevator;
    }

    public void enterMaintenanceMode() {
        elevator.setState(State.MAINTENANCE);
        // Add logging statement
        System.out.println("Elevator " + elevator.getId() + " entered maintenance mode.");
    }

    public void exitMaintenanceMode() {
        elevator.setState(State.IDLE);
        // Add logging statement
        System.out.println("Elevator " + elevator.getId() + " exited maintenance mode.");
    }
}