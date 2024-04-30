package org.elevatorsystem.model.request;

public abstract class Request {
    protected int floor;
    protected int numberOfPeople;

    public Request(int floor, int numberOfPeople) {
        this.floor = floor;
        this.numberOfPeople = numberOfPeople;
    }

    public int getFloor() {
        return floor;
    }

    public int getNumberOfPeople() {
        return numberOfPeople;
    }
}