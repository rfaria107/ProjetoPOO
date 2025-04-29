package org.spotifumtp37.view;

import java.util.Scanner;

public class AdminMenuView {

    public void run() {
        while (true) {
            System.out.println("Admin Menu:");
            System.out.println("1. Add New Album");
            System.out.println("2. Edit Existing Album");
            System.out.println("3. Parse data from Json");
            System.out.println("4. Show Albums");
            System.out.println("5. Logout");

            System.out.print("Select an option: ");
            Scanner scanner = new Scanner(System.in);
            int choice = scanner.nextInt();
            switch (choice) {
                case 1 -> {
                    System.out.println("Adding new album...");
                    break;
                }
                case 2 -> {
                    System.out.println("Editing existing album...");
                }

                case 5 -> {
                    System.out.println("Returning to Main Menu!");
                    return;
                }
            }
        }
    }

}