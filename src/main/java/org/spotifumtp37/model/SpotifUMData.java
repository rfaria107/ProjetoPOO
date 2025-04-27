package org.spotifumtp37.model;

import org.spotifumtp37.model.album.*;
import org.spotifumtp37.model.user.*;

import java.util.Map;

public class SpotifUMData {
    //basicamente a facade do model? tem todos os dados e é a esta que o controller deve aceder
    public Map<String, Album> albuns;
    public Map<String, User> users;
    public Map<String, Playlist> playlists;
}
