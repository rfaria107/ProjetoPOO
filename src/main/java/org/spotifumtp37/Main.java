package org.spotifumtp37;


import org.spotifumtp37.delegate.TextUI;

public class Main {
    public static void main(String[] args) {
        System.out.println("\nWelcome to SpotifUM!");

        TextUI spotifum = new TextUI();
        spotifum.run();
    }
}