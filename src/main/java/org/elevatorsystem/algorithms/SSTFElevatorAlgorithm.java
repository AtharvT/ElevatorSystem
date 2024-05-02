package org.elevatorsystem.algorithms;

import org.elevatorsystem.model.Direction;
import org.elevatorsystem.model.Elevator;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.stream.Collectors;

public class SSTFElevatorAlgorithm implements ElevatorAlgorithm {
    @Override
    public int getNextFloor(Elevator elevator) {
        Queue<Integer> destinationFloors = elevator.getDestinationFloorsPQ();
        if (destinationFloors.isEmpty()) {
            return elevator.getCurrentFloor();
        } else {
            int currentFloor = elevator.getCurrentFloor();
            int closestFloor = destinationFloors.peek();
            int minDistance = Math.abs(closestFloor - currentFloor);
            for (int floor : destinationFloors) {
                int distance = Math.abs(floor - currentFloor);
                if (distance < minDistance) {
                    closestFloor = floor;
                    minDistance = distance;
                }
            }
            return closestFloor;
        }
    }
}