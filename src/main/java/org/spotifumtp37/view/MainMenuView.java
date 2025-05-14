/*package org.spotifumtp37.view;

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
            System.out.println("2. Sign up as a new User");
            System.out.println("3. Log in as Admin");
            System.out.println("4. Exit");

            System.out.print("Select an option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1 -> this.loginAsUser();
                case 2 -> this.signUpAsUser();
                case 3 -> this.loginAsAdmin();
                case 4 -> {
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
            UserMenuView userMenuView = new UserMenuView(controller);
            userMenuView.run(); // Show user menu
        } else {
            System.out.println("Invalid credentials for User!");
        }
    }

    public void signUpAsUser() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Sign Up as a New User!");
        // Obtain username and password
        System.out.print("Enter your desired username: ");
        String username = scanner.next();

        System.out.print("Enter your desired password: ");
        String password = scanner.next();

        System.out.print("Confirm your password: ");
        String confirmPassword = scanner.next();

        System.out.print("Enter your email address: ");
        String email = scanner.next();

        System.out.print("Enter your address: ");
        String address = scanner.next();

        // Verify that the user confirmed the password correctly
        if (!password.equals(confirmPassword)) {
            System.out.println("Passwords do not match! Please retry signing up.");
            return;
        }

        // Call controller to create the user
        boolean isSuccess = controller.registerNewUser(username, password, email, address);

        // Check if account creation was successful
        if (isSuccess) {
            System.out.println("Account created successfully! You can now log in.");
        } else {
            System.out.println("Failed to create account. Username might already exist.");
        }
    }

    public void loginAsAdmin() {
        System.out.println("Redirecting to Admin Menu...");
        AdminMenuView adminMenuView = new AdminMenuView(controller);
        adminMenuView.run(); // Show admin menu
    }
}
 */