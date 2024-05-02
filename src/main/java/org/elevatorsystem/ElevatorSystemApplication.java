package org.elevatorsystem;

import org.elevatorsystem.algorithms.ElevatorAlgorithm;
import org.elevatorsystem.algorithms.SSTFElevatorAlgorithm;
import org.elevatorsystem.controller.ElevatorController;
import org.elevatorsystem.model.Direction;
import org.elevatorsystem.model.DisplayBoard;
import org.elevatorsystem.model.Elevator;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class ElevatorSystemApplication {
    private static final int NUMBER_OF_ELEVATORS = 4;
    private static final int NUMBER_OF_FLOORS = 10;

    private ElevatorController elevatorController;
    private List<Elevator> elevators;

    public ElevatorSystemApplication() {
        initializeElevators();
        initializeElevatorController();
    }

    private void initializeElevators() {
        elevators = new ArrayList<>();
        for (int i = 1; i <= NUMBER_OF_ELEVATORS; i++) {
            DisplayBoard displayBoard = new DisplayBoard();
            Elevator elevator = new Elevator(i, 1, displayBoard);
            elevators.add(elevator);
        }
    }

    private void initializeElevatorController() {
        ElevatorAlgorithm algorithm = new SSTFElevatorAlgorithm();
        elevatorController = new ElevatorController(elevators, algorithm);
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            displayMenu();
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    requestElevator(scanner);
                    break;
                case 2:
                    selectDestinationFloor(scanner);
                    break;
                case 3:
                    exit = true;
                    System.out.println("Exiting the Elevator System. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }

    private void displayMenu() {
        System.out.println("Elevator System Menu:");
        System.out.println("1. Request Elevator");
        System.out.println("2. Select Destination Floor");
        System.out.println("3. Exit");
        System.out.print("Enter your choice: ");
    }

    private void requestElevator(Scanner scanner) {
        System.out.print("Enter the floor number: ");
        int floor = scanner.nextInt();
        System.out.print("Enter the direction (1 for UP, -1 for DOWN): ");
        int directionChoice = scanner.nextInt();
        Direction direction = (directionChoice == 1) ? Direction.UP : Direction.DOWN;
        elevatorController.requestElevator(floor, direction);
    }

    private void selectDestinationFloor(Scanner scanner) {
        System.out.print("Enter the elevator ID: ");
        int elevatorId = scanner.nextInt();
        System.out.print("Enter the destination floor: ");
        int destinationFloor = scanner.nextInt();
        Elevator elevator = findElevatorById(elevatorId);
        if (elevator != null) {
            elevator.addDestinationFloor(destinationFloor);
        } else {
            System.out.println("Invalid elevator ID.");
        }
    }

    private Elevator findElevatorById(int elevatorId) {
        for (Elevator elevator : elevators) {
            if (elevator.getId() == elevatorId) {
                return elevator;
            }
        }
        return null;
    }
}