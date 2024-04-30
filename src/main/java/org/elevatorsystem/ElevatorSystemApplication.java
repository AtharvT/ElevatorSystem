package org.elevatorsystem;

import org.elevatorsystem.controller.ElevatorController;
import org.elevatorsystem.model.DisplayBoard;
import org.elevatorsystem.model.Elevator;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ElevatorSystemApplication {
    public static void main(String[] args) {
        final Scanner scanner = new Scanner(System.in);
        List<Elevator> elevatorList = new ArrayList<>();
        DisplayBoard displayBoard = new DisplayBoard();
        Elevator elevator1 = new Elevator(displayBoard, 50);
        Elevator elevator2 = new Elevator(displayBoard, 50);
        Elevator elevator3 = new Elevator(displayBoard, 50);
        Elevator elevator4 = new Elevator(displayBoard, 50);
        elevatorList.add(elevator1);
        elevatorList.add(elevator2);
        elevatorList.add(elevator3);
        elevatorList.add(elevator4);
        ElevatorController elevatorController = new ElevatorController(elevatorList);
        ;



    }
}