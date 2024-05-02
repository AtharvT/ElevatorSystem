package org.elevatorsystem.algorithms;

import org.elevatorsystem.model.Direction;
import org.elevatorsystem.model.Elevator;

import java.util.PriorityQueue;
import java.util.Queue;

public class SCANElevatorAlgorithm implements ElevatorAlgorithm {
    @Override
    public int getNextFloor(Elevator elevator) {
        int currentFloor = elevator.getCurrentFloor();
        Direction currentDirection = elevator.getCurrentDirection();
        Queue<Integer> destinationFloors = elevator.getDestinationFloorsPQ();
        if (destinationFloors.isEmpty()) {
            return currentFloor;
        }
        int nextFloor = currentFloor;
        if (currentDirection == Direction.UP) {
            for (int floor : destinationFloors) {
                if (floor >= currentFloor) {
                    nextFloor = Math.min(nextFloor, floor);
                }
            }
        } else {
            for (int floor : destinationFloors) {
                if (floor <= currentFloor) {
                    nextFloor = Math.max(nextFloor, floor);
                }
            }
        }
        return nextFloor;
    }
}