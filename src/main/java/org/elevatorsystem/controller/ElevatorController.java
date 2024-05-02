package org.elevatorsystem.controller;

import org.elevatorsystem.algorithms.ElevatorAlgorithm;
import org.elevatorsystem.model.Direction;
import org.elevatorsystem.model.Elevator;
import org.elevatorsystem.model.request.Request;
import org.elevatorsystem.model.request.RequestType;
import org.elevatorsystem.observer.ElevatorObserver;
import java.util.List;
import java.util.PriorityQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ElevatorController implements ElevatorObserver {
    private static final Logger logger = Logger.getLogger(ElevatorController.class.getName());
    private static final int PERSONS_PER_REQUEST = 1;

    private final PriorityQueue<Elevator> availableElevators;
    private final ElevatorAlgorithm algorithm;

    public ElevatorController(List<Elevator> elevators, ElevatorAlgorithm algorithm) {
        this.algorithm = algorithm;
        this.availableElevators = new PriorityQueue<>(new ElevatorComparator(algorithm));
        this.availableElevators.addAll(elevators);
        for (Elevator elevator : elevators) {
            elevator.addObserver(this);
        }
    }

    public synchronized void requestElevator(int floor, Direction direction) {
        Request request = new Request(floor, PERSONS_PER_REQUEST, RequestType.EXTERNAL, direction);
        Elevator bestElevator = findBestElevator();
        bestElevator.addRequest(request);
    }

    private synchronized Elevator findBestElevator() {
        return availableElevators.poll();
    }

    public synchronized void releaseElevator(Elevator elevator) {
        availableElevators.offer(elevator);
    }

    public synchronized void enterMaintenanceMode(int elevatorId) {
        Elevator elevator = findElevatorById(elevatorId);
        if (elevator != null) {
            elevator.enterMaintenanceMode();
            availableElevators.remove(elevator);
        } else {
            logger.log(Level.WARNING, "Elevator with ID " + elevatorId + " not found for maintenance mode.");
        }
    }

    public synchronized void exitMaintenanceMode(int elevatorId) {
        Elevator elevator = findElevatorById(elevatorId);
        if (elevator != null) {
            elevator.exitMaintenanceMode();
            availableElevators.offer(elevator);
        } else {
            logger.log(Level.WARNING, "Elevator with ID " + elevatorId + " not found for exiting maintenance mode.");
        }
    }

    private Elevator findElevatorById(int elevatorId) {
        for (Elevator elevator : availableElevators) {
            if (elevator.getId() == elevatorId) {
                return elevator;
            }
        }
        return null;
    }

    @Override
    public void onElevatorStateChanged(Elevator elevator) {
        logger.log(Level.INFO, "Elevator " + elevator.getId() + " state changed:");
        logger.log(Level.INFO, "Current Floor: " + elevator.getCurrentFloor());
        logger.log(Level.INFO, "Current Direction: " + elevator.getCurrentDirection());
        logger.log(Level.INFO, "Current State: " + elevator.getState());
        logger.log(Level.INFO, "-------------------------");
    }
}