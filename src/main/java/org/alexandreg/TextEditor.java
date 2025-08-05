package org.alexandreg;

import java.util.Scanner;
import java.util.Stack;

public class TextEditor {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Using Stack (LIFO) to implement Undo and Redo functionalities.
        // Each stack will store complete "snapshots" of the text state.
        Stack<String> stackUndo = new Stack<>(); // Stores previous text states (for Undo)
        Stack<String> stackRedo = new Stack<>(); // Stores undone text states (for Redo)

        String currentText = "";    // Holds the current text being edited
        boolean executing = true;   // Controls the main loop

        while (executing) {
            System.out.println("\n=== Text Editor ===");
            System.out.println("1 - Insert a new text");
            System.out.println("2 - Undo");
            System.out.println("3 - Redo");
            System.out.println("4 - Show current text");
            System.out.println("5 - Exit");
            System.out.print("Choose an option: ");

            int option = scanner.nextInt();
            scanner.nextLine(); // Consume the leftover newline from nextInt()

            switch (option) {
                case 1:
                    System.out.println("Insert a new text: ");

                    // Save the current text in Undo stack before making changes.
                    stackUndo.push(currentText);

                    // Update the current text with the new input.
                    currentText = scanner.nextLine();

                    // Clear Redo stack because a new edit invalidates the redo history.
                    stackRedo.clear();
                    break;

                case 2:
                    // Undo: Revert to the previous text state
                    if (!stackUndo.isEmpty()) {
                        // Save the current text in Redo stack so it can be restored if needed.
                        stackRedo.push(currentText);

                        // Pop the most recent text state from Undo and set it as current.
                        currentText = stackUndo.pop();

                        System.out.println("Undo finished!");
                    } else {
                        System.out.println("Nothing to be undone.");
                    }
                    break;

                case 3:
                    // Redo: Reapply the last undone change
                    if (!stackRedo.isEmpty()) {
                        // Save the current text in Undo before redoing,
                        // so we can "undo" this redo if necessary.
                        stackUndo.push(currentText);

                        // Pop the most recent state from Redo and restore it.
                        currentText = stackRedo.pop();

                        System.out.println("Redo finished!");
                    } else {
                        System.out.println("Nothing to redo.");
                    }
                    break;

                case 4:
                    // Display the current text content
                    System.out.println("Current text: " + currentText);
                    break;

                case 5:
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
}
