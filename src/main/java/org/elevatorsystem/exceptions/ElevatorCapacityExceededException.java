package org.elevatorsystem.exceptions;

public class ElevatorCapacityExceededException extends Exception {
    public ElevatorCapacityExceededException(String message) {
        super(message);
    }
}