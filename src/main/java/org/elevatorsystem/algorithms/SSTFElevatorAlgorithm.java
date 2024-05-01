package org.elevatorsystem.algorithms;

import org.elevatorsystem.model.Elevator;

import java.util.Comparator;
import java.util.PriorityQueue;

public class SSTFElevatorAlgorithm implements ElevatorAlgorithm {

    @Override
    public int getNextFloor(Elevator elevator) {
        PriorityQueue<Integer> destinationFloor = elevator.getDestinationFloors();
        if (destinationFloor.isEmpty()) {
            return elevator.getCurrentFloor(); // Stay on the current floor if no requests
        } else {
            int currentFloor = elevator.getCurrentFloor();
            int nearestFloor = destinationFloor.stream().min(Comparator.comparingInt(floor -> Math.abs(floor - currentFloor))).orElse(currentFloor);
            return nearestFloor;
        }
    }
}