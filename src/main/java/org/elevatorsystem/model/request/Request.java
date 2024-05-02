package org.elevatorsystem.model.request;

import org.elevatorsystem.model.Direction;
public class Request {
    private final int floor;
    private final int numberOfPeople;
    private final RequestType type;
    private final Direction direction; // Only applicable for external requests

    public Request(int floor, int numberOfPeople, RequestType type, Direction direction) {
        validateFloor(floor);
        validateNumberOfPeople(numberOfPeople);
        this.floor = floor;
        this.numberOfPeople = numberOfPeople;
        this.type = type;
        this.direction = direction;
    }

    private void validateFloor(int floor) {
        if (floor < 1) {
            throw new IllegalArgumentException("Floor must be greater than or equal to 1.");
        }
    }

    private void validateNumberOfPeople(int numberOfPeople) {
        if (numberOfPeople <= 0) {
            throw new IllegalArgumentException("Number of people must be greater than 0.");
        }
    }

    public int getFloor() {
        return floor;
    }

    public int getNumberOfPeople() {
        return numberOfPeople;
    }

    public RequestType getType() {
        return type;
    }

    public Direction getDirection() {
        if (type == RequestType.INTERNAL) {
            throw new UnsupportedOperationException("Direction is not applicable for internal requests.");
        }
        return direction;
    }

    @Override
    public String toString() {
        return "Request{" +
                "floor=" + floor +
                ", numberOfPeople=" + numberOfPeople +
                ", type=" + type +
                ", direction=" + (type == RequestType.EXTERNAL ? direction : "N/A") +
                '}';
    }
}