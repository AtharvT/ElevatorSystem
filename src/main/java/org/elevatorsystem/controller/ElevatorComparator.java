package org.elevatorsystem.controller;

import org.elevatorsystem.algorithms.ElevatorAlgorithm;
import org.elevatorsystem.model.Elevator;
import org.elevatorsystem.model.State;

import java.util.Comparator;

class ElevatorComparator implements Comparator<Elevator> {
    private final ElevatorAlgorithm algorithm;

    public ElevatorComparator(ElevatorAlgorithm algorithm) {
        this.algorithm = algorithm;
    }

    @Override
    public int compare(Elevator e1, Elevator e2) {
        int floor1 = algorithm.getNextFloor(e1);
        int floor2 = algorithm.getNextFloor(e2);

        int distance1 = Math.abs(e1.getCurrentFloor() - floor1);
        int distance2 = Math.abs(e2.getCurrentFloor() - floor2);

        int distanceComparison = Integer.compare(distance1, distance2);

        if (distanceComparison == 0) {
            if (e1.getState() == State.IDLE && e2.getState() != State.IDLE) {
                return -1;
            } else if (e1.getState() != State.IDLE && e2.getState() == State.IDLE) {
                return 1;
            }
        }

        return distanceComparison;
    }
}