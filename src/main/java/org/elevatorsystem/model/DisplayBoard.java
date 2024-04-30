package org.elevatorsystem.model;
public class DisplayBoard {
    private Integer currentFloor;
    private Direction direction;

    public void updateDisplay(int currentFloor, Direction direction) {
        this.currentFloor = currentFloor;
        this.direction = direction;
        show();
    }

    private void show() {
        System.out.println("Current floor: " + currentFloor);
        System.out.println("Direction: " + (direction != null ? direction.toString() : "Idle"));
    }
}