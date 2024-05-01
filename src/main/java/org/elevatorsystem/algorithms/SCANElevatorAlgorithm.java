package org.elevatorsystem.algorithms;

import org.elevatorsystem.model.Direction;
import org.elevatorsystem.model.Elevator;

import java.util.PriorityQueue;

public class SCANElevatorAlgorithm implements ElevatorAlgorithm {

    @Override
    public int getNextFloor(Elevator elevator) {
        int currentFloor = elevator.getCurrentFloor();
        Direction currentDirection = elevator.getCurrentDirection();
        PriorityQueue<Integer> destinationFloor = elevator.getDestinationFloors();

        if (destinationFloor.isEmpty()) {
            return currentFloor;
        }

        int nextFloor;
        if (currentDirection == Direction.UP) {
            nextFloor = destinationFloor.stream().filter(floor -> floor >= currentFloor).min(Integer::compareTo).orElse(destinationFloor.peek());
        } else {
            nextFloor = destinationFloor.stream().filter(floor -> floor <= currentFloor).max(Integer::compareTo).orElse(destinationFloor.peek());
        }

        return nextFloor;
    }
}