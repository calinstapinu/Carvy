package org.dealership.presentation;

import java.util.Scanner;

/**
 * Utility class for handling user input and displaying menus in the console.
 * Provides methods for showing menus and reading various types of input from the user.
 */
public class MenuHandler {
    private static final Scanner scanner = new Scanner(System.in);

    /**
     * Displays a menu with the specified title and options, and reads the user's choice.
     *
     * @param menuTitle the title of the menu to display
     * @param options   the array of menu options
     * @return the user's choice as an integer
     */
    public static int showMenu(String menuTitle, String[] options) {
        System.out.println("\n=== " + menuTitle + " ===");
        for (int i = 0; i < options.length; i++) {
            System.out.println((i + 1) + ". " + options[i]);
        }
        System.out.println("0. Exit");
        System.out.print("Choose an option: ");
        return scanner.nextInt();
    }

    /**
     * Reads a line of text input from the user.
     *
     * @param prompt the message displayed to the user before input
     * @return the text entered by the user
     */
    public static String readText(String prompt) {
        System.out.print(prompt);
        return scanner.next();
    }

    /**
     * Reads an integer input from the user.
     *
     * @param prompt the message displayed to the user before input
     * @return the integer entered by the user
     */
    public static int readInt(String prompt) {
        System.out.print(prompt);
        return scanner.nextInt();
    }

    /**
     * Reads a long integer input from the user.
     * Validates that the input is a valid long value.
     *
     * @param prompt the message displayed to the user before input
     * @return the long value entered by the user
     */
    public static long readLong(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextLong()) { // Validates that the input is a valid long
            System.out.println("Please enter a valid number!");
            scanner.next(); // Consume invalid input
            System.out.print(prompt);
        }
        return scanner.nextLong();
    }

    /**
     * Reads a float input from the user.
     *
     * @param prompt the message displayed to the user before input
     * @return the float value entered by the user
     */
    public static float readFloat(String prompt) {
        System.out.print(prompt);
        return scanner.nextFloat();
    }
}