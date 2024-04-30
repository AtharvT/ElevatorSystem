package org.elevatorsystem.model;

import org.elevatorsystem.exceptions.ElevatorCapacityExceededException;
import org.elevatorsystem.exceptions.ElevatorMaintainanceModeException;
import org.elevatorsystem.exceptions.InvalidFloorException;
import org.elevatorsystem.model.request.Request;

import java.util.Comparator;
import java.util.PriorityQueue;

public class Elevator {

    private final DisplayBoard displayBoard;
    private int currentFloor;
    private PriorityQueue<Integer> destinationFloor;
    private final int maxCapacity;
    private int currentLoad;
    private Direction currentDirection;
    private State state;

    public Elevator(DisplayBoard displayBoard, int maxCapacity) {
        this.displayBoard = displayBoard;
        this.maxCapacity = maxCapacity;
        this.currentFloor = 0;
        this.currentDirection = Direction.IDLE;
        this.state = State.IDLE;
        this.destinationFloor = new PriorityQueue<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                // Comparator logic to sort the queue based on direction and proximity
                if (currentDirection == Direction.UP) {
                    return Integer.compare(o1, o2);
                } else {
                    return Integer.compare(o2, o1);
                }
            }
        });
        displayBoard.updateDisplay(currentFloor, currentDirection);
    }

    private Direction calculateDirection() {
        if (!destinationFloor.isEmpty()) {
            return currentFloor < destinationFloor.peek() ? Direction.UP : Direction.DOWN;
        }
        return Direction.IDLE;
    }

    private void updateDirection(int requestFloor) {
        if (currentFloor < requestFloor) {
            currentDirection = Direction.UP;
        } else if (currentFloor > requestFloor) {
            currentDirection = Direction.DOWN;
        }
    }

    public boolean isFull() {
        return currentLoad >= maxCapacity;
    }


    public DisplayBoard getDisplayBoard() {
        return displayBoard;
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public void setCurrentFloor(int currentFloor) {
        this.currentFloor = currentFloor;
    }

    public PriorityQueue<Integer> getDestinationFloor() {
        return destinationFloor;
    }

    public void setDestinationFloor(PriorityQueue<Integer> destinationFloor) {
        this.destinationFloor = destinationFloor;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public int getCurrentLoad() {
        return currentLoad;
    }

    public void setCurrentLoad(int currentLoad) {
        this.currentLoad = currentLoad;
    }

    public void move() {
        if (!destinationFloor.isEmpty() && state != State.MAINTAINANCE) {
            int nextFloor = destinationFloor.peek();
            currentFloor = nextFloor;
            displayBoard.updateDisplay(currentFloor, calculateDirection());
            destinationFloor.poll();
            state = State.MOVING;  // Update state to MOVING
        } else {
            state = State.IDLE;  // Set state to IDLE when there are no more floors to visit
        }
    }

    public void stop() {
        if (state != State.MAINTAINANCE) {
            state = State.IDLE;
            displayBoard.updateDisplay(currentFloor, currentDirection);
        }
    }

    public void addRequest(Request request) throws InvalidFloorException, ElevatorCapacityExceededException, ElevatorMaintainanceModeException {
        if (this.state == State.MAINTAINANCE) {
            throw new ElevatorMaintainanceModeException("Elevator is under maintenance.");
        }
        if (currentLoad + request.getNumberOfPeople() > maxCapacity) {
            throw new ElevatorCapacityExceededException("Elevator capacity exceeded.");
        }
        if (request.getFloor() < 0 || request.getFloor() > 41) { // Assuming 'topFloor' is the highest floor in the building
            throw new InvalidFloorException("Requested floor is invalid.");
        }
        destinationFloor.offer(request.getFloor());
        updateDirection(request.getFloor());
        if (currentDirection == Direction.IDLE) {
            move();
        }
    }

    public void enterMaintenanceMode() {
        state = State.MAINTAINANCE;
        destinationFloor.clear();  // Optionally clear all pending requests
        displayBoard.updateDisplay(currentFloor, currentDirection);
    }
}
