/*package org.spotifumtp37.controller;

import org.spotifumtp37.model.SpotifUMData;
import org.spotifumtp37.model.album.Album;
import org.spotifumtp37.model.exceptions.JaExisteException;
import org.spotifumtp37.model.exceptions.NaoExisteException;
import org.spotifumtp37.model.subscription.FreePlan;
import org.spotifumtp37.model.user.User;
import org.spotifumtp37.util.JsonDataParser;

import java.io.IOException;
import java.util.Map;


public class MainController {
    private final SpotifUMData modelData; // Model com que o controller deve interagir
    private final JsonDataParser parser = new JsonDataParser();

    public MainController(SpotifUMData modelData) {
        this.modelData = modelData;
    }

    public boolean authenticateUser(String username, String password) {
        if (username == null || password == null || username.isEmpty() || password.isEmpty()) {
            return false;
        }
        try {
            User user = modelData.getUser(username);
            return password.equals(user.getPassword());
        } catch (NaoExisteException e) {
            return false;
        }
    }

    public void addAlbum(Album album) throws JaExisteException {
        modelData.adicionaAlbum(album);
    }

    public Map<String, Album> getAllAlbums() {
        return modelData.getMapAlbums();
    }

    public boolean registerNewUser(String username, String password, String email, String address) {
        User user = new User(username, email, address, new FreePlan(), password, 0);
        try {
            modelData.adicionaUser(user);
            return true;
        } catch (JaExisteException e) {
            return false;
        }
    }

    public void loadFromJson(String filePath) throws IOException {
        SpotifUMData loaded = parser.fromJsonData(filePath);
        modelData.setMapAlbums(loaded.getMapAlbums());
        modelData.setMapUsers(loaded.getMapUsers());
        modelData.setMapPlaylists(loaded.getMapPlaylists());
    }

    public void saveToJson(String filePath) throws IOException {
        parser.toJsonData(modelData, filePath);
    }

}
 */