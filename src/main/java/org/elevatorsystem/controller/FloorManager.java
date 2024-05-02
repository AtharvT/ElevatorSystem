package org.elevatorsystem.controller;

import org.elevatorsystem.model.Direction;
import org.elevatorsystem.model.Elevator;
import org.elevatorsystem.model.State;

import java.util.PriorityQueue;

public class FloorManager {
    private final Elevator elevator;
    private final PriorityQueue<Integer> destinationFloors;

    public FloorManager(Elevator elevator) {
        this.elevator = elevator;
        this.destinationFloors = new PriorityQueue<>();
    }

    public void addDestinationFloor(int floor) {
        // Add validation for floor range if needed
        destinationFloors.offer(floor);
    }

    public void moveElevator() {
        while (!destinationFloors.isEmpty()) {
            int nextFloor = destinationFloors.poll();
            int currentFloor = elevator.getCurrentFloor();
            while (currentFloor != nextFloor) {
                if (nextFloor > currentFloor) {
                    elevator.setCurrentDirection(Direction.UP);
                    currentFloor++;
                } else {
                    elevator.setCurrentDirection(Direction.DOWN);
                    currentFloor--;
                }
                elevator.setCurrentFloor(currentFloor);
                // Simulate elevator movement delay
                try {
                    Thread.sleep(1000); // Adjust the delay as needed
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        elevator.setCurrentDirection(Direction.IDLE);
        elevator.setState(State.IDLE);
    }
}
