package org.spotifumtp37.model;

import org.spotifumtp37.model.album.*;
import org.spotifumtp37.model.exceptions.JaExisteException;
import org.spotifumtp37.model.exceptions.NaoExisteException;
import org.spotifumtp37.model.playlist.Playlist;
import org.spotifumtp37.model.user.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.*;

public class SpotifUMData implements Serializable {
    //basicamente a facade do model? tem todos os dados e é a esta que o controller deve aceder
    private Map<String, Album> albuns;
    private Map<String, User> users;
    private Map<String, Playlist> playlists;

    // falta os play e pause, contar as views da musica

    public SpotifUMData() {
        this.albuns = new HashMap<>();
        this.users = new HashMap<>();
        this.playlists = new HashMap<>();
    }

    public SpotifUMData(SpotifUMData outro) {
        this.albuns = new HashMap<>();
        for (Map.Entry<String, Album> entry : outro.albuns.entrySet()) {
            this.albuns.put(entry.getKey(), entry.getValue().clone());
        }
        this.users = new HashMap<>();
        for (Map.Entry<String, User> entry : outro.users.entrySet()) {
            this.users.put(entry.getKey(), entry.getValue().clone());
        }
        this.playlists = new HashMap<>();
        for (Map.Entry<String, Playlist> entry : outro.playlists.entrySet()) {
            this.playlists.put(entry.getKey(), entry.getValue().clone());
        }
    }

    @Override
    public SpotifUMData clone() {
        return new SpotifUMData(this);
    }

    @Override
    public String toString() {
        return "Albuns: " + albuns.toString()
                + "\nUsers: " + users.toString()
                + "\nPlaylists: " + playlists.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SpotifUMData that = (SpotifUMData) o;
        return Objects.equals(albuns, that.albuns)
                && Objects.equals(users, that.users)
                && Objects.equals(playlists, that.playlists);
    }

    public Map<String, Album> getMapAlbums() {
        Map<String, Album> mapCarros = new HashMap<>();
        for (Map.Entry<String, Album> entry : albuns.entrySet()) {
            mapCarros.put(entry.getKey(), entry.getValue().clone());
        }
        return mapCarros;
    }

    public Map<String, User> getMapUsers() {
        Map<String, User> mapUsers = new HashMap<>();
        for (Map.Entry<String, User> entry : users.entrySet()) {
            mapUsers.put(entry.getKey(), entry.getValue().clone());
        }
        return mapUsers;
    }

    public User getCurrentUserPointer(String username) {
        return this.users.get(username);
    }

    public Map<String, Playlist> getMapPlaylists() {
        Map<String, Playlist> mapPlaylists = new HashMap<>();
        for (Map.Entry<String, Playlist> entry : playlists.entrySet()) {
            mapPlaylists.put(entry.getKey(), entry.getValue().clone());
        }
        return mapPlaylists;
    }

    public void setMapAlbums(Map<String, Album> mapAlbums) {
        Map<String, Album> newMapCarros = new HashMap<>();
        for (Map.Entry<String, Album> entry : mapAlbums.entrySet()) {
            newMapCarros.put(entry.getKey(), entry.getValue().clone());
        }
        this.albuns = newMapCarros;
    }

    public void setMapUsers(Map<String, User> mapUsers) {
        Map<String, User> newMapUsers = new HashMap<>();
        for (Map.Entry<String, User> entry : mapUsers.entrySet()) {
            newMapUsers.put(entry.getKey(), entry.getValue().clone());
        }
        this.users = newMapUsers;
    }

    public void setMapPlaylists(Map<String, Playlist> mapPlaylists) {
        Map<String, Playlist> newMapPlaylists = new HashMap<>();
        for (Map.Entry<String, Playlist> entry : mapPlaylists.entrySet()) {
            newMapPlaylists.put(entry.getKey(), entry.getValue().clone());
        }
        this.playlists = newMapPlaylists;
    }

    public boolean existeAlbum(String title) {
        return albuns.containsKey(title);
    }

    public boolean existeMusica(String title, String albumTitle) {
        if (existeAlbum(albumTitle)) {
            Album album = albuns.get(albumTitle);
            for (Song song : album.getSongs()) {
                if (song.getName().equals(title)) {
                    return true;
                }
            }
            return false;
        }
        else return false;
    }

    public boolean existePlaylist(String playlistName) {
        return playlists.containsKey(playlistName);
    }

    public boolean existeUser(String username) {
        return users.containsKey(username);
    }

    public int quantosAlbums() {
        return albuns.size();
    }

    public int quantosPlaylists() {
        return playlists.size();
    }

    public int quantosUsers() {
        return users.size();
    }

    public Album getAlbum(String title) throws NaoExisteException {
        if (!existeAlbum(title)) {
            throw new NaoExisteException(title);
        }
        else {
            return albuns.get(title).clone();
        }
    }

    public Song getSong(String title, String albumTitle) throws NaoExisteException {
        if (!existeMusica(title, albumTitle)) {
            throw new NaoExisteException(title);
        }
        else {
            Album album = albuns.get(albumTitle);
            for (Song song : album.getSongs()) {
                if (song.getName().equals(title)) {
                    return song;
                }
            }
        }
        return null;
    }

    public Playlist getPlaylist(String playlistName) throws NaoExisteException {
        if (!existePlaylist(playlistName) || playlists.get(playlistName).isPrivate()) {
            throw new NaoExisteException(playlistName);
        }
        else {
            return playlists.get(playlistName).clone();
        }
    }

    public User getUser(String username) throws NaoExisteException {
        if (!existeUser(username)) {
            throw new NaoExisteException(username);
        }
        else {
            return users.get(username).clone();
        }
    }

    public void adicionaAlbum(Album album) throws JaExisteException {
        if (!albuns.containsKey(album.getTitle())) {
            albuns.put(album.getTitle(), album.clone());
        }
        else {
            throw new JaExisteException(album.getTitle());
        }
    }

    public void adicionaPlaylist(Playlist playlist) throws JaExisteException {
        if (!playlists.containsKey(playlist.getPlaylistName())) {
            playlists.put(playlist.getPlaylistName(), playlist.clone());
        }
        else {
            throw new JaExisteException(playlist.getPlaylistName());
        }
    }

    public void adicionaUser (User user) throws JaExisteException {
        if (!users.containsKey(user.getName())) {
            users.put(user.getName(), user.clone());
        }
        else {
            throw new JaExisteException(user.getName());
        }
    }
}


