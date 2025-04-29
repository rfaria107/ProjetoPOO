package org.spotifumtp37;

import org.spotifumtp37.controller.MainController;
import org.spotifumtp37.model.SpotifUMData;
import org.spotifumtp37.view.MainMenuView;

public class Main {
    public static void main(String[] args) {
        // inicializar o model
        SpotifUMData spotifUMData = new SpotifUMData();
        // o controller
        MainController controller = new MainController(spotifUMData);
        // e a view
        MainMenuView mainMenuView = new MainMenuView(controller);
        //iniciar a execução
        mainMenuView.run();
    }
}