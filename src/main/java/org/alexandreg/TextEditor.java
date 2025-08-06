package org.alexandreg;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

public class TextEditor {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Using Stack (LIFO) to implement Undo and Redo functionalities.
        // Each stack will store complete "snapshots" of the text state.
        Stack< List<String>> stackUndo = new Stack<>(); // Stores previous text states (for Undo)
        Stack< List<String>> stackRedo = new Stack<>(); // Stores undone text states (for Redo)

        List<String> currentTasks = new ArrayList<>();    // Holds the current text being edited
        boolean executing = true;   // Controls the main loop

        while (executing) {
            System.out.println("\n=== Text Editor ===");
            System.out.println("1 - Insert a new text");
            System.out.println("2 - Undo");
            System.out.println("3 - Redo");
            System.out.println("4 - Show current text");
            System.out.println("5 - Remove a task");
            System.out.println("6 - Exit");
            System.out.print("Choose an option: ");

            int option = scanner.nextInt();
            scanner.nextLine(); // Consume the leftover newline from nextInt()

            switch (option) {
                case 1:
                    System.out.println("Insert a new text: ");
                    stackUndo.push(new ArrayList<>(currentTasks));//save a copy of the current state
                    String newTask =  scanner.nextLine();
                    currentTasks.add(newTask); //add a new task
                    stackRedo.clear();//clear redo stack
                    printDebug(currentTasks, stackUndo, stackRedo);
                    break;

                case 2:
                    // Undo: Revert to the previous text state
                    if (!stackUndo.isEmpty()) {
                        stackRedo.push(new ArrayList<>(currentTasks));//save the current state in redo
                        currentTasks = new ArrayList<>(stackUndo.pop());//restores previous state
                        System.out.println("Undo finished!");
                    } else {
                        System.out.println("Nothing to be undone.");
                    }
                    printDebug(currentTasks, stackUndo, stackRedo);
                    break;

                case 3:
                    // Redo: Reapply the last undone change
                    if (!stackRedo.isEmpty()) {
                        stackUndo.push(new ArrayList<>(currentTasks));//save the current state in redo
                        currentTasks = new ArrayList<>(stackRedo.pop());//restores previous state
                        System.out.println("Redo finished!");
                    } else {
                        System.out.println("Nothing to redo.");
                    }
                    printDebug(currentTasks, stackUndo, stackRedo);
                    break;

                case 4:
                    // Display the current text content
                    System.out.println("Current text: " );
                    if(currentTasks.isEmpty()){
                        System.out.println("(No tasks added yet.)");
                    }else {
                        for (int i = 0; i < currentTasks.size(); i++) {
                            System.out.println((i + 1) + " - " + currentTasks.get(i));
                        }
                    }
                    break;

                case 5:
                    if(currentTasks.isEmpty()){
                        System.out.println("No tasks to remove.");
                    }else{
                        System.out.println("Select the task number to remove it: ");
                        for(int i=0; i< currentTasks.size(); i++){
                            System.out.println((i+1) + " - " + currentTasks.get(i));
                        }

                        int taskNumber = scanner.nextInt();
                        scanner.nextLine();

                        if(taskNumber < 1 || taskNumber  > currentTasks.size()){
                            System.out.println("Invalid task number.");
                        }else {
                            stackUndo.push(new ArrayList<>(currentTasks));//save state for undo
                            currentTasks.remove(taskNumber - 1);//remove the selected task
                            stackRedo.clear();//clear redo since I've just made a new change
                            System.out.println("Task removed successfully!");
                            printDebug(currentTasks, stackUndo, stackRedo);
                        }
                    }
                    break;

                case 6:
                    // Exit the program
                    System.out.println("Thank you for using me :)");
                    executing = false;
                    break;

                default:
                    System.out.println("Invalid option.");
            }
        }
        scanner.close();
    }

    private static void printDebug(List<String> currentTasks, Stack<List<String>> stackUndo, Stack<List<String>> stackRedo){
        System.out.println("\n[DEBUG] Current Text: " + currentTasks);
        System.out.println("[DEBUG] Undo Stack: " + stackUndo);
        System.out.println("[DEBUG] Redo Stack: " + stackRedo);
    }
}
