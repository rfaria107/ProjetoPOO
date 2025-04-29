package org.spotifumtp37.view;

import org.spotifumtp37.controller.MainController;

import java.util.Scanner;

public class MainMenuView {
    private final MainController controller;

    //construtor
    public MainMenuView(MainController controller) {
        this.controller = controller;
    }

    public void run() {
        while(true) {
            Scanner scanner = new Scanner(System.in);

            System.out.println("Welcome to SpotifUM!");
            System.out.println("1. Log in as User");
            System.out.println("2. Log in as Admin");
            System.out.println("3. Exit");

            System.out.print("Select an option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1 -> this.loginAsUser();
                case 2 -> this.loginAsAdmin();
                case 3 -> {
                    System.out.println("Exiting application!");
                    return;
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }

    public void loginAsUser() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Input your username:");
        String username = scanner.next();
        System.out.print("Input your password:");
        String password = scanner.next();
        //o controller deve aceder ao model e verificar se a password e username estão corretos
        boolean isAuthenticated = controller.authenticateUser(username, password);
        if (isAuthenticated) {
            System.out.println("Login successful! Redirecting to User Menu...");
            UserMenuView userMenuView = new UserMenuView();
            userMenuView.run(); // Show user menu
        } else {
            System.out.println("Invalid credentials for User!");
        }
    }

    public void loginAsAdmin() {
        System.out.println("Redirecting to Admin Menu...");
        AdminMenuView adminMenuView = new AdminMenuView();
        adminMenuView.run(); // Show admin menu
    }
}