package org.spotifumtp37.view;

import org.spotifumtp37.controller.MainController;
import org.spotifumtp37.util.JsonDataParser;

import java.util.Scanner;

public class AdminMenuView {
    private final MainController controller;

    public AdminMenuView(MainController controller) {
        this.controller = controller;
    }

    public void run() {
        while (true) {
            System.out.println("Admin Menu:");
            System.out.println("1. Add New Album");
            System.out.println("2. Edit Existing Album");
            System.out.println("3. Delete Album");
            System.out.println("4. Add New User");
            System.out.println("5. Edit Existing User");
            System.out.println("6. Delete User");
            System.out.println("7. Parse data from Json");
            System.out.println("8. Show Albums");
            System.out.println("9. Show Users");
            System.out.println("10. Show Playlists");
            System.out.println("11. Logout");

            System.out.print("Select an option: ");
            Scanner scanner = new Scanner(System.in);
            int choice = scanner.nextInt();
            switch (choice) {
                case 1 -> {
                    System.out.println("Adding new album...");
                }
                case 2 -> {
                    System.out.println("Editing existing album...");
                }
                case 3 -> {
                    System.out.println("Enter the path to the Json file: ");
                    String path = scanner.next().trim();
                    System.out.println("Parsing data from Json...");
                    JsonDataParser parser = new JsonDataParser();
                    try {
                        // Carregar tudo
                        controller.loadFromJson(path);
                    } catch (Exception e) {
                        System.out.println("Failed to load data: " + e.getMessage());
                    }
                }
                case 4 -> {
                    System.out.println("Showing albums...");

                }
                case 5 -> {
                    System.out.println("Returning to Main Menu!");
                    return;
                }
            }
        }
    }
}