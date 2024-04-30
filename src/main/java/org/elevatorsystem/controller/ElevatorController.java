package org.elevatorsystem.controller;

import org.elevatorsystem.exceptions.ElevatorCapacityExceededException;
import org.elevatorsystem.exceptions.ElevatorMaintainanceModeException;
import org.elevatorsystem.exceptions.InvalidFloorException;
import org.elevatorsystem.model.Elevator;
import org.elevatorsystem.model.request.Request;

import java.util.List;

public class ElevatorController {
    private List<Elevator> elevators;

    public ElevatorController(List<Elevator> elevators) {
        this.elevators = elevators;
    }

    public void processRequest(Request request) throws InvalidFloorException, ElevatorMaintainanceModeException, ElevatorCapacityExceededException {
        Elevator bestElevator = findBestElevator(request);
        bestElevator.addRequest(request);
    }

    private Elevator findBestElevator(Request request) {
        return elevators.get(0); // Simplified example
    }
}
