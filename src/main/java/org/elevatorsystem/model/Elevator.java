package org.elevatorsystem.model;

import org.elevatorsystem.algorithms.ElevatorAlgorithm;
import org.elevatorsystem.algorithms.FCFSElevatorAlgorithm;
import org.elevatorsystem.controller.FloorManager;
import org.elevatorsystem.controller.MaintainanceManager;
import org.elevatorsystem.exceptions.ElevatorCapacityExceededException;
import org.elevatorsystem.exceptions.ElevatorMaintainanceModeException;
import org.elevatorsystem.exceptions.InvalidFloorException;
import org.elevatorsystem.model.request.Request;
import org.elevatorsystem.observer.ElevatorObserver;

import java.util.*;

public class Elevator {
    private final int id;
    private int currentFloor;
    private Direction currentDirection;
    private State state;
    private final List<ElevatorObserver> observers;
    private final FloorManager floorManager;
    private final MaintainanceManager maintenanceManager;
    private int currentLoad;
    private static final int MAX_CAPACITY = 50;
    private final DisplayBoard displayBoard;
    private final PriorityQueue<Integer> destinationFloorsPQ;
    private final Queue<Integer> destinationFloorsQueue;

    public Elevator(int id, int initialFloor, DisplayBoard displayBoard) {
        this.displayBoard = displayBoard;
        if (initialFloor < 1 || initialFloor > 41) {
            throw new IllegalArgumentException("Invalid initial floor number.");
        }
        this.id = id;
        this.currentFloor = initialFloor;
        this.currentDirection = Direction.IDLE;
        this.state = State.IDLE;
        this.observers = new ArrayList<>();
        this.floorManager = new FloorManager(this);
        this.maintenanceManager = new MaintainanceManager(this);
        this.destinationFloorsPQ = new PriorityQueue<>(Comparator.comparingInt(Math::abs));
        this.destinationFloorsQueue = new LinkedList<>();
    }

    public void addObserver(ElevatorObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(ElevatorObserver observer) {
        observers.remove(observer);
    }

    private void notifyStateChanged() {
        for (ElevatorObserver observer : observers) {
            observer.onElevatorStateChanged(this);
        }
    }

    public void addRequest(Request request) {
        try {
            if (state == State.MAINTENANCE) {
                throw new ElevatorMaintainanceModeException("Elevator is in maintenance mode.");
            }
            if (currentLoad + request.getNumberOfPeople() > MAX_CAPACITY) {
                throw new ElevatorCapacityExceededException("Elevator capacity exceeded.");
            }
            if (request.getFloor() < 1 || request.getFloor() > 41) {
                throw new InvalidFloorException("Invalid floor number.");
            }
            floorManager.addDestinationFloor(request.getFloor());
            currentLoad = currentLoad + request.getNumberOfPeople();
            if (state == State.IDLE) {
                state = State.MOVING;
                notifyStateChanged();
                floorManager.moveElevator();
            }
        } catch (ElevatorMaintainanceModeException | ElevatorCapacityExceededException | InvalidFloorException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void removePassengers(int numberOfPeople) {
        currentLoad = Math.max(0, currentLoad - numberOfPeople);
    }

    public void reachDestinationFloor(int floor) {
        currentFloor = floor;
        notifyStateChanged();
        // Open elevator doors and let passengers exit
        // ...
    }

    // Getters and setters...

    public int getId() {
        return id;
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public void setCurrentFloor(int currentFloor) {
        this.currentFloor = currentFloor;
        notifyStateChanged();
    }

    public Direction getCurrentDirection() {
        return currentDirection;
    }

    public void setCurrentDirection(Direction currentDirection) {
        this.currentDirection = currentDirection;
        displayBoard.updateDisplay(currentFloor, currentDirection);
        notifyStateChanged();
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
        displayBoard.updateDisplay(currentFloor, currentDirection);
        notifyStateChanged();
    }

    public void enterMaintenanceMode() {
        maintenanceManager.enterMaintenanceMode();
    }

    public void exitMaintenanceMode() {
        maintenanceManager.exitMaintenanceMode();
    }


    public void addDestinationFloor(int floor) {
        destinationFloorsPQ.offer(floor);
        destinationFloorsQueue.offer(floor);
    }

    public int removeDestinationFloor(ElevatorAlgorithm algorithm) {
        if (algorithm instanceof FCFSElevatorAlgorithm) {
            if (!destinationFloorsQueue.isEmpty()) {
                return destinationFloorsQueue.poll();
            }
        } else {
            if (!destinationFloorsPQ.isEmpty()) {
                return destinationFloorsPQ.poll();
            }
        }
        return currentFloor;
    }

    public PriorityQueue<Integer> getDestinationFloorsPQ() {
        return destinationFloorsPQ;
    }

    public Queue<Integer> getDestinationFloorsQueue() {
        return destinationFloorsQueue;
    }
}