package org.spotifumtp37.controller;

import org.spotifumtp37.model.SpotifUMData;


public class MainController {
    //controller para a view mainmenu
    private final SpotifUMData modelData; // Model com que o controller deve interagir

    public MainController(SpotifUMData modelData) {
        this.modelData = modelData;
    }

    public boolean authenticateUser(String username, String password) {
        if (username == null || password == null || username.isEmpty() || password.isEmpty()) {
            return false;
        }
        //hardcoded enquanto o model não é acabado
        return username.equals("testUser") && password.equals("password123");
    }

}