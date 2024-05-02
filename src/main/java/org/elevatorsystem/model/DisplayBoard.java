package org.elevatorsystem.model;

public class DisplayBoard {
    private int currentFloor;
    private Direction direction;

    public DisplayBoard() {
        this.currentFloor = 0;
        this.direction = Direction.IDLE;
    }

    public synchronized void updateDisplay(int currentFloor, Direction direction) {
        this.currentFloor = currentFloor;
        this.direction = direction;
        show();
    }

    private synchronized void show() {
        System.out.println("Current floor: " + currentFloor);
        System.out.println("Direction: " + (direction != null ? direction.toString() : "Idle"));
    }

    public synchronized void clearDisplay() {
        this.direction = Direction.IDLE;
        show();
    }
}