package org.elevatorsystem.algorithms;

import org.elevatorsystem.model.Elevator;

import java.util.PriorityQueue;

public class FCFSElevatorAlgorithm implements ElevatorAlgorithm {

    @Override
    public int getNextFloor(Elevator elevator) {
        PriorityQueue<Integer> destinationFloor = elevator.getDestinationFloors();
        if (destinationFloor.isEmpty()) {
            return elevator.getCurrentFloor();
        } else {
            return destinationFloor.peek();
        }
    }
}
