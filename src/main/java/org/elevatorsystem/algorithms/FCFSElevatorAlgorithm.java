package org.elevatorsystem.algorithms;

import org.elevatorsystem.model.Elevator;

import java.util.PriorityQueue;
import java.util.Queue;

public class FCFSElevatorAlgorithm implements ElevatorAlgorithm {
    @Override
    public int getNextFloor(Elevator elevator) {
        Queue<Integer> destinationFloors = elevator.getDestinationFloorsQueue();
        if (destinationFloors.isEmpty()) {
            return elevator.getCurrentFloor();
        } else {
            return destinationFloors.peek();
        }
    }
}
