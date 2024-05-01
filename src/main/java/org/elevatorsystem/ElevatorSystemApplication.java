package org.elevatorsystem;

import org.elevatorsystem.algorithms.ElevatorAlgorithm;
import org.elevatorsystem.algorithms.SCANElevatorAlgorithm;
import org.elevatorsystem.controller.ElevatorController;
import org.elevatorsystem.exceptions.ElevatorCapacityExceededException;
import org.elevatorsystem.exceptions.ElevatorMaintainanceModeException;
import org.elevatorsystem.exceptions.InvalidFloorException;
import org.elevatorsystem.model.Direction;
import org.elevatorsystem.model.DisplayBoard;
import org.elevatorsystem.model.Elevator;
import org.elevatorsystem.model.request.ExternalRequest;
import org.elevatorsystem.model.request.InternalRequest;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class ElevatorSystemApplication {
    public static void main(String[] args) throws InvalidFloorException, ElevatorMaintainanceModeException, ElevatorCapacityExceededException {
        final Scanner scanner = new Scanner(System.in);
        List<Elevator> elevatorList = new ArrayList<>();
        DisplayBoard displayBoard = new DisplayBoard();

        // Initialize elevators with distinct display boards if needed
        for (int i = 0; i < 4; i++) {
            elevatorList.add(new Elevator(new DisplayBoard(), 50));
        }

        ElevatorAlgorithm algorithm = new SCANElevatorAlgorithm(); // or any other algorithm
        ElevatorController elevatorController = new ElevatorController(elevatorList, algorithm);

        while (true) {
            System.out.println("Enter command (ADD, MAINTAIN, EXIT):");
            String command = scanner.next().toUpperCase();

            switch (command) {
                case "ADD":
                    handleAddRequest(scanner, elevatorController);
                    break;
                case "MAINTAIN":
                    handleMaintenance(scanner, elevatorController);
                    break;
                case "EXIT":
                    System.out.println("Exiting system...");
                    scanner.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid command.");
                    break;
            }
        }
    }

    private static void handleAddRequest(Scanner scanner, ElevatorController controller) throws InvalidFloorException, ElevatorMaintainanceModeException, ElevatorCapacityExceededException {
        System.out.println("Enter request type (INTERNAL or EXTERNAL):");
        String type = scanner.next().toUpperCase();
        System.out.println("Enter floor number:");
        int floor = scanner.nextInt();
        System.out.println("Enter number of people:");
        int people = scanner.nextInt();

        if (type.equals("EXTERNAL")) {
            System.out.println("Enter direction (UP or DOWN):");
            String dir = scanner.next().toUpperCase();
            Direction direction = Direction.valueOf(dir);
            controller.processRequest(new ExternalRequest(direction, floor, people));
        } else {
            controller.processRequest(new InternalRequest(floor, people));
        }
    }

    private static void handleMaintenance(Scanner scanner, ElevatorController controller) {
        System.out.println("Enter elevator ID for maintenance:");
        int elevatorId = scanner.nextInt();
        controller.enterMaintainanceMode(elevatorId);
    }
}