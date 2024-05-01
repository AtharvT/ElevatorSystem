package org.elevatorsystem.model;

public class DisplayBoard {
    private int currentFloor;
    private Direction direction;
    private boolean floorSet;

    public synchronized void updateDisplay(int currentFloor, Direction direction) {
        this.currentFloor = currentFloor;
        this.direction = direction;
        this.floorSet = true;
        show();
    }

    private synchronized void show() {
        if (floorSet) {
            System.out.println("Current floor: " + currentFloor);
            System.out.println("Direction: " + (direction != null ? direction.toString() : "Idle"));
        } else {
            System.out.println("Elevator is idle");
        }
    }
}