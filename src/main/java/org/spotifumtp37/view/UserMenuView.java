package org.spotifumtp37.view;

import org.spotifumtp37.controller.MainController;

import java.util.Scanner;

public class UserMenuView {
    private final MainController controller;
    private final Scanner scanner = new Scanner(System.in);

    public UserMenuView(MainController controller) {
        this.controller = controller;
    }
    public void run() {
        System.out.println("User Menu:");
        System.out.println("1. Browse Playlists");
        System.out.println("2. Create a Playlist");
        System.out.println("3. Play a Song");
        System.out.println("4. Update Subscription");
        System.out.println(". Logout");
    }

}