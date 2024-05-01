package org.elevatorsystem.controller;

import org.elevatorsystem.algorithms.ElevatorAlgorithm;
import org.elevatorsystem.exceptions.ElevatorCapacityExceededException;
import org.elevatorsystem.exceptions.ElevatorMaintainanceModeException;
import org.elevatorsystem.exceptions.InvalidFloorException;
import org.elevatorsystem.model.Elevator;
import org.elevatorsystem.model.request.Request;

import java.util.Comparator;
import java.util.List;

public class ElevatorController {
    private List<Elevator> elevators;
    private ElevatorAlgorithm algorithm; // Add this field

    public ElevatorController(List<Elevator> elevators, ElevatorAlgorithm algorithm) {
        this.elevators = elevators;
        this.algorithm = algorithm; // Initialize the algorithm
    }

    public void processRequest(Request request) throws InvalidFloorException, ElevatorMaintainanceModeException, ElevatorCapacityExceededException {
        Elevator bestElevator = findBestElevator(request);
        bestElevator.addRequest(request);
    }

    private Elevator findBestElevator(Request request) {
        // Use the elevator algorithm to find the best elevator
        // You can implement a more sophisticated logic here
        return elevators.stream()
                .min(Comparator.comparingInt(elevator -> algorithm.getNextFloor(elevator)))
                .orElseThrow();
    }

    public void enterMaintainanceMode(int elevatorId) {
        // Validate the elevator ID
        if (elevatorId < 0 || elevatorId >= elevators.size()) {
            System.out.println("Invalid elevator ID.");
            return;
        }

        // Get the specific elevator
        Elevator elevator = elevators.get(elevatorId);

        // Enter maintenance mode using the elevator's own method
        elevator.enterMaintenanceMode();
        System.out.println("Elevator " + elevatorId + " has been set to maintenance mode.");
    }
}
