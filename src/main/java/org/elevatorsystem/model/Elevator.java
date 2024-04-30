package org.elevatorsystem.model;

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

    public Elevator(DisplayBoard displayBoard, int maxCapacity) {
        this.displayBoard = displayBoard;
        this.maxCapacity = maxCapacity;
        this.currentFloor = 0;
        this.currentDirection = Direction.IDLE;
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
    }

    public void move() {
        if (!destinationFloor.isEmpty()) {
            int nextFloor = destinationFloor.peek(); // Get the next destination
            currentFloor = nextFloor; // Move to the next floor
            displayBoard.updateDisplay(currentFloor, calculateDirection()); // Update display
            destinationFloor.poll(); // Remove the destination once reached
        }
    }

    private Direction calculateDirection() {
        if (!destinationFloor.isEmpty()) {
            return currentFloor < destinationFloor.peek() ? Direction.UP : Direction.DOWN;
        }
        return Direction.IDLE;
    }

    public void stop() {

    }
    public void addRequest(Request request) {

        destinationFloor.offer(request.getFloor());
        updateDirection(request.getFloor());
        // If the elevator was idle, trigger the movement mechanism
        if (currentDirection == Direction.IDLE) {
            move();
        }
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

}
